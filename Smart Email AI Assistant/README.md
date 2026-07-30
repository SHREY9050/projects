# 📧 Email Reply Generator

An AI-powered Email Reply Generator that helps users generate professional, casual, or friendly email responses instantly.

## 🚀 Features

* Generate email replies using AI
* Multiple tone options:

  * Professional
  * Casual
  * Friendly
* Clean and responsive UI
* Copy generated replies with one click
* Loading indicators during generation
* Error handling for failed requests

---

## 🛠️ Tech Stack

### Frontend

* React.js
* Material UI (MUI)
* Axios

### Backend

* Spring Boot
* REST API

### AI Integration

* Google Gemini API

---

## 📂 Project Structure

```text
email-reply-generator/
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── application.properties
│
└── README.md
```

---

## ⚙️ Installation

### 1. Clone Repository

```bash
git clone https://github.com/your-username/email-reply-generator.git
cd email-reply-generator
```

---

### 2. Backend Setup

Navigate to backend directory:

```bash
cd backend
```

Configure your Gemini API key:

```properties
gemini.api.key=YOUR_API_KEY
```

Run Spring Boot application:

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

### 3. Frontend Setup

Navigate to frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start development server:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

## 📡 API Endpoint

### Generate Email Reply

**POST**

```http
/api/email/generate
```

### Request Body

```json
{
  "emailContent": "Can you provide the project update?",
  "tone": "professional"
}
```

### Response

```text
Dear Team,

Thank you for your email.

I will review the project status and provide a detailed update shortly.

Best Regards,
Your Name
```

---

## 🎯 Usage

1. Paste the original email content.
2. Select a tone (optional).
3. Click **Generate Reply**.
4. Review the generated response.
5. Copy the reply using the **Copy to Clipboard** button.

---

## 📸 Screenshots

Add screenshots of your application here.

---

## 🔮 Future Enhancements

* Email templates
* Dark mode
* Reply history
* Multiple AI providers
* Export as PDF
* Browser extension support

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Open a Pull Request

---

## 📜 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

Shrey Agarwal

Built with React, Spring Boot, and Gemini AI.
