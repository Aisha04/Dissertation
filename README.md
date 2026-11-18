# Railway Tracking and Arrival Time Prediction (IoT)

An IoT-based railway tracking system that records the **actual arrival time of trains** using RFID sensors and shares it in real time through Android mobile apps. The system connects train hardware, administrators, drivers and passengers so that everyone knows where a train is, when it will arrive, and whether it is delayed or needs help.

Developed as my BSc dissertation project.

---

## System Overview

The system has four connected parts, all linked through **Firebase**:

```
        ┌──────────────────────┐
        │  Arduino + RFID      │  Train passes station → arrival time recorded
        │  (train detection)   │  → train number & time shown on display screen
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │      Firebase        │  Real-time database & authentication
        └───┬──────┬───────┬───┘
            │      │       │
            ▼      ▼       ▼
       ┌──────┐ ┌──────┐ ┌────────┐
       │Admin │ │Driver│ │ Client │
       │ App  │ │ App  │ │  App   │
       └──────┘ └──────┘ └────────┘
```

### 1. Arduino & RFID sensor module
- Each RFID tag represents a **specific train**, and different trains use different tags.
- When a tag passes the RFID reader at a station, the system **detects the time and records it as the train's actual arrival time** in the application.
- A connected **display screen** shows the train number and arrival time at the station.

### 2. Admin app
- Add and manage **stations, trains and timetables** (scheduled time of each train at each station).
- Manage driver and client accounts.
- Receive **issue reports from drivers** in real time, for example a breakdown, delay or need for help.
- Decide whether a train **needs intervention** and update the system with **how late** the train will be.

### 3. Driver app
- Secure login for train drivers.
- **Report issues encountered during the journey**, such as a breakdown or other incident.
- Indicate whether **help is needed** or **how late** the train will be, so the admin can update the schedule.

### 4. Client (passenger) app
- View **train arrival and departure times** for a specific station.
- See live arrival times and delay updates.
- View stations on a map, receive notifications and manage a user profile.

---

## Stations Covered

Port Louis · Beau Bassin · Rose Hill · Quatre Bornes · Phoenix · Vacoas · Sadally · Floreal · Trianon · St Jean · St Louis · Curepipe · Coromandel

---

## Repository Structure

The Android source code is grouped below by feature:

| Area | Files |
|------|-------|
| **Authentication** | `Login_Form.java`, `Signup_Form.java`, `ForgetPassword.java`, `Password.java` |
| **Admin** | `Admin.java`, `AdminEdit.java`, `AdminProfile.java`, `AdminPW.java` |
| **Driver** | `DriverEdit.java`, `DriverMap.java`, `DriverNotification.java`, `DriverProfile.java`, `DriverPW.java`, `driver_list.java`, `QuestionDriverdb.java` |
| **Client** | `client.java`, `ClientEdit.java`, `ClientMap.java`, `ClientNotification.java`, `ClientPW.java`, `ClientStation.java`, `client_List.java`, `clientStation_List.java` |
| **Stations** | `Station*.java`, `AddStation.java`, `addNewStation.java`, `CreateStation.java`, `stationList.java`, plus one screen per station (e.g. `PortLouis.java`, `Curepipe.java`) |
| **Trains & schedules** | `createTrain.java`, `Schedule.java`, `ScheduleUpdate.java`, `scheduleList.java`, `Late.java`, `list_*.java` |
| **Database models (Firebase)** | `clientdb.java`, `clientStationdb.java`, `stationdb.java`, `scheduledb.java`, `Infodb.java`, `Questiondb.java`, `MainClientdb.java` |
| **Navigation & UI** | `MainActivity.java`, `Main2Activity.java`, `Main3Activity.java`, `BottomNavigationBehavior.java`, `More.java`, `*ViewModel.java`, `Map.java`, `Calender.java`, `Info.java`, `InfoList.java` |

---

## Tech Stack

| Category | Technologies |
|----------|--------------|
| Programming languages | Java, Arduino, C, C++ |
| Mobile development | Android Studio |
| Hardware | Arduino, RFID reader & tags, display screen |
| Backend | Firebase (Realtime Database & Authentication) |

---

## Getting Started

### Android apps
1. Clone this repository.
2. Open the project in **Android Studio**.
3. Connect your own Firebase project (add your `google-services.json` file).
4. Build and run on an Android device or emulator.

### Arduino module
1. Open the Arduino sketch in the **Arduino IDE**.
2. Connect the RFID reader and display screen to the Arduino board.
3. Upload the sketch and scan an RFID tag to simulate a train arriving.

---

## Author

**Aishani Bissessur**, Software Engineer · BSc dissertation project
