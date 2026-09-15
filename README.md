# Hotel Reservation System

## 1. Project Title

**Hotel Reservation System** — CodeAlpha Java Programming Internship | Task 4

---

## 2. Project Description

A complete, menu-driven, console-based Java application that simulates a hotel reservation system. Users can browse room categories, search for available rooms, make reservations, simulate payments, view booking details, cancel reservations, and persist all booking data between sessions using Java File I/O.

---

## 3. Objective

To design and implement a hotel reservation system in Java that demonstrates Object-Oriented Programming principles, Java File I/O for data persistence, input validation, and clean modular code structure — all without a database or web framework.

---

## 4. Features

| # | Feature |
|---|---------|
| 1 | View all hotel rooms with category, price, and availability |
| 2 | Search available rooms by category (Standard / Deluxe / Suite) |
| 3 | Make a reservation with customer details, room selection, and nights |
| 4 | Simulated payment with three payment methods |
| 5 | Auto-generated unique Booking IDs (BK1001, BK1002 …) |
| 6 | Auto-generated Transaction IDs (TXN1001, TXN1002 …) |
| 7 | View complete booking details by Booking ID |
| 8 | View all bookings in a formatted table |
| 9 | Cancel a reservation and automatically free the room |
| 10 | Persistent storage — bookings saved to `data/bookings.txt` |
| 11 | Data is loaded on startup — no data loss between runs |
| 12 | Full input validation with user-friendly error messages |

---

## 5. Room Categories

| Room No. | Category | Price/Night |
|----------|----------|-------------|
| 101, 102, 103 | Standard | Rs. 2000 |
| 201, 202, 203 | Deluxe   | Rs. 3500 |
| 301, 302      | Suite    | Rs. 5000 |

---

## 6. Technologies Used

- **Language:** Java (JDK 8 or higher)
- **I/O:** Java File I/O (`FileWriter`, `BufferedWriter`, `FileReader`, `BufferedReader`)
- **Data Storage:** Plain text CSV file (`data/bookings.txt`)
- **Input:** `java.util.Scanner`
- **Collections:** `java.util.ArrayList`

No external libraries, no database, no web framework.

---

## 7. Java Concepts Used

- Classes and Objects
- Encapsulation (private fields + getters/setters)
- Constructors
- Method overriding (`toString()`)
- `ArrayList` for dynamic collections
- `for-each` loops and `while` loops
- `switch` statements
- Exception handling (`try-catch`, `NumberFormatException`, `IOException`)
- `Scanner` for console input
- File I/O (`FileWriter`, `BufferedWriter`, `FileReader`, `BufferedReader`)
- String methods (`.trim()`, `.split()`, `.matches()`, `.equalsIgnoreCase()`)
- Basic arithmetic (total cost calculation)
- Static variables and methods
- Input validation with regex

---

## 8. OOP Design

```
Main.java          --  Entry point; main menu loop
  |
  +-- Hotel.java       --  Core business logic; manages all rooms and bookings
        |
        +-- Room.java      --  Represents a hotel room
        +-- Customer.java  --  Represents a customer
        +-- Booking.java   --  Represents a reservation (links Customer + Room)
        +-- Payment.java   --  Simulates payment processing
        +-- FileManager.java -- Handles all File I/O operations
```

Each class has a single, clear responsibility (Single Responsibility Principle).

---

## 9. File I/O Implementation

### Storage File
```
data/bookings.txt
```

### Format (CSV — one booking per line)
```
bookingId,customerName,phoneNumber,roomNumber,numberOfNights,totalAmount,paymentStatus,paymentMethod,bookingStatus
```

### Example
```
BK1001,Rahul,9876543210,201,3,10500,PAID,UPI,CONFIRMED
BK1002,Priya,9123456780,301,5,25000,PAID,Credit/Debit Card,CONFIRMED
```

### How It Works
- **On startup:** `FileManager.loadBookings()` reads the file and reconstructs all `Booking` objects; room availability is automatically restored.
- **On reservation:** `FileManager.saveBooking()` appends one line to the file (fast append mode).
- **On cancellation:** `FileManager.saveAllBookings()` overwrites the file with updated booking statuses (cancel updates only work correctly with a full rewrite).
- **File missing:** If `data/bookings.txt` does not exist, the application starts fresh with no error. The `data/` directory is created automatically when needed.

