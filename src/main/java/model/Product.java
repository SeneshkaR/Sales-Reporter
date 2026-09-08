package model;


public class Product {
    
    private final String productId;
    private final String productName;
    private final String category;
    private final int quantitySold;
    private final double unitPrice;
    
    public Product(String productId, String productName, String category, 
                   int quantitySold, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantitySold = quantitySold;
        this.unitPrice = unitPrice;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public int getQuantitySold() {
        return quantitySold;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double calculateRevenue() {
        return quantitySold * unitPrice;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s %d $%.2f", 
            productId, productName, category, quantitySold, calculateRevenue());
    }
}
