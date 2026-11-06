import java.util.ArrayList;
import java.util.HashMap;

// The "brain" of the system. It stores all data and does all the work.
// The UI (Main) only talks to this class.
public class HotelManager {

    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    // Key = pin code, Value = list of hotels in that pin code.
    // A HashMap lets us find all hotels of a pin code instantly.
    private HashMap<String, ArrayList<Hotel>> hotelsByPin = new HashMap<>();

    // ---------- Admin ----------
    public boolean isAdminEmailTaken(String email) {
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public void registerAdmin(String name, String email, String phone, String password) {
        admins.add(new Admin(name, email, phone, password));
    }

    public Admin loginAdmin(String email, String password) {
        for (Admin a : admins) {
            if (a.checkLogin(email, password)) return a;
        }
        return null;   // no match
    }

    public Hotel registerHotel(Admin admin, String name, String address, String pinCode,
                               int rooms, double price, String details) {
        Hotel hotel = new Hotel(name, address, pinCode, rooms, price, details, admin);
        admin.addHotel(hotel);

        // put the hotel in the map under its pin code
        if (!hotelsByPin.containsKey(pinCode)) {
            hotelsByPin.put(pinCode, new ArrayList<Hotel>());
        }
        hotelsByPin.get(pinCode).add(hotel);
        return hotel;
    }

    // ---------- Customer ----------
    public boolean isCustomerEmailTaken(String email) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public void registerCustomer(String name, String email, String phone, String password) {
        customers.add(new Customer(name, email, phone, password));
    }

    public Customer loginCustomer(String email, String password) {
        for (Customer c : customers) {
            if (c.checkLogin(email, password)) return c;
        }
        return null;
    }

    public ArrayList<Hotel> searchByPin(String pinCode) {
        if (hotelsByPin.containsKey(pinCode)) {
            return hotelsByPin.get(pinCode);
        }
        return new ArrayList<Hotel>();   // empty list = nothing found
    }

    public Booking bookRoom(Customer customer, Hotel hotel, int nights, String request) {
        Booking booking = hotel.bookRoom(customer, nights, request);   // reduces available rooms
        if (booking != null) {
            customer.addBooking(booking);
        }
        return booking;
    }
}