---

## 10. Project Structure

```
HotelReservationSystem/
├── src/
│   ├── Main.java          — Entry point, menu loop
│   ├── Room.java          — Room entity class
│   ├── Customer.java      — Customer entity class
│   ├── Booking.java       — Booking entity class
│   ├── Payment.java       — Simulated payment class
│   ├── Hotel.java         — Business logic class
│   └── FileManager.java   — File I/O utility class
├── data/
│   └── bookings.txt       — Persistent booking data (CSV)
├── out/                   — Compiled .class files (auto-created)
├── README.md
└── .gitignore
```

---

## 11. How to Compile

Open a terminal in the project root folder and run:

```bash
javac -d out src\Room.java src\Customer.java src\Payment.java src\Booking.java src\FileManager.java src\Hotel.java src\Main.java
```

> **Note:** The `out/` directory is created automatically if it doesn't exist.

---

## 12. How to Run

After compiling, run:

```bash
java -cp out Main
```

> The application must be run from the project root (not from inside `src/` or `out/`) so that the `data/` directory is created in the correct location.

---

## 13. Sample Console Output

```
========================================
       HOTEL RESERVATION SYSTEM
        Welcome to Grand Vista Hotel
========================================
  Existing bookings loaded from file.

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
  Enter your choice: 1

========================================
            ALL HOTEL ROOMS
========================================
Room No.     Category       Price/Night      Status
----------------------------------------
101          Standard       Rs.2000          Available
102          Standard       Rs.2000          Available
103          Standard       Rs.2000          Available
201          Deluxe         Rs.3500          Available
202          Deluxe         Rs.3500          Available
203          Deluxe         Rs.3500          Available
301          Suite          Rs.5000          Available
302          Suite          Rs.5000          Available
========================================
```

---

## 14. Example Reservation Process

```
  Enter your choice: 3

========================================
           MAKE RESERVATION
========================================
  Customer Name  : Rahul
  Phone Number   : 9876543210
  Enter Room Number: 201
  Number of Nights: 3

  Room Price   : Rs.3500/night
  Total Amount : Rs.10500

========================================
              PAYMENT
========================================
  Amount  : Rs.10500

  Select Payment Method:
  1. Credit / Debit Card
  2. UPI
  3. Cash
  Enter choice: 2

  Payment Successful!
  Transaction ID : TXN1001
  Method         : UPI

========================================
         BOOKING CONFIRMED
========================================
  Booking ID     : BK1001
  Customer       : Rahul
  Phone          : 9876543210
  Room No.       : 201
  Category       : Deluxe
  Price/Night    : Rs.3500
  Nights         : 3
  Total Amount   : Rs.10500
  Payment Status : PAID
  Payment Method : UPI
  Booking Status : CONFIRMED
========================================
```

---

## 15. Example Cancellation Process

```
  Enter your choice: 6

========================================
          CANCEL RESERVATION
========================================
  Enter Booking ID to cancel (e.g. BK1001): BK1001

  Booking BK1001 cancelled successfully.
  Room 201 is now available.
========================================
```

---

## 16. Future Enhancements

- Add date-based check-in and check-out support
- Allow room search by price range
- Add admin login to manage rooms
- Export booking summary to PDF or HTML
- Add discount/coupon code support
- Implement a graphical user interface (JavaFX or Swing)
- Move from CSV to a SQLite database

---

## 17. Important Note on Payments

> ⚠️ **Payment simulation only.**  
> This application does **NOT** process any real payments.  
> The payment flow is simulated for demonstration purposes only.  
> No actual money is charged or transferred.

---

## GitHub Repository

**`CodeAlpha_HotelReservationSystem`**

---

## LinkedIn Post

