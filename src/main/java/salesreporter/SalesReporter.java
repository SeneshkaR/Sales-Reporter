package salesreporter;

import salesreporter.exception.SalesReportException;
import salesreporter.io.CsvProductReader;
import salesreporter.io.OutputWriter;
import salesreporter.io.OutputWriterFactory;
import salesreporter.io.ProductReader;
import salesreporter.model.Product;
import salesreporter.model.SalesSummary;
import salesreporter.service.ReportFormatter;
import salesreporter.service.SalesSummaryCalculator;

import java.util.List;

public class SalesReporter {

    public static void main(String[] args) {
        try {
            CliArguments cliArgs = CliArguments.parse(args);

            ProductReader reader = new CsvProductReader(cliArgs.getCsvFilePath());
            List<Product> products = reader.readProducts();

            SalesSummaryCalculator calculator = new SalesSummaryCalculator();
            SalesSummary summary = calculator.calculate(products);

            ReportFormatter formatter = new ReportFormatter();
            String report = formatter.format(summary);

            OutputWriter writer = OutputWriterFactory.create(cliArgs);
            writer.write(report);

        } catch (SalesReportException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}