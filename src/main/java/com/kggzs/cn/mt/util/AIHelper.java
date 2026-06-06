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
import java.util.Locale;

import bin.mt.plugin.api.PluginContext;
import bin.mt.plugin.api.ui.PluginEditText;
import bin.mt.plugin.api.ui.dialog.PluginDialog;

/**
 * AI 工具类，封装 AI API 调用的公共逻辑
 * 支持通过设置自定义配置 API 地址、模型、密钥和提示词
 * 默认使用 Google Gemini API
 */
public class AIHelper {
    // 默认配置 - Google Gemini
    private static final String DEFAULT_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/";
    private static final String DEFAULT_AI_MODEL = "gemini-2.0-flash";
    private static final String DEFAULT_API_KEY = "";
    private static final boolean IS_GEMINI_API = true;
    
    // 英文提示词
    private static final String DEFAULT_PROMPT_EN = "You are a senior code security analysis expert, proficient in Android reverse engineering using MT Manager, skilled in Smali/Java code auditing";
    private static final String DEFAULT_SHORT_PROMPT_EN = "Please briefly analyze the following code, point out main issues and improvement suggestions:";
    private static final String DEFAULT_QUICK_PROMPT_1_EN = "Analyze whether this code has obfuscation or decryption, point out obfuscation techniques and decryption methods";
    
    // 中文提示词
    private static final String DEFAULT_PROMPT_CN = "你是资深代码安全分析专家，精通MT管理器安卓逆向分析，擅长smali/Java代码审计。请严格按照用户后续指定的要求进行分析";
    private static final String DEFAULT_SHORT_PROMPT_CN = "请简要分析以下代码，指出主要问题和改进建议：";
    private static final String DEFAULT_QUICK_PROMPT_1_CN = "分析此代码是否存在混淆或解密情况，指出混淆技术和解密方法";

    // SharedPreferences 键名
    private static final String PREF_API_URL = "ai_api_url";
    private static final String PREF_AI_MODEL = "ai_model";
    private static final String PREF_API_KEY = "ai_api_key";
    private static final String PREF_CUSTOM_PROMPT = "ai_custom_prompt";
    private static final String PREF_SHORT_PROMPT = "ai_short_prompt";
    private static final String PREF_SKILLS = "ai_skills";
    private static final String PREF_QUICK_PROMPTS = "ai_quick_prompts";

    @NonNull
    private static boolean isEnglish() {
        String language = Locale.getDefault().getLanguage();
        return language.startsWith("en");
    }

    @NonNull
    private static String getDefaultPrompt() {
        return isEnglish() ? DEFAULT_PROMPT_EN : DEFAULT_PROMPT_CN;
    }

    @NonNull
    private static String getDefaultShortPrompt() {
        return isEnglish() ? DEFAULT_SHORT_PROMPT_EN : DEFAULT_SHORT_PROMPT_CN;
    }

    @NonNull
    private static String getDefaultQuickPrompt1() {
        return isEnglish() ? DEFAULT_QUICK_PROMPT_1_EN : DEFAULT_QUICK_PROMPT_1_CN;
    }

    @NonNull
    private static String getAnalyzeCodePrefix() {
        return isEnglish() ? "Please analyze the following code:\n\n" : "请分析以下代码：\n\n";
    }

    @NonNull
    public static String getApiUrl(@NonNull PluginContext context) {
        String url = context.getPreferences().getString(PREF_API_URL, "");
        return url.isEmpty() ? DEFAULT_API_URL : url;
    }

    @NonNull
    public static String getAiModel(@NonNull PluginContext context) {
        String model = context.getPreferences().getString(PREF_AI_MODEL, "");
        return model.isEmpty() ? DEFAULT_AI_MODEL : model;
    }

    @NonNull
    public static String getApiKey(@NonNull PluginContext context) {
        String key = context.getPreferences().getString(PREF_API_KEY, "");
        return key.isEmpty() ? DEFAULT_API_KEY : key;
    }

    @NonNull
    public static String getPrompt(@NonNull PluginContext context) {
        String prompt = context.getPreferences().getString(PREF_CUSTOM_PROMPT, "");
        return prompt.isEmpty() ? getDefaultPrompt() : prompt;
    }

