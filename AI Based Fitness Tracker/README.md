# AI Based Fitness Tracker

A standalone full-stack fitness tracker created from the business capabilities of the separate `fitness-microservice backend app` reference project. It does not import, modify, or depend on that project.

## Included functionality

- Secure registration, login, logout, session expiry handling, and protected APIs
- PBKDF2 password hashing; passwords are never returned from the API or stored in frontend state
- Role-aware authorization (`ADMIN` for the first registered account, `USER` afterwards)
- Activity creation and personal activity history
- Persistent local JSON database at `data/fitness-tracker.json`
- AI-style per-activity coaching with improvement, suggestion, and safety guidance
- Responsive dashboard, activity form, recommendations, profile, loading/empty/error states

## Run

```powershell
cd "AI Based Fitness Tracker"
npm start
```

Open `http://localhost:3000`. Register the first account, then sign in and record an activity.

For development, copy `.env.example` to `.env` and set a strong `JWT_SECRET`. The app has no external runtime dependencies.

## API

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout` (authenticated)
- `GET /api/auth/me` (authenticated)
- `POST /api/activities` (authenticated)
- `GET /api/activities` (authenticated)
- `GET /api/recommendations` (authenticated)
- `GET /api/admin/users` (administrator only)

The browser is served by the same Node server, avoiding cross-origin browser integration issues in local development. API requests carry a short-lived signed access token from session storage. Logging out clears it; a missing/invalid/expired token returns `401` and returns the client to sign-in.

## Verify

```powershell
npm test
```

