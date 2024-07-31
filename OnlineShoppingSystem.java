import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineShoppingSystem {
    private static List<Product> products = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize some sample data
        initializeData();

        Scanner scanner = new Scanner(System.in);

        // User interaction
        while (true) {
            System.out.println("Welcome to Online Shopping System");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    userLogin(scanner);
                    break;
                case 2:
                    adminLogin(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void initializeData() {
        // Initialize some products
        products.add(new Product("P001", "Laptop", 1000.0, 10));
        products.add(new Product("P002", "Smartphone", 500.0, 20));
        products.add(new Product("P003", "Tablet", 300.0, 15));

        // Initialize some users
        users.add(new User("John Doe", "john@example.com", "john", "password"));
        users.add(new User("Jane Smith", "jane@example.com", "jane", "password"));

        // Initialize some admins
        admins.add(new Admin("Admin1", "admin1@example.com", "A001"));
    }

    private static void userLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                userMenu(scanner, user);
                return;
            }
        }

        System.out.println("Invalid username or password.");
    }

    private static void userMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    placeOrder(scanner, user);
                    break;
                case 3:
                    user.displayOrderHistory();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void viewProducts() {
        for (Product product : products) {
            product.displayProductDetails();
        }
    }

    private static void placeOrder(Scanner scanner, User user) {
        List<CartItem> cartItems = new ArrayList<>();

        while (true) {
            System.out.println("Enter product ID to add to cart (or 'done' to finish):");
            String productId = scanner.nextLine();

            if (productId.equals("done")) {
                break;
            }

            Product product = null;
            for (Product p : products) {
                if (p.getProductId().equals(productId)) {
                    product = p;
                    break;
                }
            }

            if (product == null) {
                System.out.println("Product not found.");
                continue;
            }

            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (quantity > product.getStockQuantity()) {
                System.out.println("Insufficient stock.");
                continue;
            }

            cartItems.add(new CartItem(product, quantity));
        }

        Order order = new Order("O" + (user.getOrderHistory().size() + 1), cartItems);
        user.addOrder(order);

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
        }

        System.out.println("Order placed successfully!");
        order.displayOrderDetails();
    }

    private static void adminLogin(Scanner scanner) {
        System.out.println("Enter admin ID:");
        String adminId = scanner.nextLine();

        for (Admin admin : admins) {
            if (admin.getAdminId().equals(adminId)) {
                System.out.println("Login successful!");
                adminMenu(scanner, admin);
                return;
            }
        }

        System.out.println("Invalid admin ID.");
    }

    private static void adminMenu(Scanner scanner, Admin admin) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner, admin);
                    break;
                case 2:
                    updateProduct(scanner, admin);
                    break;
                case 3:
                    deleteProduct(scanner, admin);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner, Admin admin) {
        System.out.println("Enter product ID:");
        String productId = scanner.nextLine();
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = scanner.nextDouble();
        System.out.println("Enter stock quantity:");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine();  // consume newline

        Product product = new Product(productId, name, price, stockQuantity);
        admin.addProduct(product, products);

        System.out.println("Product added successfully!");
    }

    private static void updateProduct(Scanner scanner, Admin admin) {
        System.out.println("Enter product ID to update:");
        String productId = scanner.nextLine();

        Product product = null;
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }
        }

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Enter new product name:");
        String name = scanner.nextLine();
        System.out.println("Enter new product price:");
        double price = scanner.nextDouble();
        System.out.println("Enter new stock quantity:");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine();  // consume newline

        product.setName(name);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);

        admin.updateProduct(product, products);

        System.out.println("Product updated successfully!");
    }

    private static void deleteProduct(Scanner scanner, Admin admin) {
        System.out.println("Enter product ID to delete:");
        String productId = scanner.nextLine();

        admin.deleteProduct(productId, products);

        System.out.println("Product deleted successfully!");
    }
}
