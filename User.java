import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private String username;
    private String password;
    private List<Order> orderHistory;

    public User(String name, String email, String username, String password) {
        super(name, email);
        this.username = username;
        this.password = password;
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        this.orderHistory.add(order);
    }

    public void displayOrderHistory() {
        for (Order order : orderHistory) {
            order.displayOrderDetails();
        }
    }
}
