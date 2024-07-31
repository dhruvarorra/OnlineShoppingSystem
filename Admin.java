import java.util.List;

public class Admin extends Person {
    private String adminId;

    public Admin(String name, String email, String adminId) {
        super(name, email);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void addProduct(Product product, List<Product> products) {
        products.add(product);
    }

    public void updateProduct(Product product, List<Product> products) {
        for (Product p : products) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setStockQuantity(product.getStockQuantity());
            }
        }
    }

    public void deleteProduct(String productId, List<Product> products) {
        products.removeIf(p -> p.getProductId().equals(productId));
    }
}
