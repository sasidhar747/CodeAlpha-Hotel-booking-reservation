/**
 * Customer.java
 * Stores customer details required for room bookings with input sanitization.
 */
public class Customer {

    private String customerName;
    private String phoneNumber;

    /**
     * Constructor to create a Customer record.
     * Sanitizes customer name to prevent CSV breaking characters.
     * @param customerName Full name of the customer
     * @param phoneNumber 10-digit contact number
     */
    public Customer(String customerName, String phoneNumber) {
        // Remove any commas to maintain CSV file integrity
        this.customerName = customerName != null ? customerName.replace(",", "").trim() : "";
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : "";
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName != null ? customerName.replace(",", "").trim() : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : "";
    }

    @Override
    public String toString() {
        return customerName + " (" + phoneNumber + ")";
    }
}
