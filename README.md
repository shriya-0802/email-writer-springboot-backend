

```markdown
# ✨ Email Writer Assistant

An AI-powered email reply generator that helps you write professional email responses instantly. The complete system includes a Spring Boot backend, React frontend dashboard, and Chrome extension for Gmail integration.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)
![React](https://img.shields.io/badge/React-18.3-blue)
![Chrome Extension](https://img.shields.io/badge/Chrome%20Extension-MV3-yellow)
![License](https://img.shields.io/badge/License-MIT-green)

## 🚀 Features

- 🤖 **AI-Powered Replies** - Uses Google's Gemini AI to generate context-aware responses
- 🎨 **Multiple Tones** - Professional, friendly, urgent, formal, and casual tones
- 🔌 **Gmail Integration** - Chrome extension adds "AI Reply" button directly in Gmail
- 💻 **React Dashboard** - Modern web interface to test and manage email generation
- ⚡ **Real-time Generation** - Instant AI responses with JSON and text endpoints
- 📱 **Responsive Design** - Works on desktop, tablet, and mobile devices

## 📋 Prerequisites

- Java 17 or higher
- Node.js 18+ and npm
- Chrome browser (for extension)
- Google Gemini API key ([Get it here](https://makersuite.google.com/app/apikey))
- Maven 3.9+

## 🏗️ Project Structure

```
email-writer-assistant/
├── backend/                 # Spring Boot application
│   ├── src/main/java/com/email/writer/
│   │   ├── app/
│   │   │   ├── EmailGeneratorController.java
│   │   │   ├── EmailGeneratorService.java
│   │   │   ├── EmailRequest.java
│   │   │   └── EmailResponse.java
│   │   ├── config/
│   │   │   └── WebClientConfig.java
│   │   └── EmailWriterSbApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/                # React application
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── App.js
│   │   └── index.js
│   ├── public/
│   ├── package.json
│   └── vite.config.js
├── extension/               # Chrome extension
│   ├── background.js
│   ├── content.js
│   ├── content.css
│   ├── manifest.json
│   ├── popup.html
│   ├── popup.js
│   └── icons/
└── README.md
```

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/shriya-0802/email-writer-assistant.git
cd email-writer-assistant
```

### 2. Backend Setup (Spring Boot)

```bash
cd backend

# Configure Gemini API key
# Edit src/main/resources/application.properties
# Add: gemini.api.key=YOUR_GEMINI_API_KEY_HERE

# Build and run
mvn clean install
mvn spring-boot:run
```

**Backend runs at:** `http://localhost:8080`

### 3. Frontend Setup (React)

```bash
cd frontend

# Install dependencies
npm install

# Create environment file
echo "VITE_API_BASE_URL=http://localhost:8080/api/email" > .env.local

# Start development server
npm run dev
```

**Frontend runs at:** `http://localhost:5173`

### 4. Chrome Extension Setup

```bash
cd extension
# No build needed - files are ready
```

**Load in Chrome:**
1. Open `chrome://extensions/`
2. Enable "Developer mode"
3. Click "Load unpacked"
4. Select the `extension` folder

## ⚙️ Configuration

### Backend (`application.properties`)

```properties
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=
gemini.api.key=YOUR_GEMINI_API_KEY_HERE
server.port=8080
logging.level.com.email.writer=DEBUG
```

### Frontend (`.env.local`)

```env
VITE_API_BASE_URL=http://localhost:8080/api/email
VITE_API_TIMEOUT=30000
```

### Getting Gemini API Key

1. Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Sign in with Google account
3. Click "Create API Key"
4. Copy the generated key
5. Paste in `application.properties`

## 📖 Usage Guide

### React Dashboard

1. Start backend: `cd backend && mvn spring-boot:run`
2. Start frontend: `cd frontend && npm run dev`
3. Open `http://localhost:5173`
4. Enter email content and select tone
5. Click "Generate Reply"

### Chrome Extension in Gmail

1. Ensure backend is running on port 8080
2. Open Gmail (`https://mail.google.com`)
3. Open any email and click "Reply"
4. Click "✨ AI Reply" button in toolbar
5. AI reply is automatically inserted

### API Testing (cURL)

```bash
# JSON endpoint
curl -X POST http://localhost:8080/api/email/generate \
  -H "Content-Type: application/json" \
  -d '{"emailContent":"Hello, need help","tone":"professional"}'

# Text endpoint
curl -X POST http://localhost:8080/api/email/generate/text \
  -H "Content-Type: application/json" \
  -H "Accept: text/plain" \
  -d '{"emailContent":"Need report","tone":"urgent"}'
```

## 📡 API Endpoints

### POST `/api/email/generate` (JSON Response)

**Request:**
```json
{
  "emailContent": "Dear Team, I need the report by tomorrow",
  "tone": "professional"
}
```

**Response:**
```json
{
  "generatedEmail": "Dear Team,\n\nThank you for your message...",
  "success": true,
  "errorMessage": null
}
```

### POST `/api/email/generate/text` (Plain Text)

**Request:**
```json
{
  "emailContent": "Can you send the documents?",
  "tone": "urgent"
}
```

**Response:**
```
Dear User,

I understand the urgency...
```

### Available Tones

| Tone | Description | Best For |
|------|-------------|----------|
| professional | Formal business tone | Work emails |
| friendly | Warm and approachable | Colleagues |
| urgent | Time-sensitive | Deadlines |
| formal | Highly professional | Official |
| casual | Relaxed | Informal chats |

## 🛠️ Technology Stack

### Backend
- Spring Boot 3.3.5
- Java 17
- Google Gemini AI API
- Spring WebFlux (WebClient)
- Maven

### Frontend
- React 18.3
- Tailwind CSS 3.4
- Axios
- Vite

### Chrome Extension
- Manifest V3
- JavaScript ES6+
- Chrome APIs

## 🐛 Troubleshooting

### Backend won't start
```bash
# Check if port 8080 is in use
lsof -i :8080  # Mac/Linux
netstat -ano | findstr :8080  # Windows

# Kill process or change port in application.properties
```

### "Invalid Gemini API key"
- Verify API key in `application.properties`
- Check if API key has billing enabled
- Try generating a new API key

### Extension button not showing
- Refresh Gmail (Ctrl+Shift+R)
- Reload extension in `chrome://extensions/`
- Click "Reply" button first
- Check console for errors (F12)

### "Cannot connect to backend"
- Verify backend is running on port 8080
- Check firewall settings
- Update API URL in `background.js`

### CORS errors
```java
// Add to controller
@CrossOrigin(origins = "*")
```

## 🚀 Deployment

### Backend (Render)

```yaml
# render.yaml
services:
  - type: web
    name: email-writer-backend
    runtime: java
    envVars:
      - key: GEMINI_API_KEY
        sync: false
```

### Frontend (Vercel)

```bash
cd frontend
npm run build
vercel --prod
```

### Extension (Chrome Web Store)

1. Create `.crx` file
2. Go to Chrome Web Store Developer Dashboard
3. Upload extension
4. Submit for review




## 👤 Author

**Shriya Mohanty**

- GitHub: [@shriya-0802](https://github.com/shriya-0802)


