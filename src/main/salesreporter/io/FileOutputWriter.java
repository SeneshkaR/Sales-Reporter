package salesreporter.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes report output to a text file.
 * 
 */
public class FileOutputWriter implements OutputWriter {
    
    private final String filePath;
    
    public FileOutputWriter(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void write(String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(content);
        }
    }
    
    public String getFilePath() {
        return filePath;
    }
}
