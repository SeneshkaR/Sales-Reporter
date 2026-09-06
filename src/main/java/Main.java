import model.Product;

public class Main {

    public static void main(String[] args) {

        Product sale = new Product(
            "P001",
            "Wireless Mouse",
            "Electronics",
            12,
            25.50
        );

        System.out.println("Product ID: " + sale.getProductId());
        System.out.println("Product Name: " + sale.getProductName());
        System.out.println("Category: " + sale.getCategory());
        System.out.println("Quantity Sold: " + sale.getQuantitySold());
        System.out.println("Unit Price: " + sale.getUnitPrice());
    }
}