import exception.SalesReportException;
import io.CsvProductReader;
import io.OutputWriter;
import io.ProductReader;
import model.Product;
import model.SalesSummary;
import service.ReportFormatter;
import service.SalesSummaryCalculator;

import java.util.List;

public class SalesReporter {

    public static void main(String[] args) {
        try {
            CliArguments cliArgs = CliArguments.parse(args);

            ProductReader reader = new CsvProductReader();
            List<Product> products = reader.readProducts(cliArgs.getCsvFilePath());

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