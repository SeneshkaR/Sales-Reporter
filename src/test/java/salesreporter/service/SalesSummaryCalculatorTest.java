package salesreporter.service;

import salesreporter.model.Product;
import salesreporter.model.SalesSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SalesSummaryCalculator.
 * Tests revenue calculation and best-seller detection as required by the assignment.
 * 
 */
class SalesSummaryCalculatorTest {
    
    private SalesSummaryCalculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new SalesSummaryCalculator();
    }
    
    @Test
    void testRevenueCalculation_singleProduct() {
        Product product = new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50);
        List<Product> products = Collections.singletonList(product);
        
        SalesSummary summary = calculator.calculate(products);
        
        assertEquals(306.00, summary.getGrandTotalRevenue(), 0.01);
    }
    
    @Test
    void testRevenueCalculation_multipleProducts() {
        List<Product> products = Arrays.asList(
            new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
            new Product("P002", "Notebook", "Stationery", 35, 3.75),
            new Product("P003", "USB Hub", "Electronics", 8, 18.00),
            new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
            new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
        
        SalesSummary summary = calculator.calculate(products);
        
        assertEquals(871.25, summary.getGrandTotalRevenue(), 0.01);
    }
    
    @Test
    void testRevenueByCategory() {
        List<Product> products = Arrays.asList(
            new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
            new Product("P002", "Notebook", "Stationery", 35, 3.75),
            new Product("P003", "USB Hub", "Electronics", 8, 18.00),
            new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
            new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
        
        SalesSummary summary = calculator.calculate(products);
        
        assertEquals(690.00, summary.getRevenueByCategory().get("Electronics"), 0.01);
        assertEquals(181.25, summary.getRevenueByCategory().get("Stationery"), 0.01);
    }
    
    @Test
    void testBestSellingProduct_detection() {
        List<Product> products = Arrays.asList(
            new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
            new Product("P002", "Notebook", "Stationery", 35, 3.75),
            new Product("P003", "USB Hub", "Electronics", 8, 18.00),
            new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
            new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
        
        SalesSummary summary = calculator.calculate(products);
        
        assertNotNull(summary.getBestSellingProduct());
        assertEquals("Ballpoint Pen", summary.getBestSellingProduct().getProductName());
        assertEquals(100, summary.getBestSellingProduct().getQuantitySold());
    }
    
    @Test
    void testBestSellingProduct_singleProduct() {
        Product product = new Product("P001", "Mouse", "Electronics", 5, 10.00);
        List<Product> products = Collections.singletonList(product);
        
        SalesSummary summary = calculator.calculate(products);
        
        assertEquals("Mouse", summary.getBestSellingProduct().getProductName());
    }
    
    @Test
    void testBestSellingProduct_emptyList() {
        SalesSummary summary = calculator.calculate(Collections.emptyList());
        
        assertNull(summary.getBestSellingProduct());
    }
    
    @Test
    void testHighestRevenueProduct() {
        List<Product> products = Arrays.asList(
            new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
            new Product("P002", "Notebook", "Stationery", 35, 3.75),
            new Product("P003", "USB Hub", "Electronics", 8, 18.00),
            new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
            new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
        
        SalesSummary summary = calculator.calculate(products);
        
        assertNotNull(summary.getHighestRevenueProduct());
        assertEquals("Wireless Mouse", summary.getHighestRevenueProduct().getProductName());
        assertEquals(306.00, summary.getHighestRevenueProduct().calculateRevenue(), 0.01);
    }
    
    @Test
    void testHighestRevenueProduct_emptyList() {
        SalesSummary summary = calculator.calculate(Collections.emptyList());
        
        assertNull(summary.getHighestRevenueProduct());
    }
    
    @Test
    void testEmptyProductList() {
        SalesSummary summary = calculator.calculate(Collections.emptyList());
        
        assertEquals(0.0, summary.getGrandTotalRevenue(), 0.01);
        assertTrue(summary.getRevenueByCategory().isEmpty());
        assertNull(summary.getBestSellingProduct());
        assertNull(summary.getHighestRevenueProduct());
        assertTrue(summary.getProducts().isEmpty());
    }
    
    @Test
    void testProductRevenueCalculation() {
        Product product = new Product("P001", "Test", "Test", 10, 5.50);
        assertEquals(55.00, product.calculateRevenue(), 0.01);
    }
    
    @Test
    void testZeroQuantityProduct() {
        Product product = new Product("P001", "Test", "Test", 0, 10.00);
        assertEquals(0.0, product.calculateRevenue(), 0.01);
    }
    
    @Test
    void testZeroPriceProduct() {
        Product product = new Product("P001", "Free Item", "Test", 5, 0.0);
        assertEquals(0.0, product.calculateRevenue(), 0.01);
    }
}
