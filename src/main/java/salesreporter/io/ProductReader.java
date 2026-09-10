package salesreporter.io;

import salesreporter.model.Product;
import java.io.IOException;
import java.util.List;

/** Reads sales records independently of their source. */
public interface ProductReader {
    List<Product> readProducts() throws IOException;
}
