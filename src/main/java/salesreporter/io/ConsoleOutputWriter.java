package salesreporter.io;

/**
 * Writes report output to the console (standard output).
 * 
 */
public class ConsoleOutputWriter implements OutputWriter {
    
    @Override
    public void write(String content) {
        System.out.print(content);
    }
}
