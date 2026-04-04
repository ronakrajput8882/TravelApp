# ✈️ TravelApp

An Android travel application built with **Java** in Android Studio. Users can browse trips, view trip details, make bookings, and manage their profile — all backed by a local **SQLite** database with session management.

---

## 📁 Project Structure

```
TravelApp/
│
├── app/
│   ├── build.gradle                         # App-level Gradle config
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml          # App manifest & activity declarations
│       │   │
│       │   ├── java/com/example/travelapp/
│       │   │   │
│       │   │   ├── activities/              # Screen controllers
│       │   │   │   ├── SplashActivity.java  # Launch screen
│       │   │   │   ├── LoginActivity.java   # User login
│       │   │   │   ├── SignupActivity.java  # New user registration
│       │   │   │   ├── MainActivity.java    # Host for bottom-nav fragments
│       │   │   │   ├── TripDetailActivity.java  # Full trip info & booking CTA
│       │   │   │   └── BookingActivity.java # Manage user bookings
│       │   │   │
│       │   │   ├── fragments/               # In-app navigation screens
│       │   │   │   ├── HomeFragment.java    # Featured & recent trips
│       │   │   │   ├── DashboardFragment.java  # Stats & quick actions
│       │   │   │   ├── TripFragment.java    # Full trip listing
│       │   │   │   └── ProfileFragment.java # User profile & settings
│       │   │   │
│       │   │   ├── adapters/                # RecyclerView adapters
│       │   │   │   ├── TripAdapter.java     # Binds Trip list items
│       │   │   │   └── BookingAdapter.java  # Binds Booking list items
│       │   │   │
│       │   │   ├── models/                  # Data model classes (POJOs)
│       │   │   │   ├── Trip.java            # Trip entity
│       │   │   │   ├── Booking.java         # Booking entity
│       │   │   │   └── User.java            # User entity
│       │   │   │
│       │   │   ├── database/
│       │   │   │   └── DBHelper.java        # SQLiteOpenHelper – CRUD operations
│       │   │   │
│       │   │   └── utils/
│       │   │       ├── Constants.java       # App-wide constants & DB column names
│       │   │       └── SessionManager.java  # SharedPreferences login session
│       │   │
│       │   └── res/
│       │       ├── layout/                  # XML screen & item layouts
│       │       │   ├── activity_main.xml
│       │       │   ├── activity_login.xml
│       │       │   ├── activity_signup.xml
│       │       │   ├── activity_splash.xml
│       │       │   ├── activity_booking.xml
│       │       │   ├── activity_trip_detail.xml
│       │       │   ├── fragment_home.xml
│       │       │   ├── fragment_dashboard.xml
│       │       │   ├── fragment_trips.xml
│       │       │   ├── fragment_profile.xml
│       │       │   ├── item_trip.xml
│       │       │   ├── item_trip_card.xml
│       │       │   ├── item_booking.xml
│       │       │   ├── item_quick_action.xml
│       │       │   └── include_key_details.xml
│       │       │
│       │       ├── drawable/                # Vector icons & shape backgrounds
│       │       │   ├── bg_glass_effect.xml
│       │       │   ├── card_bg.xml
│       │       │   ├── edittext_bg.xml
│       │       │   ├── rounded_edittext.xml
│       │       │   ├── gradient_bg.xml
│       │       │   ├── gradient_overlay.xml
│       │       │   ├── gradient_overlay_premium.xml
│       │       │   ├── placeholder_trip_image.xml
│       │       │   ├── ic_home.xml
│       │       │   ├── ic_dashboard.xml
│       │       │   ├── ic_trips.xml
│       │       │   ├── ic_profile.xml
│       │       │   ├── ic_bookings.xml
│       │       │   ├── ic_add.xml / ic_delete.xml / ic_remove.xml
│       │       │   ├── ic_arrow_back.xml
│       │       │   ├── ic_calendar.xml / ic_clock.xml
│       │       │   ├── ic_email.xml / ic_lock.xml / ic_phone.xml
│       │       │   ├── ic_explore.xml / ic_location_pin.xml
│       │       │   ├── ic_notifications.xml / ic_settings.xml
│       │       │   ├── ic_star.xml / ic_suitcase.xml
│       │       │   ├── ic_people.xml / ic_person.xml
│       │       │   ├── ic_avatar_placeholder.xml
│       │       │   ├── ic_travel_logo.xml
│       │       │   ├── ic_no_bookings.xml
│       │       │   ├── ic_bookmark_outline.xml
│       │       │   └── ic_launcher_background/foreground.xml
│       │       │
│       │       ├── menu/
│       │       │   └── bottom_nav_menu.xml  # Bottom navigation tab items
│       │       │
│       │       ├── navigation/
│       │       │   └── nav_graph.xml        # NavComponent graph (fragments)
│       │       │
│       │       └── values/
│       │           ├── colors.xml
│       │           ├── strings.xml
│       │           ├── themes.xml
│       │           └── dimens.xml
│       │
│       ├── test/                            # Unit tests
│       └── androidTest/                     # Instrumented tests
│
├── build.gradle                             # Project-level Gradle config
├── settings.gradle                          # Module includes
├── gradle.properties                        # Gradle JVM & AndroidX flags
└── restructure.sh                           # Script to migrate flat files → this layout
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Platform | Android (API 21+) |
| Database | SQLite via `SQLiteOpenHelper` |
| Navigation | Jetpack Navigation Component |
| UI | Material Design 3, RecyclerView, BottomNavigationView |
| Session | SharedPreferences (`SessionManager`) |

---

## 🚀 Features

- **Splash Screen** — Branded launch screen with session check
- **Auth Flow** — Login & Signup with SQLite-backed user accounts
- **Home Feed** — Featured trips with card-based UI
- **Trip Listing** — Browse all available trips
- **Trip Detail** — Full trip info (location, price, duration, rating)
- **Booking System** — Book trips and view booking history
- **Dashboard** — Quick stats and action shortcuts
- **Profile** — View and manage user information
- **Bottom Navigation** — Smooth fragment-based navigation

---

## ⚙️ Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) Hedgehog or newer
- Android device / emulator (API 21+)
- JDK 8 or above

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/ronakrajput8882/TravelApp.git
   cd TravelApp
   ```

