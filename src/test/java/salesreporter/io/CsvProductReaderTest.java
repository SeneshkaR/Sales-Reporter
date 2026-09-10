package salesreporter.io;

import salesreporter.exception.InvalidCsvRowException;
import salesreporter.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CsvProductReader.
 * 
 */
class CsvProductReaderTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    void testReadProducts_validCsv() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "P001, Wireless Mouse, Electronics, 12, 25.50\n" +
            "P002, Notebook, Stationery, 35, 3.75\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertEquals(2, products.size());
        
        assertEquals("P001", products.get(0).getProductId());
        assertEquals("Wireless Mouse", products.get(0).getProductName());
        assertEquals("Electronics", products.get(0).getCategory());
        assertEquals(12, products.get(0).getQuantitySold());
        assertEquals(25.50, products.get(0).getUnitPrice(), 0.01);
        
        assertEquals("P002", products.get(1).getProductId());
        assertEquals("Notebook", products.get(1).getProductName());
    }
    
    @Test
    void testHeaderRowIsSkipped() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "P001, Widget, Gadgets, 5, 10.00\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertEquals(1, products.size());
        assertEquals("Widget", products.get(0).getProductName());
    }
    
    @Test
    void testEmptyLinesAreSkipped() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "P001, Widget, Gadgets, 5, 10.00\n" +
            "\n" +
            "P002, Gadget, Gadgets, 3, 20.00\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertEquals(2, products.size());
    }
    
    @Test
    void testFileNotFound() {
        CsvProductReader reader = new CsvProductReader("/nonexistent/file.csv");
        
        assertThrows(IOException.class, reader::readProducts);
    }
    
    @Test
    void testInvalidRow_missingColumns() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "P001, Widget, Gadgets\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        
        Exception exception = assertThrows(InvalidCsvRowException.class, reader::readProducts);
        assertTrue(exception.getMessage().contains("line 2"));
    }
    
    @Test
    void testInvalidRow_badNumericValue() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "P001, Widget, Gadgets, abc, 10.00\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        
        Exception exception = assertThrows(InvalidCsvRowException.class, reader::readProducts);
        assertTrue(exception.getMessage().contains("Invalid numeric value"));
    }
    
    @Test
    void testEmptyFile() throws IOException {
        File csvFile = createTempCsv("");
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertTrue(products.isEmpty());
    }
    
    @Test
    void testHeaderOnly() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertTrue(products.isEmpty());
    }
    
    @Test
    void testWhitespaceIsTrimmed() throws IOException {
        File csvFile = createTempCsv(
            "product_id, product_name, category, quantity_sold, unit_price\n" +
            "  P001 , Widget , Gadgets , 5 , 10.00  \n"
        );
        
        CsvProductReader reader = new CsvProductReader(csvFile.getAbsolutePath());
        List<Product> products = reader.readProducts();
        
        assertEquals("P001", products.get(0).getProductId());
        assertEquals("Widget", products.get(0).getProductName());
        assertEquals("Gadgets", products.get(0).getCategory());
    }
    
    @Test
    void testNoHeaderPreservesFirstProduct() throws IOException {
        File file = createTempCsv("P001, Mouse, Electronics, 12, 25.50\nP002, Pen, Stationery, 100, 0.50\n");
        List<Product> products = new CsvProductReader(file.getAbsolutePath()).readProducts();
        assertEquals(2, products.size());
        assertEquals("P001", products.get(0).getProductId());
    }

    @Test
    void testHeaderAfterBlankLine() throws IOException {
        File file = createTempCsv("\nproduct_id,product_name,category,quantity_sold,unit_price\nP001,Mouse,Electronics,12,25.50\n");
        assertEquals(1, new CsvProductReader(file.getAbsolutePath()).readProducts().size());
    }

    @Test
    void testMissingFirstRowColumnIsRejected() throws IOException {
        File file = createTempCsv("P001,Mouse,Electronics,12,\n");
        assertThrows(InvalidCsvRowException.class,
            () -> new CsvProductReader(file.getAbsolutePath()).readProducts());
    }

    private File createTempCsv(String content) throws IOException {
        File file = tempDir.resolve("test.csv").toFile();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        }
        return file;
    }
}
