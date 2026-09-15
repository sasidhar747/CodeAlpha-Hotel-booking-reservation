# CodeAlpha - Hotel Reservation System

An object-oriented, console-based **Hotel Reservation System** developed in Java as part of the **CodeAlpha Java Programming Internship**.

This application simulates a complete hotel management workflow including room management, category-based room searching, reservation processing, payment simulation, cancellation management, and data persistence using Java File I/O.

---

## 🌟 Key Features

- **🏨 Room Management**: Pre-configured room inventory across multiple categories (**Standard**, **Deluxe**, **Suite**) with real-time status tracking (*Available* vs. *Booked*).
- **🔍 Search & Filter**: Filter available rooms by category along with transparent pricing per night.
- **📝 Interactive Reservation Workflow**:
  - Validates customer name and phone number (10-digit validation).
  - Automatically calculates total price based on duration of stay.
  - Generates unique Booking IDs (`BK1001`, `BK1002`, ...).
- **💳 Payment Simulation**:
  - Supports multiple payment methods: **Credit/Debit Card**, **UPI**, and **Cash**.
  - Generates unique Transaction IDs (`TXN1001`, `TXN1002`, ...).
- **🔄 Reservation Cancellation**:
  - Allows cancelling active bookings.
  - Automatically releases rooms back into available inventory and updates payment status to `REFUNDED`.
- **💾 File Persistence (No Database Required)**:
  - Automatically loads previous bookings from `data/bookings.txt` on startup.
  - Saves new bookings and updates existing records seamlessly in CSV format.

---

## 📁 Project Structure

```
Hotel Reservation System/
│
├── src/
│   ├── Main.java          # Entry point and interactive console menu
│   ├── Hotel.java         # Core business logic and room/booking manager
│   ├── Room.java          # Room entity model (Room No, Category, Price, Status)
│   ├── Customer.java      # Customer entity model (Name, Phone)
│   ├── Booking.java       # Reservation details and cost calculations
│   ├── Payment.java       # Simulated payment processing and receipt generator
│   └── FileManager.java   # File I/O operations for data persistence
│
├── data/
│   └── bookings.txt       # Persistent CSV storage for reservation records
│
├── out/                   # Compiled Java bytecode files (.class)
├── .gitignore             # Standard Git ignore configuration
└── README.md              # Project documentation
```

---

## 🏗️ System Architecture & Classes

| Class | Responsibilities |
| :--- | :--- |
| `Main` | Drives the application menu and handles user input choices (Option 1 to 7). |
| `Hotel` | Manages rooms list, active bookings, room search, reservation workflow, and cancellation. |
| `Room` | Represents room properties such as `roomNumber`, `category`, `pricePerNight`, and `available`. |
| `Customer` | Stores guest details including `customerName` and `phoneNumber`. |
| `Booking` | Encapsulates booking details (`bookingId`, `customer`, `room`, `numberOfNights`, `totalAmount`, `paymentStatus`, `bookingStatus`). |
| `Payment` | Simulates payment processing (`transactionId`, `amount`, `paymentMethod`, `paymentStatus`). |
| `FileManager` | Reads and writes reservation data to `data/bookings.txt` in CSV format. |

---

## 💾 Data Persistence Format

Bookings are saved in `data/bookings.txt` using the following CSV schema:

```csv
bookingId,customerName,phoneNumber,roomNumber,numberOfNights,totalAmount,paymentStatus,paymentMethod,bookingStatus
```

### Example Record:
```csv
BK1001,John Doe,9876543210,201,3,10500,PAID,Credit/Debit Card,CONFIRMED
```

---

## 🚀 How to Run the Application

### Prerequisites
- **Java Development Kit (JDK 8 or higher)** installed and configured in your environment variables.

### 1. Clone the Repository
```bash
git clone https://github.com/sasidhar747/CodeAlpha-Hotel-booking-reservation.git
cd CodeAlpha-Hotel-booking-reservation
```

### 2. Compile the Source Code
```bash
javac -d out src/*.java
```

### 3. Run the Application
```bash
java -cp out Main
```

---

## 📱 User Interface Preview

```text
========================================
       HOTEL RESERVATION SYSTEM         
        Welcome to Grand Vista Hotel    
========================================

========================================
         MAIN MENU                      
========================================
  1. View All Rooms
  2. Search Available Rooms
  3. Make Reservation
  4. View Booking Details
  5. View All Bookings
  6. Cancel Reservation
  7. Exit
========================================
  Enter your choice: 
```

---

## 👨‍💻 Contributor

- **Gamini Sasidhar sai varma**

---

## 📜 License

This project is created for educational and internship evaluation purposes under the **CodeAlpha Java Programming Internship Program**.