    @NonNull
    public static String getShortPrompt(@NonNull PluginContext context) {
        String prompt = context.getPreferences().getString(PREF_SHORT_PROMPT, "");
        return prompt.isEmpty() ? getDefaultShortPrompt() : prompt;
    }

    public static void setApiUrl(@NonNull PluginContext context, @NonNull String url) {
        context.getPreferences().edit().putString(PREF_API_URL, url).apply();
    }

    public static void setAiModel(@NonNull PluginContext context, @NonNull String model) {
        context.getPreferences().edit().putString(PREF_AI_MODEL, model).apply();
    }

    public static void setApiKey(@NonNull PluginContext context, @NonNull String key) {
        context.getPreferences().edit().putString(PREF_API_KEY, key).apply();
    }

    public static void setPrompt(@NonNull PluginContext context, @NonNull String prompt) {
        context.getPreferences().edit().putString(PREF_CUSTOM_PROMPT, prompt).apply();
    }

    public static void setShortPrompt(@NonNull PluginContext context, @NonNull String prompt) {
        context.getPreferences().edit().putString(PREF_SHORT_PROMPT, prompt).apply();
    }

    @NonNull
    public static String getQuickPrompts(@NonNull PluginContext context) {
        String prompts = context.getPreferences().getString(PREF_QUICK_PROMPTS, "");
        if (prompts.isEmpty()) {
            JSONArray defaultPrompts = new JSONArray();
            try {
                JSONObject prompt1 = new JSONObject();
                prompt1.put("name", isEnglish() ? "Analyze Code Obfuscation" : "分析代码混淆");
                prompt1.put("prompt", getDefaultQuickPrompt1());
                defaultPrompts.put(prompt1);
            } catch (Exception e) {
                android.util.Log.e("AIHelper", "创建默认快速提示词失败", e);
            }
            return defaultPrompts.toString();
        }
        return prompts;
    }

    public static void setQuickPrompts(@NonNull PluginContext context, @NonNull String promptsJson) {
        context.getPreferences().edit().putString(PREF_QUICK_PROMPTS, promptsJson).apply();
    }

    @Nullable
    public static String[] analyzeCodeWithCustomPrompt(
            @NonNull PluginContext context,
            @NonNull String code,
            @NonNull String customPrompt,
            @NonNull PluginEditText thinkingEdit,
            @NonNull PluginEditText resultEdit,
            @NonNull PluginDialog dialog) throws Exception {
        return analyzeCodeWithAI(context, code, thinkingEdit, resultEdit, dialog, true, customPrompt);
    }

    @NonNull
    public static String getSkills(@NonNull PluginContext context) {
        return context.getPreferences().getString(PREF_SKILLS, "[]");
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
        String combinedSystemPrompt = userPrompt + "\n\n" + systemPrompt;
        return analyzeCodeWithAI(context, code, thinkingEdit, resultEdit, dialog, true, combinedSystemPrompt);
    }

    @Nullable
    public static String[] analyzeCodeWithUserPromptNoUI(
            @NonNull PluginContext context,
            @NonNull String code,
            @NonNull String userPrompt) throws Exception {
        String systemPrompt = getPrompt(context);
        String combinedSystemPrompt = userPrompt + "\n\n" + systemPrompt;
        return analyzeCodeWithAINoUI(context, code, true, combinedSystemPrompt);
    }

