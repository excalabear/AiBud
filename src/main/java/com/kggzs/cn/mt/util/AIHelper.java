package com.kggzs.cn.mt.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import bin.mt.plugin.api.PluginContext;
import bin.mt.plugin.api.ui.PluginEditText;
import bin.mt.plugin.api.ui.dialog.PluginDialog;

/**
 * AI Helper for Gemini API integration
 * Simplified version for code analysis
 */
public class AIHelper {
    private static final String PREF_API_KEY = "gemini_api_key";
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";
    private static final String GEMINI_MODEL = "gemini-2.0-flash";

    @NonNull
    public static String getApiKey(@NonNull PluginContext context) {
        String key = context.getPreferences().getString(PREF_API_KEY, "");
        if (key.isEmpty()) {
            throw new RuntimeException("Gemini API key not configured. Please set it in plugin settings.");
        }
        return key;
    }

    public static void setApiKey(@NonNull PluginContext context, @NonNull String key) {
        context.getPreferences().edit().putString(PREF_API_KEY, key).apply();
    }

    public static void resetToDefault(@NonNull PluginContext context) {
        context.getPreferences().edit().remove(PREF_API_KEY).apply();
    }

    /**
     * Analyze code with Gemini API
     */
    @Nullable
    public static String[] analyzeCode(
            @NonNull PluginContext context,
            @NonNull String code,
            @Nullable PluginEditText resultEdit,
            @Nullable PluginDialog dialog) throws Exception {

        String apiKey = getApiKey(context);
        String prompt = "You are a code security expert. Analyze this code and point out security issues, bugs, and improvements.";

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", GEMINI_MODEL);
        requestBody.put("stream", true);

        JSONArray messages = new JSONArray();
        JSONObject systemMsg = new JSONObject();
        systemMsg.put("role", "system");
        systemMsg.put("content", prompt);
        messages.put(systemMsg);

        JSONObject userMsg = new JSONObject();
        userMsg.put("role", "user");
        userMsg.put("content", "Please analyze this code:\n\n" + code);
        messages.put(userMsg);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        URL url = new URL(GEMINI_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-goog-api-key", apiKey);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);
        connection.setDoOutput(true);

        connection.getOutputStream().write(requestBody.toString().getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            java.io.InputStream errorStream = connection.getErrorStream();
            StringBuilder errorResponse = new StringBuilder();
            if (errorStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                }
            }
            connection.disconnect();
            throw new Exception("Gemini API Error (" + responseCode + "): " + errorResponse.toString());
        }

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String data = line.substring(6);
                    if ("[ DONE]".equals(data)) {
                        break;
                    }

                    try {
                        JSONObject chunk = new JSONObject(data);
                        JSONArray choices = chunk.optJSONArray("choices");
                        if (choices != null && choices.length() > 0) {
                            JSONObject choice = choices.getJSONObject(0);
                            JSONObject delta = choice.optJSONObject("delta");
                            
                            if (delta != null) {
                                String content = delta.optString("content", "");
                                if (!content.isEmpty()) {
                                    result.append(content);
                                    
                                    if (resultEdit != null) {
                                        final String currentResult = result.toString();
                                        runOnMainThread(() -> {
                                            resultEdit.setText(currentResult);
                                            resultEdit.selectEnd();
                                        });
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        android.util.Log.w("AIHelper", "Parse error: " + e.getMessage());
                    }
                }
            }
        }

        if (result.length() == 0) {
            throw new Exception("No response from Gemini API");
        }

        return new String[]{result.toString()};
    }

    public static void runOnMainThread(@NonNull Runnable action) {
        new Handler(Looper.getMainLooper()).post(action);
    }
}
