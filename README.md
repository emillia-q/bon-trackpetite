# 🥘 Bon TrackPETite
## An Android application for monitoring pet nutrition through Firebase data integration.

## 📲 About the Project
**Bon TrackPETite** is an integral part of //link Pet Appetite Monitor project. Its main purpose is to provide pet owners a **user-friendly interface** to access nutrition data collected by an **ESP-based hardware system**.
By fetching records directly from **Firebase**, it eliminates the need for manual SD card retrieval.
The project is based on a **custom interactive calendar**. When a user selects a date, the app displays a detailed log of the pet's meals if there are any data of the selected day.
This makes it easier to track habits or share the information with a veterinarian.

---

## 🛠️ Technologies Used
* **Firebase Realtime Database**: Used for real-time data synchronization and persistent storage of records.
* **Recycler View & Custom Adapters**: Implemented complex list management for the calendar grid and daily logs. Featuring:
  * **Dynamic Grid Logic**: Calculation to determine if the grid should display 5 or 6 rows (35 or 42 cells) based on the month's length and starting day.
  * **Data Mapping**: Transforming Firebase snapshots into Kotlin data classes (`WeightData`).
* **Glide**: Media loading library used for handling GIF animations to provide visual feedback for empty data states.
* **UI/UX Feedback**: Integrated a **Progress Bar** to signal background data opeartions, ensuring the user is informed while the app communicates with Firebase.
* **Vector Assets & Selectors**: Used for creating responsive and interactive UI with custom-styled buttons, icons and calendar cells.
* **Java Time API**: Utilized `LocalDate` and `DateTimeFormatter` for accurate date manipulation and user-friendly formatting.

---

## 👩‍💻 Author
**Emilia Kura**
