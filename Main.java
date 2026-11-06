import java.util.ArrayList;
import java.util.Scanner;

// The user interface: simple console menus.
// It only asks questions, shows results and calls HotelManager.
public class Main {

    static Scanner sc = new Scanner(System.in);
    static HotelManager manager = new HotelManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HOTEL BOOKING SYSTEM =====");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ");

            if (choice == 1)      adminSection();
            else if (choice == 2) customerSection();
            else if (choice == 0) { System.out.println("Goodbye!"); break; }
            else                  System.out.println("Invalid choice.");
        }
    }

    // ================= ADMIN =================
    static void adminSection() {
        while (true) {
            System.out.println("\n--- ADMIN ---");
            System.out.println("1. Register as admin");
            System.out.println("2. Login");
            System.out.println("0. Back");
            int choice = readInt("Choose: ");

            if (choice == 1) {
                String name = readText("Name: ");
                String email = readText("Email: ");
                if (manager.isAdminEmailTaken(email)) {
                    System.out.println("This email is already registered.");
                    continue;
                }
                String phone = readText("Phone: ");
                String password = readText("Password: ");
                manager.registerAdmin(name, email, phone, password);
                System.out.println("Registered! You can login now.");
            } else if (choice == 2) {
                String email = readText("Email: ");
                String password = readText("Password: ");
                Admin admin = manager.loginAdmin(email, password);
                if (admin == null) System.out.println("Wrong email or password.");
                else adminMenu(admin);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static void adminMenu(Admin admin) {
        System.out.println("\nWelcome, " + admin.getName() + "!");
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Register a hotel");
            System.out.println("2. View my hotels");
            System.out.println("3. View bookings of a hotel");
            System.out.println("0. Logout");
            int choice = readInt("Choose: ");

            if (choice == 1)      registerHotel(admin);
            else if (choice == 2) viewMyHotels(admin);
            else if (choice == 3) viewHotelBookings(admin);
            else if (choice == 0) return;
            else                  System.out.println("Invalid choice.");
        }
    }

    static void registerHotel(Admin admin) {
        String name = readText("Hotel name: ");
        String address = readText("Address: ");
        String pin = readPinCode();
        int rooms = readInt("Number of rooms: ");
        if (rooms <= 0) {
            System.out.println("Rooms must be at least 1.");
            return;
        }
        double price = readDouble("Price per night (Rs.): ");
        String details = readText("Other details (facilities, rating...): ");

        manager.registerHotel(admin, name, address, pin, rooms, price, details);
        System.out.println("Hotel registered successfully!");
    }

    static void viewMyHotels(Admin admin) {
        ArrayList<Hotel> hotels = admin.getMyHotels();
        if (hotels.isEmpty()) {
            System.out.println("You have not registered any hotel yet.");
            return;
        }
        for (Hotel h : hotels) {
            h.showForAdmin();
        }
    }

    static void viewHotelBookings(Admin admin) {
        ArrayList<Hotel> hotels = admin.getMyHotels();
        if (hotels.isEmpty()) {
            System.out.println("You have not registered any hotel yet.");
            return;
        }
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println((i + 1) + ". " + hotels.get(i).getName());
        }
        int pick = readInt("Pick hotel number: ");
        if (pick < 1 || pick > hotels.size()) {
            System.out.println("Invalid hotel number.");
            return;
        }
        ArrayList<Booking> bookings = hotels.get(pick - 1).getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        for (Booking b : bookings) {
            b.showForAdmin();
        }
    }

    // ================= CUSTOMER =================
    static void customerSection() {
        while (true) {
            System.out.println("\n--- CUSTOMER ---");
            System.out.println("1. Register as customer");
            System.out.println("2. Login");
            System.out.println("0. Back");
            int choice = readInt("Choose: ");

            if (choice == 1) {
                String name = readText("Name: ");
                String email = readText("Email: ");
                if (manager.isCustomerEmailTaken(email)) {
                    System.out.println("This email is already registered.");
                    continue;
                }
                String phone = readText("Phone: ");
                String password = readText("Password: ");
                manager.registerCustomer(name, email, phone, password);
                System.out.println("Registered! You can login now.");
            } else if (choice == 2) {
                String email = readText("Email: ");
                String password = readText("Password: ");
                Customer customer = manager.loginCustomer(email, password);
                if (customer == null) System.out.println("Wrong email or password.");
                else customerMenu(customer);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static void customerMenu(Customer customer) {
        System.out.println("\nWelcome, " + customer.getName() + "!");
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Search hotels by pin code and book a room");
            System.out.println("2. My bookings");
            System.out.println("0. Logout");
            int choice = readInt("Choose: ");

            if (choice == 1)      searchAndBook(customer);
            else if (choice == 2) viewMyBookings(customer);
            else if (choice == 0) return;
            else                  System.out.println("Invalid choice.");
        }
    }

    static void searchAndBook(Customer customer) {
        String pin = readPinCode();
        ArrayList<Hotel> found = manager.searchByPin(pin);

        if (found.isEmpty()) {
            System.out.println("No hotels found for pin code " + pin + ".");
            return;
        }

        System.out.println("\n" + found.size() + " hotel(s) found in " + pin + ":");
        for (int i = 0; i < found.size(); i++) {
            System.out.print((i + 1) + ". ");
            found.get(i).showForCustomer();
        }

        int pick = readInt("Pick a hotel number to book (0 to go back): ");
        if (pick == 0) return;
        if (pick < 1 || pick > found.size()) {
            System.out.println("Invalid hotel number.");
            return;
        }

        Hotel chosen = found.get(pick - 1);
        if (!chosen.hasAvailableRoom()) {
            System.out.println("Sorry, " + chosen.getName() + " is fully booked.");
            return;
        }

        int nights = readInt("Number of nights: ");
        if (nights <= 0) {
            System.out.println("Nights must be at least 1.");
            return;
        }
        System.out.print("Special requirements (press Enter to skip): ");
        String request = sc.nextLine().trim();
        if (request.isEmpty()) request = "None";

        Booking booking = manager.bookRoom(customer, chosen, nights, request);
        if (booking != null) {
            System.out.println("\nBooking confirmed!");
            booking.showForCustomer();
            System.out.println("Rooms left in " + chosen.getName() + ": " + chosen.getAvailableRooms());
        }
    }

    static void viewMyBookings(Customer customer) {
        ArrayList<Booking> bookings = customer.getMyBookings();
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings yet.");
            return;
        }
        for (Booking b : bookings) {
            b.showForCustomer();
        }
    }

    // ================= INPUT HELPERS =================
    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("This field cannot be empty.");
        }
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    // Pin code must be exactly 6 digits
    static String readPinCode() {
        while (true) {
            String pin = readText("Area pin code (6 digits): ");
            if (pin.matches("[0-9]{6}")) return pin;
            System.out.println("Invalid pin code.");
        }
    }
}
