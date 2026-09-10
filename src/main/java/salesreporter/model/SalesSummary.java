package salesreporter.model;

import java.util.List;
import java.util.Map;

public class SalesSummary {
    
    private final List<Product> products;
    private final Map<String, Double> revenueByCategory;
    private final Product bestSellingProduct;
    private final Product highestRevenueProduct;
    private final double grandTotalRevenue;
    
    public SalesSummary(List<Product> products, Map<String, Double> revenueByCategory,
                        Product bestSellingProduct, Product highestRevenueProduct,
                        double grandTotalRevenue) {
        this.products = products;
        this.revenueByCategory = revenueByCategory;
        this.bestSellingProduct = bestSellingProduct;
        this.highestRevenueProduct = highestRevenueProduct;
        this.grandTotalRevenue = grandTotalRevenue;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public Map<String, Double> getRevenueByCategory() {
        return revenueByCategory;
    }
    
    public Product getBestSellingProduct() {
        return bestSellingProduct;
    }
    
    public Product getHighestRevenueProduct() {
        return highestRevenueProduct;
    }
    
    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }
}
