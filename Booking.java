// One room booking: links a customer to a hotel.
public class Booking {

    private static int nextId = 1;

    private int bookingId;
    private Hotel hotel;
    private Customer customer;
    private int roomNumber;
    private int nights;
    private String specialRequest;   // simple text written by the customer

    public Booking(Hotel hotel, Customer customer, int roomNumber, int nights, String specialRequest) {
        this.bookingId = nextId++;
        this.hotel = hotel;
        this.customer = customer;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.specialRequest = specialRequest;
    }

    public double getTotalCost() {
        return nights * hotel.getPricePerNight();
    }

    // Admin view: shows who booked and what they asked for
    public void showForAdmin() {
        System.out.println("Booking #" + bookingId + " | Room " + roomNumber + " | " + nights + " night(s)");
        System.out.println("      Guest: " + customer.getName() + " | " + customer.getPhone()
                + " | " + customer.getEmail());
        System.out.println("      Special request: " + specialRequest);
    }

    // Customer view: shows hotel, room and cost
    public void showForCustomer() {
        System.out.println("Booking #" + bookingId + " | " + hotel.getName() + " (" + hotel.getAddress() + ")");
        System.out.println("      Room " + roomNumber + " | " + nights + " night(s) | Total: Rs." + getTotalCost());
        System.out.println("      Your request: " + specialRequest);
    }
}
