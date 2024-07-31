import java.util.List;

public class Order {
    private String orderId;
    private List<CartItem> items;
    private double totalAmount;

    public Order(String orderId, List<CartItem> items) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = calculateTotalAmount();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        this.totalAmount = calculateTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private double calculateTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Amount: " + totalAmount);
        for (CartItem item : items) {
            item.displayCartItemDetails();
        }
    }
}