> 🏨 Excited to share my **Hotel Reservation System** built as Task 4 of my Java Programming Internship at **CodeAlpha**!
>
> This project is a complete **console-based Java application** featuring:
> - 🛏️ Multiple room categories (Standard, Deluxe, Suite)
> - 📋 Full reservation lifecycle (book → pay → confirm → cancel)
> - 💳 Simulated payment system (Card, UPI, Cash)
> - 💾 Java File I/O for persistent booking data
> - ✅ Robust input validation and exception handling
>
> Key Java concepts used: **OOP, ArrayList, File I/O, Exception Handling, Scanner, String manipulation**
>
> 💻 GitHub: [CodeAlpha_HotelReservationSystem]
>
> Grateful for the learning opportunity at **CodeAlpha**! 🚀
>
> #Java #CodeAlpha #Internship #HotelReservationSystem #OOP #JavaProgramming #100DaysOfCode

---

## Video Explanation Script (2–3 minutes)

> **[INTRO — 0:00]**  
> "Hi everyone! In this video, I'm going to quickly walk you through my Hotel Reservation System — a console-based Java application I built for my CodeAlpha Java Programming Internship."
>
> **[PROJECT OVERVIEW — 0:20]**  
> "The project has 7 Java classes: Room, Customer, Booking, Payment, Hotel, FileManager, and Main. Each class has a single responsibility, demonstrating clean OOP design."
>
> **[DEMO — 0:45]**  
> "When the application starts, it loads any saved bookings from a text file. I can view all rooms, search by category, and make a reservation by entering my name, phone number, room number, and nights. The total cost is calculated automatically. Then I choose a payment method — Card, UPI, or Cash — and a booking confirmation is displayed."
>
> **[FILE I/O — 1:30]**  
> "All bookings are saved to data/bookings.txt in CSV format. When I restart the application, all previous bookings are loaded and room availability is restored automatically."
>
> **[CANCEL — 1:50]**  
> "Cancelling a booking is simple — I enter the Booking ID, and the booking is marked CANCELLED, the room becomes available again, and the file is updated."
>
> **[VALIDATION — 2:10]**  
> "The application handles all invalid inputs — empty names, non-numeric input, already-booked rooms, non-existent booking IDs — without crashing."
>
> **[OUTRO — 2:30]**  
> "This project helped me strengthen my understanding of Java OOP, File I/O, and building clean, modular applications. If you found this helpful, please like and follow. Thanks!"

---

## Viva Questions and Answers

**Q1: What is the purpose of the `FileManager` class?**  
A: It handles all Java File I/O operations — saving bookings to a CSV file, loading them on startup, and rewriting the file when a booking is cancelled.

**Q2: Why do you rewrite the entire file on cancellation instead of just appending?**  
A: Because we need to update the status of an existing line. Append mode can only add new lines; to *modify* an existing record we must rewrite the whole file.

**Q3: How is room availability restored when the application restarts?**  
A: In `FileManager.loadBookings()`, after parsing each CSV line, if the booking status is `CONFIRMED`, the corresponding `Room` object's `available` field is set to `false`.

**Q4: How are Booking IDs generated uniquely?**  
A: A counter starts at 1001 and increments for each new booking. On startup, all existing Booking IDs are parsed and the counter is set above the highest existing ID.

**Q5: What happens if the `data/bookings.txt` file doesn't exist?**  
A: The `loadBookings()` method checks if the file exists using `file.exists()`. If not, it simply returns an empty list. The `ensureDataDirectoryExists()` method creates the `data/` directory automatically when saving.

**Q6: What OOP concept does the `toString()` method demonstrate?**  
A: Method overriding — each class overrides the `toString()` method inherited from `Object` to provide a custom, formatted string representation.

**Q7: Why is `ArrayList` used instead of arrays?**  
A: `ArrayList` is dynamic — it grows automatically as rooms or bookings are added, unlike arrays which require a fixed size.

**Q8: How does the payment simulation work?**  
A: The `Payment` class generates a unique Transaction ID and sets the payment status to `SUCCESS` when `processPayment()` is called. No real payment gateway is involved.

**Q9: What exception handling is implemented?**  
A: `NumberFormatException` is caught when parsing integer input (menu choices, room numbers, nights). `IOException` is caught in all File I/O operations. All exceptions show user-friendly messages without crashing.

**Q10: Can the same room be booked twice?**  
A: No. Before making a reservation, the system checks `room.isAvailable()`. If the room is already booked, it prints an error and returns without creating a booking.
