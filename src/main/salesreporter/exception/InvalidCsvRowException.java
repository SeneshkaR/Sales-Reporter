package salesreporter.exception;

public class InvalidCsvRowException extends SalesReportException {
    public InvalidCsvRowException(int lineNumber, String rawLine, String reason) {
        super(String.format("Invalid CSV row at line %d: %s%n  Offending line: \"%s\"",
                lineNumber, reason, rawLine));
    }
}