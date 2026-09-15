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

    // Constructor
    public Booking(String bookingId, Customer customer, Room room, int numberOfNights,
                   String paymentMethod) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PAID";
        this.bookingStatus = "CONFIRMED";
        this.totalAmount = calculateTotal();
    }

    // Calculate total booking cost
    public double calculateTotal() {
        return room.getPricePerNight() * numberOfNights;
    }

    // Getters
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

    // Setters
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Print full booking details
    public void displayBookingDetails() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         BOOKING CONFIRMED              ");
        System.out.println("========================================");
        System.out.println("  Booking ID     : " + bookingId);
        System.out.println("  Customer       : " + customer.getCustomerName());
        System.out.println("  Phone          : " + customer.getPhoneNumber());
        System.out.println("  Room No.       : " + room.getRoomNumber());
        System.out.println("  Category       : " + room.getCategory());
        System.out.println("  Price/Night    : Rs." + (int) room.getPricePerNight());
        System.out.println("  Nights         : " + numberOfNights);
        System.out.println("  Total Amount   : Rs." + (int) totalAmount);
        System.out.println("  Payment Status : " + paymentStatus);
        System.out.println("  Payment Method : " + paymentMethod);
        System.out.println("  Booking Status : " + bookingStatus);
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format("%-10s %-16s %-10d %-10d %-16s %s",
                bookingId,
                customer.getCustomerName(),
                room.getRoomNumber(),
                numberOfNights,
                "Rs." + (int) totalAmount,
                bookingStatus);
    }
}
