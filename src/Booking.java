/**
 * Booking.java
 * Represents a hotel reservation made by a customer for a specific room.
 */
public class Booking {

    private String bookingId;
    private Customer customer;
    private Room room;
    private int numberOfNights;
    private double totalAmount;
    private String paymentStatus;
    private String paymentMethod;
    private String bookingStatus; // CONFIRMED or CANCELLED

    /**
     * Constructor to create a new Booking.
     */
    public Booking(String bookingId, Customer customer, Room room, int numberOfNights, String paymentMethod) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PAID";
        this.bookingStatus = "CONFIRMED";
        this.totalAmount = calculateTotal();
    }

    /**
     * Calculates total booking price.
     */
    public double calculateTotal() {
        return room.getPricePerNight() * numberOfNights;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Prints comprehensive booking confirmation details.
     */
    public void displayBookingDetails() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("            RESERVATION CONFIRMATION              ");
        System.out.println("==================================================");
        System.out.printf("  Booking ID     : %s%n", bookingId);
        System.out.printf("  Customer Name  : %s%n", customer.getCustomerName());
        System.out.printf("  Phone Number   : %s%n", customer.getPhoneNumber());
        System.out.printf("  Room Number    : %d%n", room.getRoomNumber());
        System.out.printf("  Category       : %s%n", room.getCategory());
        System.out.printf("  Price / Night  : Rs. %.0f%n", room.getPricePerNight());
        System.out.printf("  Duration       : %d Night(s)%n", numberOfNights);
        System.out.printf("  Total Amount   : Rs. %.0f%n", totalAmount);
        System.out.printf("  Payment Status : %s%n", paymentStatus);
        System.out.printf("  Payment Method : %s%n", paymentMethod);
        System.out.printf("  Booking Status : %s%n", bookingStatus);
        System.out.println("==================================================");
    }

    /**
     * Formats booking as a row for table outputs.
     */
    @Override
    public String toString() {
        return String.format("  %-12s %-26s %-10d %-8d %-14s %s",
                bookingId,
                customer.getCustomerName(),
                room.getRoomNumber(),
                numberOfNights,
                "Rs. " + (int) totalAmount,
                bookingStatus);
    }
}
