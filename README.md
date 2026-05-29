# MTKang Plugin - MT Manager V3 AI Intelligent Programming Assistant

<div align="center">

[![MT Manager](https://img.shields.io/badge/MT%20Manager-V3-blue.svg)](https://mt2.cn)
[![Plugin Version](https://img.shields.io/badge/Version-v2.0.2-green.svg)](https://github.com)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-21-orange.svg)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**An intelligent programming assistant plugin based on AI, providing comprehensive features including intelligent code analysis, encoding conversion, and development assistance for MT Manager text editor**

[Core Features](#-core-features) • [Quick Start](#-quick-start) • [Usage Guide](#-usage-guide) • [Technical Architecture](#-technical-architecture) • [FAQ](#-faq)

</div>

---

## ☕ Sponsorship Support

<div align="center">

**If this project is helpful to you, we welcome your sponsorship to support continuous maintenance and updates!**

</div>

> 💡 **About Sponsorship**: This open-source plugin and accompanying free AI model face increasing server, computing power, and operational costs that individuals can no longer sustain long-term. To prevent service interruption and ensure future updates and maintenance, we're seeking community sponsorship support.
>
> All sponsorship funds will be used for server renewals, computing costs, and project maintenance. Thank you for your long-term support; together we can keep open-source and free AI services going strong.

<div align="center">

### 💝 Special Thanks

| Sponsor | Amount |
|---------|--------|
| **You Cheng** | ¥100 |

### 📱 Sponsorship Methods

| WeChat Pay | Alipay |
|:--------:|:------:|
| ![WeChat QR Code](src/main/assets/wx.jpg) | ![Alipay QR Code](src/main/assets/zfb.jpg) |

**Your every bit of support is my motivation to move forward! Thank you for your recognition and encouragement!**

</div>

---

## 📋 Table of Contents

- [Plugin Introduction](#-plugin-introduction)
- [Core Features](#-core-features)
  - [AI Code Analysis](#ai-code-analysis)
  - [AI Quick Analysis](#ai-quick-analysis)
  - [Custom AI Configuration](#custom-ai-configuration)
- [Helper Tools](#-helper-tools)
  - [Encoding/Decoding Tools](#encodingdecoding-tools)
  - [Hash Calculation](#hash-calculation)
  - [Timestamp Conversion](#timestamp-conversion)
  - [Quick Time Insertion](#quick-time-insertion)
- [Quick Start](#-quick-start)
- [Usage Guide](#-usage-guide)
- [Technical Architecture](#-technical-architecture)
- [FAQ](#-faq)
- [Changelog](#-changelog)
- [Contact](#-contact)

---

## 📖 Plugin Introduction

**MTKang Plugin** is an enhanced plugin for MT Manager V3 designed specifically for developers, with **AI Intelligent Programming Assistant** as its core, deeply integrated with AI models to provide intelligent code analysis for mobile programming. 

The plugin also integrates multiple practical development helper tools, including encoding/decoding, hash calculation, timestamp conversion, time insertion, and other commonly used functions to meet the diverse needs of developers in daily programming.

### Applicable Scenarios

- 📱 **Mobile Programming Learning** - Get code analysis and suggestions anytime, anywhere
- 🔍 **Code Quality Checking** - Quickly discover potential issues and optimization opportunities
- 🛠️ **Development Efficiency Boost** - One-click encoding conversion, hash calculation, and other quick tools
- 🌐 **Multi-language Development** - Supports 10 language interfaces, suitable for international use

---

## ✨ Core Features

### 🤖 AI Code Analysis

**The core feature of the plugin**, providing intelligent code analysis services based on AI models:

#### Feature Highlights

- **Full Text Analysis** - Deep analysis of all code in the editor
- **Selected Analysis** - Analyze only the selected code snippet for quick diagnosis
- **Thinking Process Display** - Show AI reasoning process in real-time to understand analysis approach
- **Streaming Output** - Return analysis results in real-time without long waits
- **Intelligent Suggestions** - Provide code optimization suggestions, problem diagnosis, and best practices
- **Background Running** - Support background analysis with pop-up results upon completion
- **Custom Prompts** - Support quick prompts and custom Skills

#### Analysis Content

AI analysis provides professional evaluation from the following dimensions:

1. **Code Functionality Overview** - Brief description of the main functionality
2. **Code Quality Assessment** - Point out code strengths and issues
3. **Potential Problems** - Identify possible bugs, performance issues, or security risks
4. **Optimization Suggestions** - Provide specific improvement suggestions and best practices
5. **Code Standards** - Check if code complies with common coding standards

#### Usage

In MT Manager text editor:

- **Full Text Analysis** - Click **AI Code Analysis** in the top "Edit" menu
- **Quick Analysis** - Select code, then click **AI Quick Analysis** in the floating menu

---

### ⚡ AI Quick Analysis

Quick diagnosis feature for selected code snippets:

- **Precise Targeting** - Analyze only selected code for better accuracy
- **Instant Feedback** - Quick analysis results to improve efficiency
- **Use Cases** - Check specific functions, code blocks, or suspicious code segments
- **Quick Prompts** - Support one-click append of preset prompts
- **Skill Selection** - Support selecting custom Skills to append to prompts

---

### 🔧 Custom AI Configuration

The plugin provides flexible AI configuration management where users can customize according to needs:

#### Configuration Items

| Configuration Item | Default Value | Description |
|--------|--------|------|
| **API Address** | `https://api.kggzs.cn/v1` | Base URL for AI API |
| **Model Name** | `MT-v1` | AI model to use |
| **API Key** | `sk-MT-kggzs-API-key` | Access key (provided by default) |
| **Global Analysis Prompt** | Built-in professional prompt | Used for full text analysis |
| **Quick Analysis Prompt** | Built-in quick prompt | Used for quick analysis |
| **Quick Prompts** | Preset prompt list | One-click append during analysis |
| **Custom Skills** | User-defined | Can be appended to end of prompts |

#### Configuration Method

1. **Unified API Configuration** - Complete API address, model name, and key configuration on one page
2. **Independent Prompt Configuration** - Support custom multi-line prompts for different analysis needs
3. **Quick Prompt Management** - Configure up to 10 quick prompts
4. **Skill Management** - Add, edit, delete, and apply custom Skills
5. **One-Click Reset** - Quickly restore all default settings

> 💡 **Tip**: If you have your own AI API service, you can modify it to a custom address and key in settings.

---

## 🛠️ Helper Tools

In addition to core AI features, the plugin provides multiple practical development helper tools:

### 🔐 Encoding/Decoding Tools

Provides comprehensive text encoding and decoding functions, supporting:

- **Base64 Encoding/Decoding** - Standard Base64 encoding/decoding
- **Hex (Hexadecimal) Encoding/Decoding** - Hexadecimal format conversion
- **Unicode Encoding/Decoding** - Unicode escape sequence conversion
- **URL Encoding/Decoding** - URL parameter encoding/decoding
- **ROT13 Encoding/Decoding** - Caesar cipher variant
- **Binary Encoding/Decoding** - Binary format conversion

### 🔢 Hash Calculation

Quickly calculate hash values of text, supporting:

- **MD5** - 128-bit hash value, commonly used for file verification
- **SHA-256** - 256-bit secure hash algorithm
- **SHA-512** - 512-bit secure hash algorithm

> 💡 **Usage**: Quickly generate text fingerprints for file integrity verification, password encryption, and more.

### ⏰ Timestamp Conversion

Support bi-directional conversion between timestamps and date-time:

- **Timestamp to Date** - Convert numeric timestamp to readable date format
- **Date to Timestamp** - Convert date string to timestamp
- **Multiple Format Support** - Auto-recognize formats like `yyyy-MM-dd`, `yyyy/MM/dd`, etc.

### ⚡ Quick Time Insertion

One-click insert current date and time at cursor position, supporting multiple formats:

#### Display Modes

| Mode | Description | Example |
|------|------|------|
| **Without Time** | Display date only | May 20, 2026 |
| **With Time** | Display date + hour:minute:second | May 20, 2026 19:29:55 |
| **Custom Format** | Use custom format | According to user settings |

#### Preset Formats

- **Gregorian Calendar Format**
  - Standard Chinese format: May 20, 2026
  - ISO format: 2026-05-20
  - Slash format: 2026/5/20
  - Compact format: 20260520
  - With week: May 20, 2026 Wednesday

- **Lunar Calendar Format**
  - Traditional Chinese: Year of Dragon, Fourth Month, Fourth Day
  - Lunar short: Lunar Fourth Month, Fourth Day
  - Heavenly Stems format: Year of Dragon, Fourth Month, Fourth Day
  - Lunar + Arabic numerals: Lunar 2026 Fourth Month, Fourth Day
  - Combined: 2026-05-20 (Year of Dragon, Fourth Month, Fourth Day)

#### Custom Format

Support using format tags to customize output format:

| Tag | Description | Example |
|------|------|------|
| yyyy | 4-digit year | 2026 |
| yy | 2-digit year | 26 |
| MM | Zero-padded month | 05 |
| M | Month | 5 |
| N | Lunar month | Fourth Month |
| dd | Zero-padded day | 20 |
| e | Lunar day | Fourth |
| E | Weekday | Wednesday |
| a | Time period | Morning/Afternoon |
| aa | Precise time period | Evening/Early Morning |
| HH | 24-hour with padding | 19 |
| mm | Zero-padded minute | 08 |
| ss | Zero-padded second | 55 |
| l | Hour name | Rooster |

**Example**: `N Month e E a H:mm l Hour` → `Fourth Month Fourth Wednesday Afternoon 19:29 Rooster Hour`

---

### 🌐 Multi-language Support

The plugin supports 10 languages:

| Language | Filename | Description |
|------|--------|------|
| **Chinese (Default)** | `strings.mtl` | Default language |
| **Simplified Chinese** | `strings-zh-CN.mtl` | Simplified Chinese |
| **Traditional Chinese** | `strings-zh-TW.mtl` | Traditional Chinese |
| **Japanese** | `strings-ja.mtl` | Japanese |
| **Korean** | `strings-ko.mtl` | Korean |
| **Arabic** | `strings-ar.mtl` | Arabic |
| **German** | `strings-de.mtl` | German |
| **Spanish** | `strings-es.mtl` | Spanish |
| **French** | `strings-fr.mtl` | French |
| **Russian** | `strings-ru.mtl` | Russian |

> 💡 **Tip**: Language automatically switches based on your device system language without manual configuration.

---

## 🚀 Quick Start

### Environment Requirements

- **MT Manager**: 2.26.3+ (requires VIP permission)
- **Android Version**: 5.0+ (API 21+)
- **Development Tools**: Android Studio Hedgehog (2023.1.1)+
- **Build Tools**: AGP 8.13.2+, Gradle 8.13
- **Java Version**: Java 17

### Install Plugin

#### Method 1: Build from Source

1. **Clone Project**
   ```bash
   git clone https://github.com/kggzs/MT_Plugin.git
   cd mt-kang
   ```

2. **Build Plugin**
   ```bash
   # Build using Gradle
   ./gradlew packageReleaseMtp
   ```

3. **Install Plugin**
   - Generated `.mtp` file located at: `build/outputs/mt-plugin/`
   - Copy `.mtp` file to your device
   - Open and install in MT Manager

#### Method 2: Direct Installation

1. Download the latest `.mtp` file
2. Click the file in MT Manager
3. Follow prompts to complete installation

### First Use

After installation, you can use plugin features in the following scenarios:

- **Text Editor** - Open text editor and find plugin features in toolbar or floating menu
- **Plugin Management** - View plugin information and settings in MT Manager's plugin management

---

## 📖 Usage Guide

### Encoding/Decoding Tools

#### How to Open

In MT Manager text editor:
- Select text, then click **Encoding/Decoding** in the floating menu
- Or find the Encoding/Decoding icon in the editor toolbar

#### Instructions

1. **Input Text**
   - Enter or paste text to process in the input box at the top
   - If text is already selected, it will auto-fill

2. **Select Operation**
   - Click corresponding button (such as Base64 Encode, Hex Decode, etc.)
   - Processing result displays in the input box

3. **Undo and Replace**
   - **Undo** - Restore to original text
   - **Replace** - Replace processed text back to editor

#### Hash Calculation

1. Enter text to calculate hash
2. Click **Hash Calculation** button
3. Select hash type (MD5, SHA-256, SHA-512)
4. Click **Copy** to copy result to clipboard

#### Timestamp Conversion

Supports auto-recognition of the following formats:
- `yyyy-MM-dd HH:mm:ss`
- `yyyy/MM/dd HH:mm:ss`
- `yyyy-MM-dd HH:mm`
- `yyyy/MM/dd HH:mm`
- `yyyy-MM-dd`
- `yyyy/MM/dd`

**Timestamp to Date**:
1. Enter timestamp (seconds or milliseconds)
2. Click **Timestamp Conversion**
3. Auto-recognize and convert to date format

**Date to Timestamp**:
1. Enter date-time string
2. Click **Timestamp Conversion**
3. Convert to seconds-level timestamp

---

### AI Code Analysis

#### How to Open

In MT Manager text editor:

- **Full Text Analysis** - Click **AI Code Analysis** in top "Edit" menu
- **Quick Analysis** - Select code, then click **AI Quick Analysis** in floating menu

#### Usage Process

1. **Prepare Code**
   - Full Text Analysis: No need to select, click menu directly
   - Quick Analysis: Select the code snippet to analyze first

2. **Set Prompts**
   - Enter custom prompts in the pop-up dialog
   - Click quick prompt buttons to append presets
   - Optionally select custom Skills to append to end of prompts

3. **Wait for Analysis**
   - AI analyzes using configured API and prompts
   - Real-time display of thinking process and results
   - Can choose "Background Run" to not block current interface

4. **View Results**
   - Pop-up result dialog after analysis completion
   - View complete analysis results
   - Support copying analysis results

#### Custom Configuration

In the plugin settings **AI Code Analysis Configuration** group, you can:

1. **API Configuration** - Click "API Configuration" and configure in pop-up dialog:
   - **API Address** - Base URL for AI API
   - **Model Name** - AI model name to use
   - **API Key** - AI API access key
   - Click "Save" button to save all configuration
   - Click "Reset" button to reset to defaults

2. **AI Capability Configuration** - Click "AI Capability Configuration":
   - **Global Analysis Prompt** - System prompt for full text analysis
   - **Quick Analysis Prompt** - System prompt for quick analysis
   - **Quick Prompt Management** - Add/edit/delete quick prompts (max 10)
   - **Skill Management** - Add/edit/delete/apply custom Skills

3. **Reset Configuration** - Click "Reset Configuration" to restore all settings to defaults

> 💡 **Tip**: All configuration automatically saves; next use will apply these settings directly.

---

### Quick Time Insertion

#### How to Use

1. In MT Manager text editor
2. Move cursor to position where you want to insert time
3. Click **Insert Time** button in floating menu
4. Auto-insert current date and time

#### Time Format Settings

Click "Time Format Settings" in plugin settings:

**Display Mode**:
- **Without Time** - Display date only, no hours:minutes:seconds
- **With Time** - Display date + hours:minutes:seconds
- **Custom Format** - Use custom format

**Preset Format Selection**:

- **Gregorian Calendar Format**
  - Standard Chinese format: May 20, 2026
  - ISO format: 2026-05-20
  - Slash format: 2026/5/20
  - Compact format: 20260520
  - With week: May 20, 2026 Wednesday

- **Lunar Calendar Format**
  - Traditional Chinese: Year of Dragon, Fourth Month, Fourth Day
  - Lunar short: Lunar Fourth Month, Fourth Day
  - Heavenly Stems format: Year of Dragon, Fourth Month, Fourth Day
  - Lunar + Arabic numerals: Lunar 2026 Fourth Month, Fourth Day
  - Combined: 2026-05-20 (Year of Dragon, Fourth Month, Fourth Day)

**Custom Format Editing**:

After clicking "Custom Format", you can enter custom format codes supporting the following tags:

| Tag | Description | Tag | Description |
|------|------|------|------|
| yyyy | 4-digit year | yy | 2-digit year |
| MM | Zero-padded month | M | Month |
| N | Lunar month | dd | Zero-padded day |
| d | Day | e | Lunar day |
| E | Weekday | a | Time period |
| aa | Precise time period | HH | 24-hour with padding |
| H | 24-hour | mm | Zero-padded minute |
| ss | Zero-padded second | l | Hour name |

---

### Plugin Settings

#### How to Open

- Find **MTKang Plugin** in MT Manager's plugin management
- Click to enter settings interface

#### Settings Content

Settings interface provides:
- Plugin name and author information
- Official plugin website and open source address
- Support author (sponsorship QR codes)
- AI Code Analysis configuration
- Time format settings
- Usage instructions for each feature

---

## 🏗️ Technical Architecture

### Core Technology Stack

| Technology | Version/Description |
|------|-----------|
| **Development Language** | Java 17 |
| **Build Tools** | Gradle 8.13, AGP 8.13.2 |
| **Android SDK** | Min SDK 21, Target SDK 28, Compile SDK 36 |
| **Plugin Framework** | MT Plugin API V3 (1.0.0-beta6) |
| **HTTP Communication** | HttpURLConnection |
| **JSON Parsing** | org.json.* |
| **Async Processing** | Thread + Handler/Looper |
| **Multi-language Support** | 10 languages |

### Architecture Design

```
MTKang Plugin
├── Encoding/Decoding Module (EncodeDecodeMenu)
│   ├── Base64 Encoding/Decoding
│   ├── Hex Encoding/Decoding
│   ├── Unicode Encoding/Decoding
│   ├── URL Encoding/Decoding
│   ├── ROT13 Encoding/Decoding
│   ├── Binary Encoding/Decoding
│   ├── Hash Calculation (MD5, SHA-256, SHA-512)
│   └── Timestamp Conversion
├── AI Analysis Module
│   ├── Full Text Analysis (AICodeAnalysisToolMenu)
│   ├── Selection Analysis (AICodeAnalysisFloatingMenu)
│   └── AI Analysis Helper (AICodeAnalysisHelper)
│       ├── Prompt Input Dialog
│       ├── Quick Prompt Buttons
│       ├── Skill Multi-select
│       ├── Analysis Process Control
│       └── Markdown Cleanup
├── Utility Classes
│   ├── AIHelper - AI network requests and configuration management
│   ├── TimeFormatHelper - Time format configuration
│   └── LunarCalendar - Lunar calendar calculation
├── Quick Features
│   └── Quick Time Insertion (QuickInsertFunction)
└── Settings Module
    └── Preferences (MyPreference)
```

### API Endpoints

| Usage | Default Value |
|------|--------|
| **API Address** | `https://api.kggzs.cn/v1` |
| **Model Name** | `MT-v1` |
| **API Key** | `sk-MT-kggzs-API-key` |

> 💡 All API configuration can be customized in the plugin settings interface

---

## 📁 Project Structure

```
mt-kang/
├── src/main/
│   ├── java/com/kggzs/cn/mt/
│   │   ├── EncodeDecodeMenu.java              # Encoding/Decoding floating menu
│   │   ├── AICodeAnalysisToolMenu.java        # AI code analysis tool menu
│   │   ├── AICodeAnalysisFloatingMenu.java    # AI quick analysis floating menu
│   │   ├── AICodeAnalysisHelper.java          # AI analysis helper class
│   │   ├── QuickInsertFunction.java           # Quick time insertion feature
│   │   ├── MyPreference.java                  # Plugin preference settings
│   │   └── util/
│   │       ├── AIHelper.java                  # AI utility class (core network logic)
│   │       ├── TimeFormatHelper.java          # Time format configuration tool
│   │       └── LunarCalendar.java             # Lunar calendar calculation tool
│   ├── assets/
│   │   ├── strings.mtl                        # Default language resources
│   │   ├── strings-zh-CN.mtl                  # Simplified Chinese
│   │   ├── strings-zh-TW.mtl                  # Traditional Chinese
│   │   ├── strings-ja.mtl                     # Japanese
│   │   ├── strings-ko.mtl                     # Korean
│   │   ├── strings-ar.mtl                     # Arabic
│   │   ├── strings-de.mtl                     # German
│   │   ├── strings-es.mtl                     # Spanish
│   │   ├── strings-fr.mtl                     # French
│   │   ├── strings-ru.mtl                     # Russian
│   │   ├── wx.jpg                             # WeChat QR code
│   │   └── zfb.jpg                            # Alipay QR code
│   ├── resources/
│   │   └── icon.png                           # Plugin icon
│   └── AndroidManifest.xml
├── docs/                                      # Documentation directory
├── gradle/
│   └── libs.versions.toml                     # Version directory
├── build.gradle                               # Project build configuration
├── settings.gradle                            # Gradle settings
├── gradle.properties                          # Gradle properties
├── proguard-rules.pro                         # Obfuscation rules
├── BUILD.md                                   # Build instructions
└── README.md                                  # Project documentation
```

---

## 🛠️ Development Guide

### Development Environment Setup

1. **Install Android Studio**
   - Recommended version: Hedgehog (2023.1.1) or later

2. **Import Project**
   ```bash
   # Clone project
   git clone https://github.com/kggzs/MT_Plugin.git
   cd mt-kang
   
   # Open in Android Studio
   ```

3. **Sync Gradle**
   - After opening, wait for Gradle to auto-sync
   - Or manually click "Sync Project with Gradle Files"

### Build Plugin

#### Debug Build

```bash
# Build debug version
./gradlew assembleDebug

# Run directly in Android Studio
# Click the run button in top-right corner
```

#### Release Build

```bash
# Package Release MTP file
./gradlew packageReleaseMtp

# Output location
# build/outputs/mt-plugin/
```

### Plugin Configuration

Configure plugin information in `build.gradle`:

```gradle
mtPlugin {
    pushTarget = "auto"                // Push target (auto-detect)
    pluginID = "com.kggzs.cn.mt"       // Plugin ID
    versionCode = 4                    // Version code
    versionName = "v2.0.2"             // Version name
    name = "{plugin_name}"             // Plugin name (supports localization)
    description = "{plugin_description}" # Plugin description
    mainPreference = "com.kggzs.cn.mt.MyPreference"
    interfaces = [
        "com.kggzs.cn.mt.EncodeDecodeMenu",
        "com.kggzs.cn.mt.QuickInsertFunction",
        "com.kggzs.cn.mt.AICodeAnalysisToolMenu",
        "com.kggzs.cn.mt.AICodeAnalysisFloatingMenu"
    ]
}
```

### Code Standards

- Follow Java code standards
- Use meaningful variable and method names
- Add necessary comments
- Perfect error handling
- Timely resource cleanup

---

## ❓ FAQ

### Q1: Can't see features after installing plugin?

**A**: Please check:
1. Is MT Manager version V3 with VIP permission
2. Is plugin successfully installed (check in plugin management)
3. Are you using in correct scenario (text editor)

### Q2: AI code analysis not working?

**A**: Possible reasons:
1. Network connection issue - Ensure device can access configured API address
2. Invalid API key - Check and update API key in settings
3. Wrong model name - Confirm model name is correct
4. Empty selected code - Ensure text is selected (quick analysis)

### Q3: How to modify AI prompts?

**A**: 
1. Open plugin settings interface
2. Find "AI Code Analysis Configuration" group
3. Click "AI Capability Configuration"
4. Edit global analysis prompt or quick analysis prompt
5. Click "Save" button

### Q4: Encoding/decoding results incorrect?

**A**: 
1. Check if input text format is correct
2. Confirm selected encoding/decoding type matches
3. Check for error messages

### Q5: How to contribute code?

**A**: 
1. Fork this repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Submit Pull Request

---

## 📝 Changelog

### v2.0.3 (Current Version)

- ✨ Time insertion added three display modes (without time/with time/custom)
- ✨ Time insertion added custom format feature supporting 18 format tags
- ✨ Time insertion supports hour names, precise time periods, and traditional time representation
- 🔧 Optimized time format settings interface using buttons instead of checkboxes
- 🔧 Optimized custom format edit dialog with detailed format instructions

### v2.0.2

- 🌐 Support 10 languages: Chinese, Traditional Chinese, Japanese, Korean, Arabic, German, Spanish, French, Russian, English
- 🔧 AI analysis supports background running mode
- 🔧 Added quick prompt feature (up to 10)
- 🔧 Added custom Skill feature
- 🔧 Time insertion supports multiple formats (Gregorian/Lunar/Heavenly Stems)
- 🔧 Settings interface supports sponsorship QR codes

### v2.0.1

- 🔧 AI configuration supports custom API address, model, key, and prompts
- 🔧 Removed cloud key acquisition logic, changed to local default configuration
- 🔧 Settings interface new AI configuration group with visual configuration management
- 🔧 Added configuration reset feature

### v2.0

- ✨ Added encoding/decoding tools supporting Base64, Hex, Unicode, URL, ROT13, binary encoding/decoding
- ✨ Added hash calculation (MD5, SHA-256, SHA-512)
- ✨ Added timestamp conversion
- ✨ Added AI code analysis supporting full text and selection analysis
- ✨ Added quick time insertion
- ✨ Improved plugin settings interface

### v1.0

- 🎉 Initial release
- 📝 Basic project structure setup

---

## 📄 License

This project uses MIT License. See [LICENSE](LICENSE) file for details.

---

## 📞 Contact

- **Author**: 康哥 (KangGe)
- **Website**: [www.kggzs.cn](http://www.kggzs.cn)
- **GitHub**: [https://github.com/kggzs/MT_Plugin](https://github.com/kggzs/MT_Plugin)
- **Issue Feedback**: Submit issues on GitHub

---

## 🙏 Acknowledgments

Thanks to the following open-source projects and services:

- [MT Manager](https://mt2.cn) - Powerful Android file management tool
- [DeepSeek](https://deepseek.com) - AI model provider

---

<div align="center">

**If this project helps you, please give it a ⭐ Star to support us!**

Made with ❤️ by 康哥

</div>
