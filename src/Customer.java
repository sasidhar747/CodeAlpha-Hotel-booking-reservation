/**
 * Customer.java
 * Stores customer details required for a reservation.
 */
public class Customer {

    private String customerName;
    private String phoneNumber;

    // Constructor
    public Customer(String customerName, String phoneNumber) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return customerName + " (" + phoneNumber + ")";
    }
}
