package salesreporter.io;

import java.io.IOException;

/**
 * Interface for writing report output to a destination.
 * Open-Closed: new output methods can be added without modifying existing code.
 * 
 */
public interface OutputWriter {
    void write(String content) throws IOException;
}
