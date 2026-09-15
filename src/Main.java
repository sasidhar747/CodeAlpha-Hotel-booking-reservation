import java.util.Scanner;

/**
 * Main.java
 * Application entry point for the CodeAlpha Hotel Reservation System.
 * Provides a clean console-based interactive UI.
 */
public class Main {

    public static void main(String[] args) {

        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        printWelcomeHeader();

        boolean running = true;

        while (running) {
            printMainMenu();

            System.out.print("  Enter your choice (1-7): ");
            String input = scanner.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n  [!] Invalid selection. Please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {
                case 1:
                    hotel.displayAllRooms();
                    break;

                case 2:
                    hotel.searchAvailableRooms(scanner);
                    break;

                case 3:
                    hotel.makeReservation(scanner);
                    break;

                case 4:
                    hotel.displayBooking(scanner);
                    break;

                case 5:
                    hotel.displayAllBookings();
                    break;

                case 6:
                    hotel.cancelBooking(scanner);
                    break;

                case 7:
                    printExitHeader();
                    running = false;
                    break;

                default:
                    System.out.println("\n  [!] Invalid choice. Please select an option between 1 and 7.");
            }
        }

        scanner.close();
    }

    /**
     * Prints the initial system header banner.
     */
    private static void printWelcomeHeader() {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("              CODEALPHA HOTEL RESERVATION SYSTEM          ");
        System.out.println("             Welcome to Grand Vista Luxury Hotel          ");
        System.out.println("==========================================================");
        System.out.println("  [✓] System Initialized & Saved Bookings Restored.");
    }

    /**
     * Prints the main operational menu options.
     */
    private static void printMainMenu() {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("                       MAIN MENU                          ");
        System.out.println("==========================================================");
        System.out.println("  1. View All Rooms Inventory");
        System.out.println("  2. Search Available Rooms by Category");
        System.out.println("  3. Make a New Reservation");
        System.out.println("  4. View Specific Booking Details");
        System.out.println("  5. View All Reservations Log");
        System.out.println("  6. Cancel a Reservation");
        System.out.println("  7. Exit System");
        System.out.println("==========================================================");
    }

    /**
     * Prints the exit thank-you header banner.
     */
    private static void printExitHeader() {
        System.out.println();
        System.out.println("==========================================================");
        System.out.println("  Thank you for using the Hotel Reservation System!       ");
        System.out.println("  Have a wonderful day!                                   ");
        System.out.println("==========================================================");
    }
}
