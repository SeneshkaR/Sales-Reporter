package salesreporter.service;

import salesreporter.model.Product;
import salesreporter.model.SalesSummary;

import java.util.Map;

public class ReportFormatter {
    
    private static final String SEPARATOR = "============================================";
    private static final String SECTION_HEADER = "--- %s ---";
    
    public String format(SalesSummary summary) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(SEPARATOR).append("\n");
        sb.append(" PRODUCT SALES SUMMARY REPORT\n");
        sb.append(SEPARATOR).append("\n");
        sb.append("\n");
        
        sb.append(String.format(SECTION_HEADER, "Revenue Per Product")).append("\n");
        for (Product product : summary.getProducts()) {
            sb.append(formatProductLine(product)).append("\n");
        }
        sb.append("\n");
        
        sb.append(String.format(SECTION_HEADER, "Revenue Per Category")).append("\n");
        for (Map.Entry<String, Double> entry : summary.getRevenueByCategory().entrySet()) {
            sb.append(formatCategoryLine(entry.getKey(), entry.getValue())).append("\n");
        }
        sb.append("\n");
        
        sb.append(String.format(SECTION_HEADER, "Highlights")).append("\n");
        sb.append(formatHighlights(summary)).append("\n");
        
        sb.append(SEPARATOR).append("\n");
        
        return sb.toString();
    }
    
    private String formatProductLine(Product product) {
        return String.format("%-5s %-20s %-15s $%8.2f",
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.calculateRevenue());
    }
    
    private String formatCategoryLine(String category, double revenue) {
        return String.format("%-15s : $%8.2f", category, revenue);
    }
    
    private String formatHighlights(SalesSummary summary) {
        StringBuilder sb = new StringBuilder();
        
        Product bestSelling = summary.getBestSellingProduct();
        if (bestSelling != null) {
            sb.append(String.format("%-22s : %s (%d units)",
                    "Best-Selling Product",
                    bestSelling.getProductName(),
                    bestSelling.getQuantitySold()));
        }
        sb.append("\n");
        
        Product highestRevenue = summary.getHighestRevenueProduct();
        if (highestRevenue != null) {
            sb.append(String.format("%-22s : %s ($%.2f)",
                    "Highest Revenue",
                    highestRevenue.getProductName(),
                    highestRevenue.calculateRevenue()));
        }
        sb.append("\n");
        
        sb.append(String.format("%-22s : $%.2f",
                "Grand Total Revenue",
                summary.getGrandTotalRevenue()));
        
        return sb.toString();
    }
}
