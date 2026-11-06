// Parent class for everyone who uses the system.
// "abstract" means we never create a plain User, only an Admin or a Customer.
public abstract class User {

    // private = encapsulation: outside code cannot touch these directly
    private String name;
    private String email;
    private String phone;
    private String password;

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Returns true if the given email and password match this user
    public boolean checkLogin(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    // Every child class must say what its role is (abstraction + polymorphism)
    public abstract String getRole();
}
