/**
 * Payment.java
 * Simulates payment processing for a hotel reservation.
 * Generates unique transaction IDs and tracks payment status.
 */
public class Payment {

    private String transactionId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;

    private static int transactionCounter = 1001;

    /**
     * Constructor to initialize a payment attempt.
     * @param amount Total amount to be processed
     * @param paymentMethod Method used (Credit/Debit Card, UPI, Cash)
     */
    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PENDING";
        this.transactionId = generateTransactionId();
    }

    /**
     * Generates a unique transaction ID (e.g. TXN1001, TXN1002).
     */
    private synchronized String generateTransactionId() {
        return "TXN" + transactionCounter++;
    }

    /**
     * Simulates payment processing — marks transaction as successful.
     * @return true when payment succeeds
     */
    public boolean processPayment() {
        this.paymentStatus = "SUCCESS";
        return true;
    }

    /**
     * Displays a formatted summary receipt of the payment.
     */
    public void displayPaymentDetails() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("               PAYMENT RECEIPT                    ");
        System.out.println("==================================================");
        System.out.printf("  Transaction ID : %s%n", transactionId);
        System.out.printf("  Amount Paid    : Rs. %.0f%n", amount);
        System.out.printf("  Payment Method : %s%n", paymentMethod);
        System.out.printf("  Status         : %s%n", paymentStatus);
        System.out.println("==================================================");
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
