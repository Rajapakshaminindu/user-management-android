# User Management App

A modern Android application for managing user data with a clean, intuitive Compose-based UI.

## 📋 Repository Information

**Suggested Repository Name:** `user-management-android`

**Description:** A Jetpack Compose-based Android application for managing user profiles with local database persistence using Room. Features include adding, searching, updating passwords, and deleting users with a Material Design 3 interface.

---

## ✨ Features

- ✅ **Add Users** - Create new user profiles with random generated data
- 🔍 **Search Users** - Real-time filtering of users by name, email, or phone
- 🔐 **Update Passwords** - Securely update user passwords
- 🗑️ **Delete Users** - Remove users with confirmation dialog
- 💾 **Local Database** - Persistent storage using Room
- 🎨 **Modern UI** - Material Design 3 with Jetpack Compose

---

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Database:** Room (SQLite)
- **Architecture:** MVVM (Model-View-ViewModel)
- **API Level:** Min SDK 24, Target SDK 37
- **Build System:** Gradle with Kotlin DSL

### Key Dependencies
- `androidx.compose` - Modern declarative UI framework
- `androidx.room` - Local database with Room ORM
- `androidx.lifecycle` - Lifecycle-aware components
- `androidx.material3` - Material Design 3 components

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK 37

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/user-management-android.git
   cd user-management-android
   ```

2. **Open in Android Studio**
   - File → Open → Select the project directory
   - Wait for Gradle sync to complete

3. **Build & Run**
   ```bash
   ./gradlew assembleDebug
   ```
   - Or use Android Studio's Run button

---

## 📱 App Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/activity_02/
│   │   │   ├── MainActivity.kt          # Main entry point
│   │   │   ├── data/
│   │   │   │   ├── User.kt              # Entity model
│   │   │   │   ├── UserDao.kt           # Database access
│   │   │   │   ├── UserRepository.kt    # Repository pattern
│   │   │   │   └── AppDatabase.kt       # Room database
│   │   │   └── ui/
│   │   │       ├── UserListScreen.kt    # Main UI screen
│   │   │       ├── UserViewModel.kt     # ViewModel logic
│   │   │       └── theme/               # Material 3 theme
│   │   └── AndroidManifest.xml
│   ├── androidTest/                     # Instrumented tests
│   └── test/                            # Unit tests
└── build.gradle.kts                     # App-level configuration
```

---

## 🎯 Usage

### Main Screen Features

1. **Top Bar** - Displays "Registered Users" title
2. **Search Field** - Filter users in real-time
3. **User Cards** - Display user information
4. **FAB (Floating Action Button)** - Add new random users
5. **User Actions** - Edit password or delete user

### Sample User Data
- Name, Email, Phone, Password are auto-generated
- Users are stored locally in the device database

---

## 🔧 Development

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Building Release APK
```bash
./gradlew assembleRelease
```

### Code Format
The project follows Kotlin style guidelines. Format code with:
```bash
./gradlew ktlintFormat
```

---

## 📊 Database Schema

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Primary key (auto-increment) |
| name | TEXT | User's full name |
| email | TEXT | User's email address |
| phone | TEXT | User's phone number |
| password | TEXT | User's password |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

Created for Activity_02 Android Development Course/Assignment

---

## 🆘 Troubleshooting

| Issue | Solution |
|-------|----------|
| Gradle sync fails | Clean project: `./gradlew clean`, then sync again |
| Build errors | Ensure JDK 11+ is installed and set in Project Settings |
| Database errors | Clear app data from device settings and rebuild |
| Compose preview not showing | Invalidate caches: File → Invalidate Caches |

---

## 📚 Resources

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Material Design 3](https://m3.material.io/)

---

**Last Updated:** 2024 | Android Studio Project
