package exception;

public class SalesReportException extends RuntimeException {
    public SalesReportException(String message) { super(message); }
    public SalesReportException(String message, Throwable cause) { super(message, cause); }
}