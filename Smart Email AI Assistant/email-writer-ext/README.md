# 📧 Email Writer Extension

<p align="center">
  <img src="email_ext_logo.png" alt="Email Writer Extension Logo" width="180">
</p>

An AI-powered Chrome Extension that integrates directly with Gmail and generates intelligent email replies using the Email Reply Generator backend powered by Gemini AI.

---

## 🚀 Project Overview

This project was created by transforming an existing **Email Reply Generator Web Application** into a **Chrome Extension** that works directly inside Gmail.

### Original Application

The original application consisted of:

* React Frontend
* Spring Boot Backend
* Gemini AI Integration

Users had to:

1. Open the web application.
2. Copy email content from Gmail.
3. Paste it into the application.
4. Generate a reply.
5. Copy the generated response.
6. Paste it back into Gmail.

While functional, this workflow required multiple manual steps.

### Extension Version

To improve productivity, the application was converted into a Gmail-integrated Chrome Extension.

Now users can:

1. Open Gmail.
2. Click **AI Reply**.
3. Generate a response instantly.
4. Automatically insert the reply into the compose window.

No copy-pasting required.

---

## 🏗️ Conversion Architecture

### Before

```text
Gmail
   ↓
Copy Email
   ↓
Web Application
   ↓
Generate Reply
   ↓
Copy Response
   ↓
Paste Into Gmail
```

### After

```text
Gmail
   ↓
Chrome Extension
   ↓
Spring Boot API
   ↓
Gemini AI
   ↓
Generated Reply
   ↓
Inserted Into Gmail
```

---

## ⚙️ How The Conversion Works

### Step 1: Keep Existing Backend

The Spring Boot backend remained unchanged.

Existing endpoint:

```http
POST /api/email/generate
```

Request:

```json
{
  "emailContent": "Project update request",
  "tone": "professional"
}
```

Response:

```text
Generated AI Reply
```

The extension simply consumes this API.

---

### Step 2: Create Chrome Extension

A new Chrome Extension project was created using:

```text
Manifest V3
JavaScript
HTML
CSS
```

Project Structure:

```text
email-writer-ext/
│
├── manifest.json
├── content.js
├── content.css
├── email_ext_logo.png
└── README.md
```

---

### Step 3: Inject Content Script Into Gmail

The extension injects JavaScript into Gmail pages.

```json
{
  "content_scripts": [
    {
      "matches": ["*://mail.google.com/*"],
      "js": ["content.js"]
    }
  ]
}
```

This allows the extension to interact with Gmail's interface.

---

### Step 4: Detect Compose Windows

A MutationObserver monitors Gmail for new compose and reply windows.

```javascript
const observer = new MutationObserver(...)
```

Whenever Gmail creates a reply box, the extension detects it automatically.

---

### Step 5: Add AI Reply Button

The extension dynamically creates:

```text
AI Reply
```

and inserts it into Gmail's toolbar.

Users can generate responses directly from Gmail.

---

### Step 6: Extract Email Content

When the AI Reply button is clicked:

```javascript
getEmailContent()
```

extracts the original email content from Gmail.

Example:

```text
Hi,

Can you provide the latest project update?

Regards,
John
```

---

### Step 7: Send Request To Backend

The extension sends the email content to:

```http
http://localhost:8080/api/email/generate
```

using:

```javascript
fetch(...)
```

---

### Step 8: Generate AI Response

Backend Flow:

```text
Spring Boot
      ↓
Gemini AI
      ↓
Generated Reply
```

Example Output:

```text
Dear John,

Thank you for your email.

I will review the project status and provide a detailed update shortly.

Best Regards,
Shrey
```

---

### Step 9: Insert Reply Into Gmail

The extension automatically finds Gmail's editable compose box:

```javascript
[role="textbox"][g_editable="true"]
```

and inserts the generated response.

The user can immediately send the email.

---

## 🔧 Installation

### Clone Repository

```bash
git clone https://github.com/yourusername/email-writer-ext.git
```

### Open Chrome Extensions

```text
chrome://extensions
```

### Enable Developer Mode

Turn on:

```text
Developer Mode
```

### Load Extension

Click:

```text
Load Unpacked
```

and select:

```text
email-writer-ext/
```

---

## 🧪 Testing

1. Start Spring Boot backend.
2. Open Gmail.
3. Open an email.
4. Click Reply.
5. Click AI Reply.
6. Wait for response generation.
7. Verify generated reply appears automatically.

---

## 🛠️ Technologies Used

### Frontend

* React (Original Web App)
* Material UI

### Backend

* Spring Boot
* REST API

### AI

* Google Gemini API

### Extension

* Manifest V3
* JavaScript
* Gmail DOM Manipulation
* MutationObserver

---

## 🔮 Future Improvements

* Multiple tone selection
* Custom prompts
* Dark mode
* Outlook support
* Regenerate response
* Streaming AI replies
* Settings popup

---

## 👨‍💻 Author

Shrey Agarwal

Built with Spring Boot, Gemini AI, Gmail Integration, and Chrome Extension APIs.

---

## 📜 License

MIT License
