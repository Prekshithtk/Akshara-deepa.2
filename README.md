# Akshara-Deepa Tutor

Offline Android self-study companion for 10th-grade SSLC students in rural areas.

## Stack

- Java, XML layouts, ViewBinding
- Android SDK 34, min SDK 24, target SDK 34
- Gradle 8.2 / Android Gradle Plugin 8.2.2 / JDK 17
- Room SQLite, RecyclerView, Material Components
- MPAndroidChart radar chart
- SharedPreferences for login and settings

## Setup

1. Open this folder in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or Android device.

The app stores all data locally and does not require internet, Firebase, backend services, or API keys.

## Demo Login

Create any username/password on first launch. The same credentials are stored locally for offline login.

## Features

- Offline login with remembered session
- Dashboard with progress, daily goal, streak, subjects, quote
- Expandable subject/chapter tracker with Room-persisted completion
- Chapter quiz with 30-second question timer, previous/next, answer review, final score
- Radar strength map updated from quiz results
- Daily goal tracking, streaks, badges, dark mode toggle, chapter search
