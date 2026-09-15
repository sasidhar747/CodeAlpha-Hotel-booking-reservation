import java.io.*;
import java.util.ArrayList;

/**
 * FileManager.java
 * Handles reading and writing booking records to disk using Java File I/O.
 * Data is stored in CSV format within the `data/bookings.txt` file.
 *
 * CSV Format Schema:
 * bookingId,customerName,phoneNumber,roomNumber,numberOfNights,totalAmount,paymentStatus,paymentMethod,bookingStatus
 */
public class FileManager {

    private static final String FILE_PATH = "data/bookings.txt";

    /**
     * Saves a single booking record to disk (append mode).
     * @param booking The Booking object to persist
     */
    public static void saveBooking(Booking booking) {
        ensureDataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(bookingToCsv(booking));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("  [!] Warning: Could not save booking to file. " + e.getMessage());
        }
    }

    /**
     * Overwrites disk storage with the full list of bookings (used during cancellation updates).
     * @param bookings Complete list of current bookings
     */
    public static void saveAllBookings(ArrayList<Booking> bookings) {
        ensureDataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Booking booking : bookings) {
                writer.write(bookingToCsv(booking));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("  [!] Warning: Could not update bookings file. " + e.getMessage());
        }
    }

    /**
     * Loads all booking records from disk storage on application startup.
     * @param rooms List of initialized rooms in the hotel
     * @return List of loaded Booking objects
     */
    public static ArrayList<Booking> loadBookings(ArrayList<Room> rooms) {
        ArrayList<Booking> bookings = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return bookings; // Return empty list if no saved data exists yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Booking booking = csvToBooking(line, rooms);
                if (booking != null) {
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            System.out.println("  [!] Warning: Could not load bookings from file. " + e.getMessage());
        }

        return bookings;
    }

    // ──────────────────────────────────────────────
    //  PRIVATE HELPER METHODS
    // ──────────────────────────────────────────────

    /**
     * Converts a Booking object into a single CSV line string.
     */
    private static String bookingToCsv(Booking booking) {
        return String.format("%s,%s,%s,%d,%d,%d,%s,%s,%s",
                booking.getBookingId(),
                booking.getCustomer().getCustomerName().replace(",", ""),
                booking.getCustomer().getPhoneNumber(),
                booking.getRoom().getRoomNumber(),
                booking.getNumberOfNights(),
                (int) booking.getTotalAmount(),
                booking.getPaymentStatus(),
                booking.getPaymentMethod(),
                booking.getBookingStatus());
    }

    /**
     * Parses a CSV string back into a Booking object.
     */
    private static Booking csvToBooking(String line, ArrayList<Room> rooms) {
        try {
            String[] parts = line.split(",", 9);
            if (parts.length < 9) return null;

            String bookingId      = parts[0].trim();
            String customerName   = parts[1].trim();
            String phoneNumber    = parts[2].trim();
            int    roomNumber     = Integer.parseInt(parts[3].trim());
            int    numberOfNights  = Integer.parseInt(parts[4].trim());
            String paymentStatus  = parts[6].trim();
            String paymentMethod  = parts[7].trim();
            String bookingStatus  = parts[8].trim();

            // Locate corresponding room object
            Room room = null;
            for (Room r : rooms) {
                if (r.getRoomNumber() == roomNumber) {
                    room = r;
                    break;
                }
            }
            if (room == null) return null;

            // Update room availability based on stored booking status
            if (bookingStatus.equalsIgnoreCase("CONFIRMED")) {
                room.setAvailable(false);
            }

            Customer customer = new Customer(customerName, phoneNumber);
            Booking booking = new Booking(bookingId, customer, room, numberOfNights, paymentMethod);
            booking.setPaymentStatus(paymentStatus);
            booking.setBookingStatus(bookingStatus);

            return booking;

        } catch (Exception e) {
            System.out.println("  [!] Warning: Skipping malformed record in file.");
            return null;
        }
    }

    /**
     * Ensures the `data/` directory exists before writing files.
     */
    private static void ensureDataDirectoryExists() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
