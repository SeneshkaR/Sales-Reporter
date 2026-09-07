package salesreporter.service;

import salesreporter.model.Product;
import salesreporter.model.SalesSummary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalesSummaryCalculator {
    
    public SalesSummary calculate(List<Product> products) {
        Map<String, Double> revenueByCategory = calculateRevenueByCategory(products);
        Product bestSelling = findBestSellingProduct(products);
        Product highestRevenue = findHighestRevenueProduct(products);
        double grandTotal = calculateGrandTotal(products);
        
        return new SalesSummary(products, revenueByCategory, 
                                bestSelling, highestRevenue, grandTotal);
    }
    
    private Map<String, Double> calculateRevenueByCategory(List<Product> products) {
        Map<String, Double> categoryRevenue = new HashMap<>();
        
        for (Product product : products) {
            String category = product.getCategory();
            double revenue = product.calculateRevenue();
            categoryRevenue.merge(category, revenue, Double::sum);
        }
        
        return categoryRevenue;
    }
    
    Product findBestSellingProduct(List<Product> products) {
        if (products.isEmpty()) {
            return null;
        }
        
        Product best = products.get(0);
        for (int i = 1; i < products.size(); i++) {
            if (products.get(i).getQuantitySold() > best.getQuantitySold()) {
                best = products.get(i);
            }
        }
        return best;
    }
    
    Product findHighestRevenueProduct(List<Product> products) {
        if (products.isEmpty()) {
            return null;
        }
        
        Product highest = products.get(0);
        for (int i = 1; i < products.size(); i++) {
            if (products.get(i).calculateRevenue() > highest.calculateRevenue()) {
                highest = products.get(i);
            }
        }
        return highest;
    }
    
    private double calculateGrandTotal(List<Product> products) {
        double total = 0.0;
        for (Product product : products) {
            total += product.calculateRevenue();
        }
        return total;
    }
}
