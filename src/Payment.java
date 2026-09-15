/**
 * Payment.java
 * Simulates payment processing for a hotel reservation.
 * No real payments are processed.
 */
public class Payment {

    private String transactionId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    private static int transactionCounter = 1001;

    // Constructor
    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PENDING";
        this.transactionId = generateTransactionId();
    }

    // Generate a unique transaction ID like TXN1001, TXN1002, ...
    public String generateTransactionId() {
        return "TXN" + transactionCounter++;
    }

    // Simulate payment processing — always succeeds
    public boolean processPayment() {
        this.paymentStatus = "SUCCESS";
        return true;
    }

    // Display payment confirmation
    public void displayPaymentDetails() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("           PAYMENT DETAILS              ");
        System.out.println("========================================");
        System.out.println("  Transaction ID  : " + transactionId);
        System.out.println("  Amount          : Rs." + (int) amount);
        System.out.println("  Payment Method  : " + paymentMethod);
        System.out.println("  Payment Status  : " + paymentStatus);
        System.out.println("========================================");
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }
}
