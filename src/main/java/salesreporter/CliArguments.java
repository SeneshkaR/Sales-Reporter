package salesreporter;

import salesreporter.exception.InvalidArgumentException;

public final class CliArguments {

    private final String csvFilePath;
    private final String outputMethod;
    private final String outputFilePath;

    private CliArguments(String csvFilePath, String outputMethod, String outputFilePath) {
        this.csvFilePath = csvFilePath;
        this.outputMethod = outputMethod;
        this.outputFilePath = outputFilePath;
    }

    public static CliArguments parse(String[] args) {
        if (args.length < 2) {
            throw new InvalidArgumentException(
                    "Missing arguments.\nUsage: java SalesReporter <csv-file-path> <output-method> [output-file-path]");
        }

        String csvFilePath = args[0];
        String outputMethod = args[1].trim().toLowerCase();

        if (!outputMethod.equals("console") && !outputMethod.equals("file")) {
            throw new InvalidArgumentException(
                    "Invalid output method: '" + args[1] + "'. Must be 'console' or 'file'.");
        }

        String outputFilePath = null;
        if (outputMethod.equals("file")) {
            if (args.length < 3 || args[2].isBlank()) {
                throw new InvalidArgumentException(
                        "output-file-path is required when output-method is 'file'.");
            }
            outputFilePath = args[2];
        }

        return new CliArguments(csvFilePath, outputMethod, outputFilePath);
    }

    public String getCsvFilePath() { return csvFilePath; }
    public String getOutputMethod() { return outputMethod; }
    public String getOutputFilePath() { return outputFilePath; }
}
