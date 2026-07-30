# 🛡️ Phishing Message Checker

An AI-powered web app that analyzes suspicious messages (emails, SMS, chat messages) and flags potential phishing attempts using Google's Gemini API. Built with Flask, it returns a structured risk assessment — risk level, reasoning, and recommended action — in a clean, easy-to-read report format.

## Features

- 🔍 **AI-driven analysis** — sends user-submitted messages to Gemini for phishing risk classification
- 🚦 **Structured risk levels** — High / Medium / Low, visually color-coded
- 📋 **Clear reasoning** — explains *why* a message was flagged
- 🛠️ **Actionable advice** — tells the user what to do next
- 🔁 **Retry/backoff handling** — gracefully handles API rate limits (HTTP 429)
- 🧹 **Robust JSON parsing** — strips markdown code fences Gemini sometimes wraps responses in
- 📱 **Responsive UI** — clean card-based layout that adapts to mobile

## Tech Stack

| Layer      | Technology                     |
|------------|---------------------------------|
| Backend    | Python, Flask                  |
| AI Model   | Google Gemini API (`gemini-flash-latest`) |
| Frontend   | HTML, Jinja2 templating, CSS   |
| HTTP       | `requests` library              |

## Project Structure

```
phishing-checker/
├── app.py                 # Flask app + Gemini API integration
├── templates/
│   └── index.html          # Main UI template
├── static/
│   └── style.css           # Styling
└── README.md
```

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd phishing-checker
   ```

2. **Install dependencies**
   ```bash
   pip install flask requests
   ```

3. **Set your Gemini API key** as an environment variable (never hardcode it):
   ```bash
   # Linux/macOS
   export GEMINI_API_KEY="your_api_key_here"

   # Windows (Command Prompt)
   set GEMINI_API_KEY="your_api_key_here"
   ```
   Get a free API key from [Google AI Studio](https://ai.google.dev/).

4. **Run the app**
   ```bash
   python app.py
   ```

5. Open `http://127.0.0.1:5000` in your browser.

## How It Works

1. User pastes a suspicious message into the text box and submits the form.
2. The Flask backend wraps the message in a structured prompt and sends it to the Gemini API.
3. Gemini returns a JSON-formatted risk assessment (`risk_level`, `reason`, `advice`).
4. The app parses the response (handling cases where Gemini wraps JSON in markdown fences) and renders it as a color-coded report card.
5. If the API is rate-limited (HTTP 429), the app retries with backoff and shows a clear, user-friendly error if the limit persists.

## Known Limitations

- **Free-tier quota**: Google's free tier caps requests per day per model (e.g. 20/day). Heavy testing will hit this limit — see [Gemini API rate limits](https://ai.google.dev/gemini-api/docs/rate-limits) for details.
- **Not a substitute for professional judgment**: This tool provides an AI-generated risk estimate, not a definitive security verdict. Always verify suspicious messages through official channels.
- **No persistent history**: Currently the app doesn't store past analyses; each check is stateless.

## Potential Improvements

- [ ] Add request caching to reduce redundant API calls for identical messages
- [ ] Store analysis history in a database (SQLite/PostgreSQL)
- [ ] Add user authentication for multi-user deployments
- [ ] Support batch analysis (multiple messages at once)
- [ ] Add unit tests for JSON parsing and error-handling logic
- [ ] Deploy with a paid Gemini tier or fallback model for production use

## Disclaimer

This tool uses a third-party AI model (Gemini) to assess phishing risk and may occasionally produce incorrect or incomplete assessments. It is intended as a supplementary screening aid, not a replacement for established security practices or professional threat analysis.

## Author

Built by Shrey — a project exploring AI-assisted security tooling, API integration, and Flask web development.
