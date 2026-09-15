import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hotel.java
 * Core business logic class — manages rooms and bookings.
 */
public class Hotel {

    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private int bookingCounter;

    // Constructor — initialise rooms and load existing bookings from file
    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        bookingCounter = 1001;
        initializeRooms();
        loadBookingsFromFile();
    }

    // Pre-populate the hotel with rooms
    private void initializeRooms() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(103, "Standard", 2000));
        rooms.add(new Room(201, "Deluxe",   3500));
        rooms.add(new Room(202, "Deluxe",   3500));
        rooms.add(new Room(203, "Deluxe",   3500));
        rooms.add(new Room(301, "Suite",    5000));
        rooms.add(new Room(302, "Suite",    5000));
    }

    // Load saved bookings from file and restore room availability
    private void loadBookingsFromFile() {
        ArrayList<Booking> loaded = FileManager.loadBookings(rooms);
        if (!loaded.isEmpty()) {
            bookings.addAll(loaded);
            // Advance counter so new IDs don't clash with saved ones
            for (Booking b : bookings) {
                String numPart = b.getBookingId().replace("BK", "");
                try {
                    int id = Integer.parseInt(numPart);
                    if (id >= bookingCounter) {
                        bookingCounter = id + 1;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    // ──────────────────────────────────────────────
    //  1. DISPLAY ALL ROOMS
    // ──────────────────────────────────────────────
    public void displayAllRooms() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("            ALL HOTEL ROOMS             ");
        System.out.println("========================================");
        System.out.printf("%-12s %-14s %-16s %s%n",
                "Room No.", "Category", "Price/Night", "Status");
        System.out.println("----------------------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("========================================");
    }

    // ──────────────────────────────────────────────
    //  2. SEARCH AVAILABLE ROOMS BY CATEGORY
    // ──────────────────────────────────────────────
    public void searchAvailableRooms(Scanner scanner) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         SEARCH AVAILABLE ROOMS         ");
        System.out.println("========================================");
        System.out.println("  Select Room Category:");
        System.out.println("  1. Standard");
        System.out.println("  2. Deluxe");
        System.out.println("  3. Suite");
        System.out.print("  Enter choice: ");

        int choice = readInt(scanner);
        String category;

        switch (choice) {
            case 1: category = "Standard"; break;
            case 2: category = "Deluxe";   break;
            case 3: category = "Suite";    break;
            default:
                System.out.println("  [!] Invalid choice. Please select 1, 2, or 3.");
                return;
        }

        System.out.println();
        System.out.println("  Available " + category + " Rooms:");
        System.out.printf("  %-12s %-14s %s%n", "Room No.", "Category", "Price/Night");
        System.out.println("  ----------------------------------------");

        boolean found = false;
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                System.out.printf("  %-12d %-14s Rs.%d%n",
                        room.getRoomNumber(), room.getCategory(), (int) room.getPricePerNight());
                found = true;
            }
        }

        if (!found) {
            System.out.println("  No available " + category + " rooms at the moment.");
        }
        System.out.println("========================================");
    }

    // ──────────────────────────────────────────────
    //  3. MAKE RESERVATION
    // ──────────────────────────────────────────────
    public void makeReservation(Scanner scanner) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("           MAKE RESERVATION             ");
        System.out.println("========================================");

        // --- Customer Name ---
        System.out.print("  Customer Name  : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  [!] Customer name cannot be empty.");
            return;
        }

        // --- Phone Number ---
        System.out.print("  Phone Number   : ");
        String phone = scanner.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("  [!] Invalid phone number. Enter 10 digits.");
            return;
        }

        // --- Show available rooms ---
        System.out.println();
        System.out.println("  Available Rooms:");
        System.out.printf("  %-12s %-14s %s%n", "Room No.", "Category", "Price/Night");
        System.out.println("  ----------------------------------------");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.printf("  %-12d %-14s Rs.%d%n",
                        room.getRoomNumber(), room.getCategory(), (int) room.getPricePerNight());
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("  [!] No rooms are currently available.");
            return;
        }

        // --- Room Selection ---
        System.out.print("  Enter Room Number: ");
        int roomNumber = readInt(scanner);
        if (roomNumber == -1) {
            System.out.println("  [!] Invalid input. Please enter a valid room number.");
            return;
        }
        Room selectedRoom = findRoomByNumber(roomNumber);
        if (selectedRoom == null) {
            System.out.println("  [!] Room " + roomNumber + " does not exist.");
            return;
        }
        if (!selectedRoom.isAvailable()) {
            System.out.println("  [!] Room " + roomNumber + " is already booked.");
            return;
        }

        // --- Number of Nights ---
        System.out.print("  Number of Nights: ");
        int nights = readInt(scanner);
        if (nights <= 0) {
            System.out.println("  [!] Number of nights must be greater than 0.");
            return;
        }

        double totalCost = selectedRoom.getPricePerNight() * nights;
        System.out.println();
        System.out.println("  Room Price   : Rs." + (int) selectedRoom.getPricePerNight() + "/night");
        System.out.println("  Total Amount : Rs." + (int) totalCost);

        // --- Payment ---
        String paymentMethod = processPayment(scanner, totalCost);
        if (paymentMethod == null) {
            System.out.println("  [!] Payment cancelled. Reservation not made.");
            return;
        }

        // --- Create Booking ---
        Customer customer = new Customer(name, phone);
        String bookingId = "BK" + bookingCounter++;
        Booking booking = new Booking(bookingId, customer, selectedRoom, nights, paymentMethod);

        // Mark room as booked
        selectedRoom.setAvailable(false);
        bookings.add(booking);

        // Persist to file
        FileManager.saveBooking(booking);

        // Confirmation
        booking.displayBookingDetails();
    }

    // ──────────────────────────────────────────────
    //  PAYMENT SIMULATION (called from makeReservation)
    // ──────────────────────────────────────────────
    private String processPayment(Scanner scanner, double amount) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("              PAYMENT                   ");
        System.out.println("========================================");
        System.out.println("  Amount  : Rs." + (int) amount);
        System.out.println();
        System.out.println("  Select Payment Method:");
        System.out.println("  1. Credit / Debit Card");
        System.out.println("  2. UPI");
        System.out.println("  3. Cash");
        System.out.print("  Enter choice: ");

        int choice = readInt(scanner);
        String method;

        switch (choice) {
            case 1: method = "Credit/Debit Card"; break;
            case 2: method = "UPI";               break;
            case 3: method = "Cash";              break;
            default:
                System.out.println("  [!] Invalid payment option.");
                return null;
        }

        Payment payment = new Payment(amount, method);
        payment.processPayment();

        System.out.println();
        System.out.println("  Payment Successful!");
        System.out.println("  Transaction ID : " + payment.getTransactionId());
        System.out.println("  Method         : " + method);

        return method;
    }

    // ──────────────────────────────────────────────
    //  4. VIEW BOOKING DETAILS
    // ──────────────────────────────────────────────
    public void displayBooking(Scanner scanner) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("          VIEW BOOKING DETAILS          ");
        System.out.println("========================================");
        System.out.print("  Enter Booking ID (e.g. BK1001): ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("  [!] Booking " + bookingId + " not found.");
            return;
        }
        booking.displayBookingDetails();
    }

    // ──────────────────────────────────────────────
    //  5. VIEW ALL BOOKINGS
    // ──────────────────────────────────────────────
    public void displayAllBookings() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("            ALL BOOKINGS                ");
        System.out.println("========================================");

        if (bookings.isEmpty()) {
            System.out.println("  No bookings found.");
            System.out.println("========================================");
            return;
        }

        System.out.printf("%-10s %-16s %-10s %-10s %-16s %s%n",
                "Booking ID", "Customer", "Room No.", "Nights", "Total", "Status");
        System.out.println("------------------------------------------------------------------------");
        for (Booking b : bookings) {
            System.out.println(b);
        }
        System.out.println("========================================");
    }

    // ──────────────────────────────────────────────
    //  6. CANCEL RESERVATION
    // ──────────────────────────────────────────────
    public void cancelBooking(Scanner scanner) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("          CANCEL RESERVATION            ");
        System.out.println("========================================");
        System.out.print("  Enter Booking ID to cancel (e.g. BK1001): ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("  [!] Booking " + bookingId + " not found.");
            return;
        }
        if (booking.getBookingStatus().equals("CANCELLED")) {
            System.out.println("  [!] Booking " + bookingId + " is already cancelled.");
            return;
        }

        // Cancel booking and free room
        booking.setBookingStatus("CANCELLED");
        booking.setPaymentStatus("REFUNDED");
        booking.getRoom().setAvailable(true);

        // Persist updated bookings
        FileManager.saveAllBookings(bookings);

        System.out.println();
        System.out.println("  Booking " + bookingId + " cancelled successfully.");
        System.out.println("  Room " + booking.getRoom().getRoomNumber() + " is now available.");
        System.out.println("========================================");
    }

    // ──────────────────────────────────────────────
    //  HELPER METHODS
    // ──────────────────────────────────────────────

    // Find a room by its room number
    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Find a booking by its booking ID
    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    // Validate phone number — must be exactly 10 digits
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    // Safe integer read from scanner — returns -1 on invalid input
    private int readInt(Scanner scanner) {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Expose rooms list (used by FileManager when loading)
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
