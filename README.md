# User Management App

A modern Android application for managing user profiles with an intuitive Jetpack Compose UI and local Room database persistence.

## ✨ Features

- 👤 **Add Users** - Create users manually with full form validation or auto-generate random profiles
- ✏️ **Edit User Profiles** - Update existing user names, email addresses, and phone numbers
- 🔍 **Real-time Search** - Filter registered users dynamically with clearable search input
- 👥 **User Initials Avatar** - Visual Material 3 avatar badges with user initials
- 📊 **User Count & Empty States** - Header badge showing registered user counts and informative empty state screens
- 🔐 **Update Passwords** - Secure password modification with validation and visibility toggles
- 🗑️ **Delete Users** - Remove users safely with confirmation dialogs
- 💾 **Local Database** - Persistent Room database caching with reactive Flow streams
- 🎨 **Modern UI** - Material Design 3 and Jetpack Compose

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material Design 3)
- **Database:** Room (SQLite) with KSP
- **Architecture:** MVVM (Model-View-ViewModel) with Kotlin Coroutines & StateFlow
- **Minimum SDK:** 24 | **Target SDK:** 37
- **CI/CD:** GitHub Actions

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 17
- Android SDK 37

### Clone and Run
```bash
git clone https://github.com/Rajapakshaminindu/user-management-android.git
cd user-management-android
./gradlew assembleDebug
```

### Running Unit Tests
```bash
./gradlew test
```

---

## 📁 Project Structure

```
app/src/
├── main/
│   ├── java/com/example/activity_02/
│   │   ├── MainActivity.kt               # Main entry activity
│   │   ├── data/
│   │   │   ├── User.kt                   # Room user entity
│   │   │   ├── UserDao.kt                # Database access object
│   │   │   ├── UserRepository.kt         # Repository pattern
│   │   │   ├── AppDatabase.kt            # Room database definition
│   │   │   └── validation/
│   │   │       └── UserValidator.kt      # Form input validation logic
│   │   └── ui/
│   │       ├── UserListScreen.kt         # Jetpack Compose UI screens & dialogs
│   │       ├── UserViewModel.kt          # StateFlow & coroutine management
│   │       └── theme/                    # Material 3 theme colors & typography
└── test/
    └── java/com/example/activity_02/
        ├── data/UserTest.kt              # User entity unit tests
        └── validation/UserValidatorTest.kt # Validator test suite
```

---

## 🤝 Contributing

Please see [CONTRIBUTING.md](CONTRIBUTING.md) for development workflows and submission guidelines.
