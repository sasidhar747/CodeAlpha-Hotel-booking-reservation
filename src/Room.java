/**
 * Room.java
 * Represents a hotel room with its number, category, price per night, and availability.
 */
public class Room {

    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean available;

    // Constructor
    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.available = true; // Rooms are available by default
    }

    // Getters
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

    // Setters
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Display a formatted row for this room
    @Override
    public String toString() {
        String status = available ? "Available" : "Booked";
        return String.format("%-12d %-14s %-16s %s",
                roomNumber, category, "Rs." + (int) pricePerNight, status);
    }
}
