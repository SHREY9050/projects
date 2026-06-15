Extension Version

To improve productivity, the application was converted into a Gmail-integrated Chrome Extension.

Now users can:

Open Gmail.
Click AI Reply.
Generate a response instantly.
Automatically insert the reply into the compose window.

No copy-pasting required.

  🏗️ Conversion Architecture
Before
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
After
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
⚙️ How The Conversion Works
Step 1: Keep Existing Backend

The Spring Boot backend remained unchanged.

Existing endpoint:

POST /api/email/generate

Request:

{
  "emailContent": "Project update request",
  "tone": "professional"
}

Response:

Generated AI Reply

The extension simply consumes this API.

Step 2: Create Chrome Extension

A new Chrome Extension project was created using:

Manifest V3
JavaScript
HTML
CSS

Project Structure:

email-writer-ext/
│
├── manifest.json
├── content.js
├── content.css
├── email_ext_logo.png
└── README.md
Step 3: Inject Content Script Into Gmail

The extension injects JavaScript into Gmail pages.

{
  "content_scripts": [
    {
      "matches": ["*://mail.google.com/*"],
      "js": ["content.js"]
    }
  ]
}

This allows the extension to interact with Gmail's interface.

Step 4: Detect Compose Windows

A MutationObserver monitors Gmail for new compose and reply windows.

const observer = new MutationObserver(...)

Whenever Gmail creates a reply box, the extension detects it automatically.

Step 5: Add AI Reply Button

The extension dynamically creates:

AI Reply

and inserts it into Gmail's toolbar.

Users can generate responses directly from Gmail.

Step 6: Extract Email Content

When the AI Reply button is clicked:

getEmailContent()

extracts the original email content from Gmail.

Example:

Hi,

Can you provide the latest project update?

Regards,
John
Step 7: Send Request To Backend

The extension sends the email content to:

http://localhost:8080/api/email/generate

using:

fetch(...)
Step 8: Generate AI Response

Backend Flow:

Spring Boot
      ↓
Gemini AI
      ↓
Generated Reply

Example Output:

Dear John,

Thank you for your email.

I will review the project status and provide a detailed update shortly.

Best Regards,
Shrey
Step 9: Insert Reply Into Gmail

The extension automatically finds Gmail's editable compose box:

[role="textbox"][g_editable="true"]

and inserts the generated response.

The user can immediately send the email.

🔧 Installation
Clone Repository
git clone https://github.com/yourusername/email-writer-ext.git
Open Chrome Extensions
chrome://extensions
Enable Developer Mode

Turn on:

Developer Mode
Load Extension

Click:

Load Unpacked

and select:

email-writer-ext/
🧪 Testing
Start Spring Boot backend.
Open Gmail.
Open an email.
Click Reply.
Click AI Reply.
Wait for response generation.
Verify generated reply appears automatically.
🛠️ Technologies Used
Frontend
React (Original Web App)
Material UI
Backend
Spring Boot
REST API
AI
Google Gemini API
Extension
Manifest V3
JavaScript
Gmail DOM Manipulation
MutationObserver
🔮 Future Improvements
Multiple tone selection
Custom prompts
Dark mode
Outlook support
Regenerate response
Streaming AI replies
Settings popup
👨‍💻 Author

Shrey Agarwal

Built with Spring Boot, Gemini AI, Gmail Integration, and Chrome Extension APIs.
