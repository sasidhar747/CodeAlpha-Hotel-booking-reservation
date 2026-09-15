/**
 * Room.java
 * Represents a hotel room with its room number, category, price per night, and availability status.
 */
public class Room {

    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean available;

    /**
     * Constructor to create a new Room.
     * @param roomNumber The unique room number
     * @param category The room category (Standard, Deluxe, Suite)
     * @param pricePerNight The nightly rate in INR
     */
    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.available = true; // Rooms are available by default
    }

    // Getters and Setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Formats room details as a neat tabular row.
     */
    @Override
    public String toString() {
        String status = available ? "[Available]" : "[Booked]";
        return String.format("  %-10d %-14s %-16s %s",
                roomNumber, category, "Rs. " + (int) pricePerNight, status);
    }
}
