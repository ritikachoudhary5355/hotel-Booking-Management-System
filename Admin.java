import java.util.ArrayList;

// An Admin IS-A User (inheritance) and HAS hotels (composition).
public class Admin extends User {

    private ArrayList<Hotel> myHotels = new ArrayList<>();

    public Admin(String name, String email, String phone, String password) {
        super(name, email, phone, password);   // pass data to the parent class
    }

    public void addHotel(Hotel hotel) {
        myHotels.add(hotel);
    }

    public ArrayList<Hotel> getMyHotels() {
        return myHotels;
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
