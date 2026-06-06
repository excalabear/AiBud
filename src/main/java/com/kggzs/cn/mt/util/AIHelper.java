package com.kggzs.cn.mt.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import bin.mt.plugin.api.PluginContext;
import bin.mt.plugin.api.ui.PluginEditText;
import bin.mt.plugin.api.ui.dialog.PluginDialog;

/**
 * AI Helper for Gemini API integration
 * Backwards-compatible wrapper around a simpler analyzeCode implementation.
 */
public class AIHelper {
    // Preference keys (keep old names for compatibility)
    private static final String PREF_API_KEY = "gemini_api_key";
    private static final String PREF_API_KEY_OLD = "ai_api_key";
    private static final String PREF_API_URL = "ai_api_url";
    private static final String PREF_AI_MODEL = "ai_model";
    private static final String PREF_CUSTOM_PROMPT = "ai_custom_prompt";
    private static final String PREF_SHORT_PROMPT = "ai_short_prompt";
    private static final String PREF_QUICK_PROMPTS = "ai_quick_prompts";
    private static final String PREF_SKILLS = "ai_skills";

    // Defaults
    private static final String DEFAULT_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/";
    private static final String DEFAULT_API_COMPLETIONS = DEFAULT_API_URL + "chat/completions";
    private static final String DEFAULT_MODEL = "gemini-2.0-flash";
    private static final String DEFAULT_PROMPT_EN = "You are a code security expert. Analyze this code and point out security issues, bugs, and improvements.";
    private static final String DEFAULT_SHORT_PROMPT_EN = "Please briefly analyze the following code, point out main issues and improvement suggestions:";

    // --- Configuration helpers (backwards-compatible) ---
    @NonNull
    public static String getApiUrl(@NonNull PluginContext context) {
        String url = context.getPreferences().getString(PREF_API_URL, "");
        return (url == null || url.isEmpty()) ? DEFAULT_API_URL : url;
    }

    public static void setApiUrl(@NonNull PluginContext context, @NonNull String url) {
        context.getPreferences().edit().putString(PREF_API_URL, url).apply();
    }

    @NonNull
    public static String getAiModel(@NonNull PluginContext context) {
        String model = context.getPreferences().getString(PREF_AI_MODEL, "");
        return (model == null || model.isEmpty()) ? DEFAULT_MODEL : model;
    }

    public static void setAiModel(@NonNull PluginContext context, @NonNull String model) {
        context.getPreferences().edit().putString(PREF_AI_MODEL, model).apply();
    }

    @Nullable
    public static String getApiKey(@NonNull PluginContext context) {
        String key = context.getPreferences().getString(PREF_API_KEY, "");
        if (key == null || key.isEmpty()) {
            key = context.getPreferences().getString(PREF_API_KEY_OLD, "");
        }
        return (key == null || key.isEmpty()) ? null : key;
    }

    public static void setApiKey(@NonNull PluginContext context, @NonNull String key) {
        context.getPreferences().edit().putString(PREF_API_KEY, key).apply();
    }

    @NonNull
    public static String getPrompt(@NonNull PluginContext context) {
        String p = context.getPreferences().getString(PREF_CUSTOM_PROMPT, "");
        return (p == null || p.isEmpty()) ? DEFAULT_PROMPT_EN : p;
    }

    public static void setPrompt(@NonNull PluginContext context, @NonNull String prompt) {
        context.getPreferences().edit().putString(PREF_CUSTOM_PROMPT, prompt).apply();
    }

    @NonNull
    public static String getShortPrompt(@NonNull PluginContext context) {
        String p = context.getPreferences().getString(PREF_SHORT_PROMPT, "");
        return (p == null || p.isEmpty()) ? DEFAULT_SHORT_PROMPT_EN : p;
    }

    public static void setShortPrompt(@NonNull PluginContext context, @NonNull String prompt) {
        context.getPreferences().edit().putString(PREF_SHORT_PROMPT, prompt).apply();
    }

    @NonNull
    public static String getQuickPrompts(@NonNull PluginContext context) {
        String prompts = context.getPreferences().getString(PREF_QUICK_PROMPTS, "");
        if (prompts == null || prompts.isEmpty()) {
            JSONArray defaultPrompts = new JSONArray();
            try {
                JSONObject prompt1 = new JSONObject();
                prompt1.put("name", "Analyze Code Obfuscation");
                prompt1.put("prompt", "Analyze whether this code has obfuscation or decryption, point out obfuscation techniques and decryption methods");
                defaultPrompts.put(prompt1);
            } catch (Exception ignored) {
            }
            return defaultPrompts.toString();
        }
        return prompts;
    }

    public static void setQuickPrompts(@NonNull PluginContext context, @NonNull String promptsJson) {
        context.getPreferences().edit().putString(PREF_QUICK_PROMPTS, promptsJson).apply();
    }

    @NonNull
    public static String getSkills(@NonNull PluginContext context) {
        String skills = context.getPreferences().getString(PREF_SKILLS, "");
        return (skills == null || skills.isEmpty()) ? "[]" : skills;
    }

    public static void setSkills(@NonNull PluginContext context, @NonNull String skillsJson) {
        context.getPreferences().edit().putString(PREF_SKILLS, skillsJson).apply();
    }

    public static void resetToDefault(@NonNull PluginContext context) {
        context.getPreferences().edit()
                .remove(PREF_API_URL)
                .remove(PREF_AI_MODEL)
                .remove(PREF_API_KEY)
                .remove(PREF_CUSTOM_PROMPT)
                .remove(PREF_SHORT_PROMPT)
                .remove(PREF_SKILLS)
                .remove(PREF_QUICK_PROMPTS)
                .apply();
    }

