# Expense Tracker 💰 | Full Stack Application

A full-stack personal finance management app that helps users track expenses, incomes, and spending categories — built to improve my backend & frontend skills while working with real-world technologies.

---

## 🚀 Features

- ✅ User authentication (Register/Login with JWT)
- ✅ Secure APIs with Spring Boot & JWT
- ✅ Add, list, and delete transactions
- ✅ Manage categories
- ✅ Record incomes separately
- ✅ Protected routes on frontend
- ✅ Clean modular code structure
- ✅ Ready for cloud deployment

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Security + JWT Authentication
- JPA + Hibernate
- PostgreSQL (or H2 for development)
- Swagger / OpenAPI Docs
- Docker & Docker Compose
- Deployed on **Google Cloud Run**

### Frontend
- React + Vite
- React Router
- Context API (Auth management)
- Tailwind CSS
- Fetch wrapper for API communication

---

## 🌩️ Cloud & DevOps

| Tool | Usage |
|------|--------|
| Google Cloud Run | Deploy backend as a container |
| Google Cloud Build | Build Docker images |
| Docker Desktop | Containerization |
| GitHub | Version control / project hosting |

---

## 📦 Project Structure

expense-tracker/
│
├── backend/ # Spring Boot API
│ ├── controller/ # REST endpoints
│ ├── service/ # Business logic
│ ├── repository/ # JPA Repositories
│ ├── model/ # Entities
│ ├── security/ # JWT security
│ ├── config/ # Global configs
│ ├── dto/ # Request/Response objects
│ └── Dockerfile
│
├── expense-tracker-ui/ # Frontend (React + Vite)
│ ├── modules/
│ ├── shared/
│ ├── components/
│ └── vite.config.js
│
└── README.md

yaml
Copy code

---

## 🔐 API Authentication

JWT token system:
- Login returns a token
- Every protected endpoint requires `Authorization: Bearer <token>`
- Token validation handled by **JwtAuthFilter** in backend

---

## 🧪 Local Setup

### Prerequisites
- Node.js & npm
- Java 17
- Docker Desktop
- Git

### Run Backend
```bash
cd backend
mvn clean package
java -jar target/*.jar
Run Frontend
bash
Copy code
cd expense-tracker-ui
npm install
npm run dev
🐳 Docker Support
Backend can run in Docker:

bash
Copy code
docker build -t expense-tracker .
docker run -p 8080:8080 expense-tracker
🌍 Deployment
The backend is ready for deployment using Google Cloud Run:

bash
Copy code
gcloud builds submit --tag gcr.io/<PROJECT_ID>/expense-tracker
gcloud run deploy expense-tracker --image gcr.io/<PROJECT_ID>/expense-tracker --platform managed
🎯 Purpose of This Project
I built this project to:

Improve my Full-Stack engineering skills

Practice clean backend architecture

Work with JWT authentication

Learn Docker & Cloud deployment

Build an end-to-end real-world app

Prepare a strong portfolio project

🔮 Future Improvements
✅ Charts & Analytics dashboard

✅ Export reports (PDF/Excel)

✅ Recurring transactions

✅ Multi-language interface

✅ Dark mode UI

✅ Mobile App (React Native)

✅ Budget goals & savings tracker

📬 Contact
Feel free to reach out if you’d like to collaborate!

Email: your-email@example.com
LinkedIn: https://linkedin.com/in/your-profile
GitHub: https://github.com/your-username

⭐ If you like this project, give it a star on GitHub! ⭐
