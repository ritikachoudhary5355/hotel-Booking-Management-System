import java.util.ArrayList;

// A Customer IS-A User and HAS a list of bookings.
public class Customer extends User {

    private ArrayList<Booking> myBookings = new ArrayList<>();

    public Customer(String name, String email, String phone, String password) {
        super(name, email, phone, password);
    }

    public void addBooking(Booking booking) {
        myBookings.add(booking);
    }

    public ArrayList<Booking> getMyBookings() {
        return myBookings;
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