2. **Reorganize files** *(only needed if files are still flat in root)*
   ```bash
   chmod +x restructure.sh
   ./restructure.sh
   ```

3. **Open in Android Studio**
   - File → Open → select the `TravelApp/` folder
   - Wait for Gradle sync to finish

4. **Run**
   - Choose a device/emulator and click **Run ▶**

---

## 🗃️ Database Schema

### `users` Table
| Column | Type | Description |
|---|---|---|
| `id` | INTEGER PK | Auto-incremented user ID |
| `name` | TEXT | Full name |
| `email` | TEXT UNIQUE | Login email |
| `password` | TEXT | Hashed password |
| `phone` | TEXT | Contact number |

### `trips` Table
| Column | Type | Description |
|---|---|---|
| `id` | INTEGER PK | Auto-incremented trip ID |
| `title` | TEXT | Trip name |
| `destination` | TEXT | Location |
| `price` | REAL | Price per person |
| `duration` | TEXT | e.g. "5 Days / 4 Nights" |
| `rating` | REAL | Average rating (0–5) |
| `description` | TEXT | Full trip description |
| `image_url` | TEXT | Remote or drawable reference |

### `bookings` Table
| Column | Type | Description |
|---|---|---|
| `id` | INTEGER PK | Auto-incremented booking ID |
| `user_id` | INTEGER FK | References `users.id` |
| `trip_id` | INTEGER FK | References `trips.id` |
| `booking_date` | TEXT | Date booked |
| `num_persons` | INTEGER | Number of travellers |
| `total_price` | REAL | Calculated total |
| `status` | TEXT | `pending` / `confirmed` / `cancelled` |

---

## 🤝 Contributing

1. Fork the repo
2. Create your feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -m "Add my feature"`
4. Push: `git push origin feature/my-feature`
5. Open a Pull Request

---

## 📄 License

This project is open source under the [MIT License](LICENSE).

---

## 👤 Author

**Ronak Rajput**
- GitHub: [@ronakrajput8882](https://github.com/ronakrajput8882)

---

> ⭐ Star this repo if you find it helpful!