    @Nullable
    public static String[] analyzeCodeWithAI(
            @NonNull PluginContext context,
            @NonNull String code,
            @Nullable PluginEditText thinkingEdit,
            @Nullable PluginEditText resultEdit,
            @Nullable PluginDialog dialog,
            boolean showThinking,
            @Nullable String customPrompt) throws Exception {

        String apiUrl = getApiUrl(context);
        String aiModel = getAiModel(context);
        String apiKey = getApiKey(context);
        String prompt = (customPrompt != null && !customPrompt.isEmpty()) ? customPrompt : getPrompt(context);

        // Build URL without API key (will go in header)
        String completionsUrl = apiUrl.endsWith("openai/") ? apiUrl + "chat/completions" : apiUrl;

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", aiModel);
        requestBody.put("stream", true);

        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", prompt);
        messages.put(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", getAnalyzeCodePrefix() + code);
        messages.put(userMessage);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        URL url = new URL(completionsUrl);
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
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                }
            }
            connection.disconnect();
            throw new Exception("Gemini API error: " + responseCode + " - " + errorResponse.toString());
        }

        StringBuilder fullContent = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String data = line.substring(6);
                    if (data.equals("[DONE]")) {
                        break;
                    }

                    try {
                        JSONObject chunk = new JSONObject(data);
                        JSONArray choices = chunk.optJSONArray("choices");
                        if (choices != null && choices.length() > 0) {
                            JSONObject firstChoice = choices.getJSONObject(0);
                            if (firstChoice != null) {
                                String content = null;

                                JSONObject delta = firstChoice.optJSONObject("delta");
                                if (delta != null) {
                                    content = delta.optString("content", "");
                                }

                                if (content == null || content.isEmpty()) {
                                    content = firstChoice.optString("text", "");
                                }

                                if (content == null || content.isEmpty()) {
                                    content = firstChoice.optString("content", "");
                                }

                                if (content != null && !content.isEmpty() && !"null".equals(content)) {
                                    fullContent.append(content);
                                    if (resultEdit != null) {
                                        final String currentContent = fullContent.toString();
                                        runOnMainThread(() -> {
                                            resultEdit.setText(currentContent);
                                            resultEdit.selectEnd();
                                        });
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        android.util.Log.w("AIHelper", "JSON parse error: " + e.getMessage());
                    }
                }
            }
        }

        if (fullContent.length() == 0) {
            throw new Exception("Gemini API returned empty result");
        }

        String result = fullContent.toString();
        return new String[]{result, ""};
    }

    public static void runOnMainThread(@NonNull Runnable action) {
        new Handler(Looper.getMainLooper()).post(action);
    }

    @Nullable
    public static String[] analyzeCodeWithAINoUI(
            @NonNull PluginContext context,
            @NonNull String code,
            boolean showThinking,
            @Nullable String customPrompt) throws Exception {

        String apiUrl = getApiUrl(context);
        String aiModel = getAiModel(context);
        String apiKey = getApiKey(context);
        String prompt = (customPrompt != null && !customPrompt.isEmpty()) ? customPrompt : getPrompt(context);

        // Build URL without API key (will go in header)
        String completionsUrl = apiUrl.endsWith("openai/") ? apiUrl + "chat/completions" : apiUrl;

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", aiModel);
        requestBody.put("stream", true);

        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", prompt);
        messages.put(systemMessage);

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", getAnalyzeCodePrefix() + code);
        messages.put(userMessage);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        URL url = new URL(completionsUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-goog-api-key", apiKey);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(120000);
        connection.setDoOutput(true);

        connection.getOutputStream().write(requestBody.toString().getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            java.io.InputStream errorStream = connection.getErrorStream();
            StringBuilder errorResponse = new StringBuilder();
            if (errorStream != null) {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                }
            }
            connection.disconnect();
            throw new Exception("Gemini API error: " + responseCode + " - " + errorResponse.toString());
        }

        StringBuilder fullContent = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String data = line.substring(6);
                    if (data.equals("[DONE]")) {
                        break;
                    }

                    try {
                        JSONObject chunk = new JSONObject(data);
                        JSONArray choices = chunk.optJSONArray("choices");
                        if (choices != null && choices.length() > 0) {
                            JSONObject firstChoice = choices.getJSONObject(0);
                            if (firstChoice != null) {
                                String content = null;

                                JSONObject delta = firstChoice.optJSONObject("delta");
                                if (delta != null) {
                                    content = delta.optString("content", "");
                                }

                                if (content == null || content.isEmpty()) {
                                    content = firstChoice.optString("text", "");
                                }

                                if (content == null || content.isEmpty()) {
                                    content = firstChoice.optString("content", "");
                                }

                                if (content != null && !content.isEmpty() && !"null".equals(content)) {
                                    fullContent.append(content);
                                }
                            }
                        }
                    } catch (Exception e) {
                        android.util.Log.w("AIHelper", "JSON parse error: " + e.getMessage());
                    }
                }
            }
        }

        if (fullContent.length() == 0) {
            throw new Exception("Gemini API returned empty result");
        }

        String result = fullContent.toString();
        return new String[]{result};
    }
}
