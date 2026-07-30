from flask import Flask, render_template, request
import requests
import json
import re
import os

app = Flask(__name__)

# ⚠️ Store your key as an environment variable instead of hardcoding it:
# export GEMINI_API_KEY="your_key_here"   (Linux/Mac)
# set GEMINI_API_KEY="your_key_here"      (Windows)
# API_KEY = os.environ.get("GEMINI_API_KEY", "HIDDEN")

API_KEY = "YOUR_GEMINI_API"
API_URL = f"YOUR_GEMINI_URL?key={API_KEY}"


def extract_json(text):
    """Strip markdown code fences (```json ... ```) if Gemini adds them."""
    cleaned = re.sub(r"^```(?:json)?\s*|\s*```$", "", text.strip(), flags=re.MULTILINE)
    return cleaned


@app.route("/", methods=["GET", "POST"])
def index():
    result = None
    user_message = ""

    if request.method == "POST":
        user_message = request.form["message"]

        prompt = f"""You are a phishing detection assistant.
Analyze the message below and respond with ONLY a valid JSON object, no markdown, no extra text.
Format exactly like this:
{{"risk_level": "High|Medium|Low", "reason": "one short sentence", "advice": "one short recommended action"}}

Message: "{user_message}"
"""

        payload = {
            "contents": [
                {"parts": [{"text": prompt}]}
            ]
        }

        headers = {"Content-Type": "application/json"}

        try:
            response = requests.post(API_URL, json=payload, headers=headers, timeout=15)
        except requests.exceptions.RequestException as e:
            result = {"error": f"Request failed: {e}"}
            return render_template("index.html", result=result, user_message=user_message)

        if response.status_code == 200:
            data = response.json()
            try:
                ai_reply = data["candidates"][0]["content"]["parts"][0]["text"]
            except (KeyError, IndexError):
                result = {"error": "Unexpected response format from Gemini API."}
                return render_template("index.html", result=result, user_message=user_message)

            cleaned = extract_json(ai_reply)

            try:
                parsed = json.loads(cleaned)
                result = {
                    "risk_level": parsed.get("risk_level", "Unknown"),
                    "reason": parsed.get("reason", "No explanation provided."),
                    "advice": parsed.get("advice", "Exercise caution.")
                }
            except json.JSONDecodeError:
                result = {"raw": ai_reply}
        else:
            result = {"error": f"Error {response.status_code}: {response.text}"}

    return render_template("index.html", result=result, user_message=user_message)


if __name__ == "__main__":
    app.run(debug=True, use_reloader=False)