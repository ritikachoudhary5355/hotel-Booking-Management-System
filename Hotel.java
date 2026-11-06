import java.util.ArrayList;

// One registered hotel. It owns its rooms count and its list of bookings.
public class Hotel {

    private static int nextId = 1;   // shared counter: gives every hotel a unique id

    private int hotelId;
    private String name;
    private String address;
    private String pinCode;          // area pin code used for searching
    private int totalRooms;
    private int availableRooms;
    private double pricePerNight;
    private String details;          // facilities, rating, rules, etc.
    private Admin owner;             // the admin who registered this hotel
    private ArrayList<Booking> bookings = new ArrayList<>();

    public Hotel(String name, String address, String pinCode, int totalRooms,
                 double pricePerNight, String details, Admin owner) {
        this.hotelId = nextId++;
        this.name = name;
        this.address = address;
        this.pinCode = pinCode;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;   // all rooms are free at the start
        this.pricePerNight = pricePerNight;
        this.details = details;
        this.owner = owner;
    }

    public int getHotelId()              { return hotelId; }
    public String getName()              { return name; }
    public String getAddress()           { return address; }
    public String getPinCode()           { return pinCode; }
    public int getTotalRooms()           { return totalRooms; }
    public int getAvailableRooms()       { return availableRooms; }
    public double getPricePerNight()     { return pricePerNight; }
    public Admin getOwner()              { return owner; }
    public ArrayList<Booking> getBookings() { return bookings; }

    public boolean hasAvailableRoom() {
        return availableRooms > 0;
    }

    // Books one room. Returns the Booking, or null if the hotel is full.
    // This is where the available room count goes down automatically.
    public Booking bookRoom(Customer customer, int nights, String specialRequest) {
        if (!hasAvailableRoom()) {
            return null;
        }
        int roomNumber = totalRooms - availableRooms + 1;   // 1, 2, 3 ...
        Booking booking = new Booking(this, customer, roomNumber, nights, specialRequest);
        bookings.add(booking);
        availableRooms--;
        return booking;
    }

    // What a customer sees in the search results
    public void showForCustomer() {
        System.out.println(name + " | " + address + " | Rs." + pricePerNight
                + "/night | Rooms available: " + availableRooms);
        System.out.println("      Details: " + details);
    }

    // What an admin sees in "My hotels"
    public void showForAdmin() {
        System.out.println("[Hotel #" + hotelId + "] " + name + " | " + address + " - " + pinCode);
        System.out.println("      Rooms: " + availableRooms + " available out of " + totalRooms
                + " | Rs." + pricePerNight + "/night");
        System.out.println("      Details: " + details);
    }
}
