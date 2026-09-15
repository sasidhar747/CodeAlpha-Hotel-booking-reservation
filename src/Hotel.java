import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hotel.java
 * Core Business Logic Class — manages room inventory, availability,
 * customer search, reservations, payments, and cancellations.
 */
public class Hotel {

    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private int bookingCounter;

    /**
     * Constructor — initializes room inventory and restores saved bookings from disk.
     */
    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        bookingCounter = 1001;
        initializeRooms();
        loadBookingsFromFile();
    }

    /**
     * Populates the hotel inventory with rooms across categories.
     */
    private void initializeRooms() {
        // Standard Rooms (Rs. 2000 / night)
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(103, "Standard", 2000));

        // Deluxe Rooms (Rs. 3500 / night)
        rooms.add(new Room(201, "Deluxe",   3500));
        rooms.add(new Room(202, "Deluxe",   3500));
        rooms.add(new Room(203, "Deluxe",   3500));

        // Suite Rooms (Rs. 5000 / night)
        rooms.add(new Room(301, "Suite",    5000));
        rooms.add(new Room(302, "Suite",    5000));
    }

    /**
     * Loads existing bookings from disk and adjusts booking ID sequence.
     */
    private void loadBookingsFromFile() {
        ArrayList<Booking> loaded = FileManager.loadBookings(rooms);
        if (!loaded.isEmpty()) {
            bookings.addAll(loaded);

            // Advance ID counter past highest saved ID to prevent duplicate IDs
            for (Booking b : bookings) {
                String numericOnly = b.getBookingId().replaceAll("[^0-9]", "");
                try {
                    int id = Integer.parseInt(numericOnly);
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
        System.out.println("==========================================================");
        System.out.println("                 ALL HOTEL ROOMS INVENTORY                ");
        System.out.println("==========================================================");
        System.out.printf("  %-10s %-14s %-16s %s%n", "Room No.", "Category", "Price / Night", "Status");
        System.out.println("  --------------------------------------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("==========================================================");
    }

    // ──────────────────────────────────────────────
    //  2. SEARCH AVAILABLE ROOMS BY CATEGORY
    // ──────────────────────────────────────────────
    public void searchAvailableRooms(Scanner scanner) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("               SEARCH AVAILABLE ROOMS                     ");
        System.out.println("==========================================================");
        System.out.println("  Select Room Category:");
        System.out.println("  1. Standard (Rs. 2,000 / night)");
        System.out.println("  2. Deluxe   (Rs. 3,500 / night)");
        System.out.println("  3. Suite    (Rs. 5,000 / night)");
        System.out.print("  Enter choice (1-3): ");

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
        System.out.printf("  %-10s %-14s %-16s %s%n", "Room No.", "Category", "Price / Night", "Status");
        System.out.println("  --------------------------------------------------------");

        boolean found = false;
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("  [!] No " + category + " rooms are currently available.");
        }
        System.out.println("==========================================================");
    }

    // ──────────────────────────────────────────────
    //  3. MAKE RESERVATION
    // ──────────────────────────────────────────────
    public void makeReservation(Scanner scanner) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("                  NEW RESERVATION WORKFLOW                ");
        System.out.println("==========================================================");

        // --- Step 1: Customer Name ---
        System.out.print("  Enter Customer Full Name : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  [!] Error: Customer name cannot be empty.");
            return;
        }

        // --- Step 2: Customer Phone Number ---
        System.out.print("  Enter 10-Digit Phone     : ");
        String phone = scanner.nextLine().trim();
        if (!isValidPhone(phone)) {
            System.out.println("  [!] Error: Invalid phone number. Must be exactly 10 digits.");
            return;
        }

        // --- Step 3: Display Currently Available Rooms ---
        System.out.println();
        System.out.println("  Currently Available Rooms:");
        System.out.printf("  %-10s %-14s %-16s %s%n", "Room No.", "Category", "Price / Night", "Status");
        System.out.println("  --------------------------------------------------------");
        boolean anyAvailable = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("  [!] Error: No rooms are currently available for booking.");
            return;
        }

        // --- Step 4: Room Selection ---
        System.out.print("  Enter Desired Room Number: ");
        int roomNumber = readInt(scanner);
        if (roomNumber == -1) {
            System.out.println("  [!] Error: Invalid input. Please enter a numerical room number.");
            return;
        }
        Room selectedRoom = findRoomByNumber(roomNumber);
        if (selectedRoom == null) {
            System.out.println("  [!] Error: Room " + roomNumber + " does not exist in inventory.");
            return;
        }
        if (!selectedRoom.isAvailable()) {
            System.out.println("  [!] Error: Room " + roomNumber + " is already occupied/booked.");
            return;
        }

        // --- Step 5: Duration of Stay ---
        System.out.print("  Enter Number of Nights   : ");
        int nights = readInt(scanner);
        if (nights <= 0) {
            System.out.println("  [!] Error: Duration of stay must be at least 1 night.");
            return;
        }

        double totalCost = selectedRoom.getPricePerNight() * nights;
        System.out.println();
        System.out.println("  --------------------------------------------------");
        System.out.printf("  Room Rate     : Rs. %.0f / night%n", selectedRoom.getPricePerNight());
        System.out.printf("  Total Amount  : Rs. %.0f%n", totalCost);
        System.out.println("  --------------------------------------------------");

        // --- Step 6: Process Payment ---
        String paymentMethod = processPayment(scanner, totalCost);
        if (paymentMethod == null) {
            System.out.println("  [!] Reservation cancelled due to aborted payment.");
            return;
        }

        // --- Step 7: Finalize Booking Record ---
        Customer customer = new Customer(name, phone);
        String bookingId = "BK" + bookingCounter++;
        Booking booking = new Booking(bookingId, customer, selectedRoom, nights, paymentMethod);

        // Update room availability status
        selectedRoom.setAvailable(false);
        bookings.add(booking);

        // Save to persistent file
        FileManager.saveBooking(booking);

        // Display confirmation summary
        booking.displayBookingDetails();
    }

    // ──────────────────────────────────────────────
    //  PAYMENT SIMULATION
    // ──────────────────────────────────────────────
    private String processPayment(Scanner scanner, double amount) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("                PAYMENT GATEWAY                   ");
        System.out.println("==================================================");
        System.out.printf("  Amount Due : Rs. %.0f%n", amount);
        System.out.println();
        System.out.println("  Select Payment Method:");
        System.out.println("  1. Credit / Debit Card");
        System.out.println("  2. UPI / NetBanking");
        System.out.println("  3. Cash");
        System.out.print("  Enter Choice (1-3): ");

        int choice = readInt(scanner);
        String method;

        switch (choice) {
            case 1: method = "Credit/Debit Card"; break;
            case 2: method = "UPI/NetBanking";    break;
            case 3: method = "Cash";              break;
            default:
                System.out.println("  [!] Error: Invalid payment method selected.");
                return null;
        }

        Payment payment = new Payment(amount, method);
        payment.processPayment();

        System.out.println();
        System.out.println("  [✓] Payment Processed Successfully!");
        System.out.printf("  Transaction ID : %s%n", payment.getTransactionId());
        System.out.printf("  Payment Method : %s%n", method);

        return method;
    }

    // ──────────────────────────────────────────────
    //  4. VIEW SPECIFIC BOOKING DETAILS
    // ──────────────────────────────────────────────
    public void displayBooking(Scanner scanner) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("                 LOOKUP BOOKING DETAILS                   ");
        System.out.println("==========================================================");
        System.out.print("  Enter Booking ID (e.g. BK1001): ");
        String bookingId = scanner.nextLine().trim();

        if (bookingId.isEmpty()) {
            System.out.println("  [!] Booking ID cannot be empty.");
            return;
        }

        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("  [!] Error: No reservation record found for Booking ID: " + bookingId);
            return;
        }

        booking.displayBookingDetails();
    }

    // ──────────────────────────────────────────────
    //  5. VIEW ALL BOOKINGS
    // ──────────────────────────────────────────────
    public void displayAllBookings() {
        System.out.println();
        System.out.println("=========================================================================================");
        System.out.println("                                ALL RESERVATIONS LOG                                     ");
        System.out.println("=========================================================================================");

        if (bookings.isEmpty()) {
            System.out.println("  No reservation records currently exist.");
            System.out.println("=========================================================================================");
            return;
        }

        System.out.printf("  %-12s %-26s %-10s %-8s %-14s %s%n",
                "Booking ID", "Customer Name", "Room No.", "Nights", "Total Amount", "Booking Status");
        System.out.println("  ---------------------------------------------------------------------------------------");
        for (Booking b : bookings) {
            System.out.println(b);
        }
        System.out.println("=========================================================================================");
    }

    // ──────────────────────────────────────────────
    //  6. CANCEL RESERVATION
    // ──────────────────────────────────────────────
    public void cancelBooking(Scanner scanner) {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("                  CANCEL RESERVATION                      ");
        System.out.println("==========================================================");
        System.out.print("  Enter Booking ID to Cancel (e.g. BK1001): ");
        String bookingId = scanner.nextLine().trim();

        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("  [!] Error: Reservation ID " + bookingId + " not found.");
            return;
        }

        if (booking.getBookingStatus().equalsIgnoreCase("CANCELLED")) {
            System.out.println("  [!] Reservation " + bookingId + " is already marked as CANCELLED.");
            return;
        }

        // Update reservation status and free room
        booking.setBookingStatus("CANCELLED");
        booking.setPaymentStatus("REFUNDED");
        booking.getRoom().setAvailable(true);

        // Save updated records to file
        FileManager.saveAllBookings(bookings);

        System.out.println();
        System.out.println("  [✓] Reservation " + bookingId + " cancelled successfully!");
        System.out.println("  [✓] Room " + booking.getRoom().getRoomNumber() + " is now available for new bookings.");
        System.out.println("  [✓] Payment status updated to REFUNDED.");
        System.out.println("==========================================================");
    }

    // ──────────────────────────────────────────────
    //  HELPER METHODS
    // ──────────────────────────────────────────────

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    private boolean isValidPhone(String phone) {
        // Strip out non-digit characters if user typed spaces or hyphens
        String cleaned = phone.replaceAll("[^0-9]", "");
        return cleaned.length() == 10;
    }

    private int readInt(Scanner scanner) {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