    // --- New simplified analyze method (core implementation) ---
    private static final String GEMINI_API_COMPLETIONS = DEFAULT_API_COMPLETIONS;

    @Nullable
    public static String[] analyzeCode(
            @NonNull PluginContext context,
            @NonNull String code,
            @Nullable PluginEditText resultEdit,
            @Nullable PluginDialog dialog) throws Exception {

        String apiKey = getApiKey(context);
        if (apiKey == null) {
            throw new Exception("Gemini API key not configured. Please set it in plugin settings.");
        }

        String prompt = getPrompt(context);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", getAiModel(context));
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

        URL url = new URL(GEMINI_API_COMPLETIONS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("x-goog-api-key", apiKey);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] out = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(out);
            os.flush();
        }

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
                    String data = line.substring(6).trim();
                    if ("[DONE]".equals(data)) {
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
                            } else {
                                String text = choice.optString("text", "");
                                if (text != null && !text.isEmpty()) {
                                    result.append(text);
                                }
                            }
                        }
                    } catch (Exception e) {
                        android.util.Log.w("AIHelper", "Parse error: " + e.getMessage());
                    }
                } else if (!line.trim().isEmpty()) {
                    try {
                        JSONObject full = new JSONObject(line.trim());
                        JSONArray choices = full.optJSONArray("choices");
                        if (choices != null && choices.length() > 0) {
                            JSONObject choice = choices.getJSONObject(0);
                            JSONObject message = choice.optJSONObject("message");
                            if (message != null) {
                                String content = message.optString("content", "");
                                if (!content.isEmpty()) result.append(content);
                            } else {
                                String text = choice.optString("text", "");
                                if (!text.isEmpty()) result.append(text);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        } finally {
            connection.disconnect();
        }

        if (result.length() == 0) {
            throw new Exception("No response from Gemini API");
        }

        return new String[]{result.toString()};
    }

    // --- Backwards-compatible wrappers ---

    @Nullable
    public static String[] analyzeCodeWithAI(
            @NonNull PluginContext context,
            @NonNull String code,
            @Nullable PluginEditText thinkingEdit,
            @Nullable PluginEditText resultEdit,
            @Nullable PluginDialog dialog,
            boolean showThinking,
            @Nullable String customPrompt) throws Exception {
        // If customPrompt provided, temporarily set it, call analyzeCode, then restore
        String old = null;
        if (customPrompt != null) {
            old = context.getPreferences().getString(PREF_CUSTOM_PROMPT, null);
            setPrompt(context, customPrompt);
        }
        try {
            String[] res = analyzeCode(context, code, resultEdit, dialog);
            // maintain old API return shape (result, empty string)
            return new String[]{res[0], ""};
        } finally {
            if (customPrompt != null) {
                if (old == null) context.getPreferences().edit().remove(PREF_CUSTOM_PROMPT).apply();
                else context.getPreferences().edit().putString(PREF_CUSTOM_PROMPT, old).apply();
            }
        }
    }

    @Nullable
    public static String[] analyzeCodeWithAINoUI(
            @NonNull PluginContext context,
            @NonNull String code,
            boolean showThinking,
            @Nullable String customPrompt) throws Exception {
        String old = null;
        if (customPrompt != null) {
            old = context.getPreferences().getString(PREF_CUSTOM_PROMPT, null);
            setPrompt(context, customPrompt);
        }
        try {
            return analyzeCode(context, code, null, null);
        } finally {
            if (customPrompt != null) {
                if (old == null) context.getPreferences().edit().remove(PREF_CUSTOM_PROMPT).apply();
                else context.getPreferences().edit().putString(PREF_CUSTOM_PROMPT, old).apply();
            }
        }
    }

    @Nullable
    public static String[] analyzeCodeWithThinking(
            @NonNull PluginContext context,
            @NonNull String code,
            @NonNull PluginEditText thinkingEdit,
            @NonNull PluginEditText resultEdit,
            @NonNull PluginDialog dialog) throws Exception {
        return analyzeCodeWithAI(context, code, thinkingEdit, resultEdit, dialog, true, null);
    }

    @Nullable
    public static String[] analyzeCodeWithUserPrompt(
            @NonNull PluginContext context,
            @NonNull String code,
            @NonNull String userPrompt,
            @NonNull PluginEditText thinkingEdit,
            @NonNull PluginEditText resultEdit,
            @NonNull PluginDialog dialog) throws Exception {
        String systemPrompt = getPrompt(context);
        String combined = userPrompt + "\n\n" + systemPrompt;
        return analyzeCodeWithAI(context, code, thinkingEdit, resultEdit, dialog, true, combined);
    }

    @Nullable
    public static String[] analyzeCodeWithUserPromptNoUI(
            @NonNull PluginContext context,
            @NonNull String code,
            @NonNull String userPrompt) throws Exception {
        String systemPrompt = getPrompt(context);
        String combined = userPrompt + "\n\n" + systemPrompt;
        return analyzeCodeWithAINoUI(context, code, true, combined);
    }

    @NonNull
    public static String getSkillsSafe(@NonNull PluginContext context) {
        return getSkills(context);
    }

    public static void runOnMainThread(@NonNull Runnable action) {
        new Handler(Looper.getMainLooper()).post(action);
    }
}
