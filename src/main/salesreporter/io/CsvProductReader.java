package salesreporter.io;

import salesreporter.exception.InvalidCsvRowException;
import salesreporter.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads product data from a CSV file.
 * 
 */
public class CsvProductReader implements ProductReader {
    
    private final String filePath;
    
    public CsvProductReader(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public List<Product> readProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip header row
                if (lineNumber == 1) {
                    continue;
                }
                
                // Skip empty lines
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                Product product = parseLine(line, lineNumber);
                products.add(product);
            }
        }
        
        return products;
    }
    
    private Product parseLine(String line, int lineNumber) throws InvalidCsvRowException {
        String[] parts = line.split(",");
        
        if (parts.length < 5) {
            throw new InvalidCsvRowException(lineNumber, 
                "Expected 5 columns but found " + parts.length);
        }
        
        try {
            String productId = parts[0].trim();
            String productName = parts[1].trim();
            String category = parts[2].trim();
            int quantitySold = Integer.parseInt(parts[3].trim());
            double unitPrice = Double.parseDouble(parts[4].trim());
            
            return new Product(productId, productName, category, quantitySold, unitPrice);
        } catch (NumberFormatException e) {
            throw new InvalidCsvRowException(lineNumber, 
                "Invalid numeric value: " + e.getMessage());
        }
    }
}
