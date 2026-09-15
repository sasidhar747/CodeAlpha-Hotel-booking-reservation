import java.util.Scanner;

/**
 * Main.java
 * Entry point for the Hotel Reservation System.
 * Contains the main menu and drives all user interaction.
 */
public class Main {

    public static void main(String[] args) {

        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("========================================");
        System.out.println("       HOTEL RESERVATION SYSTEM         ");
        System.out.println("        Welcome to Grand Vista Hotel    ");
        System.out.println("========================================");
        System.out.println("  Existing bookings loaded from file.   ");

        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("  Enter your choice: ");
            String input = scanner.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input. Please enter a number between 1 and 7.");
                continue;
            }

            System.out.println();

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
                    System.out.println("========================================");
                    System.out.println("  Thank you for using Hotel Reservation  ");
                    System.out.println("  System. Have a great day!              ");
                    System.out.println("========================================");
                    running = false;
                    break;

                default:
                    System.out.println("  [!] Invalid choice. Please select between 1 and 7.");
            }
        }

        scanner.close();
    }

    // Print the main menu
    private static void printMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         MAIN MENU                      ");
        System.out.println("========================================");
        System.out.println("  1. View All Rooms");
        System.out.println("  2. Search Available Rooms");
        System.out.println("  3. Make Reservation");
        System.out.println("  4. View Booking Details");
        System.out.println("  5. View All Bookings");
        System.out.println("  6. Cancel Reservation");
        System.out.println("  7. Exit");
        System.out.println("========================================");
    }
}
