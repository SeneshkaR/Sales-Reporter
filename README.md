# Sales-Reporter

SENG 21222 – Software Construction Assignment 1, University of Kelaniya.
A Java command-line tool that reads product sales CSV data and produces a summary.

## Requirements

- JDK 11 or later (including the Java compiler)
- Apache Maven 3.6 or later

Run commands from the repository root.

## Build and test

```sh
mvn clean verify
```

## Run

Print to the console:

```sh
java -jar target/sales-reporter-1.0.0.jar sample-data/sales.csv console
```

Save a text report:

```sh
java -jar target/sales-reporter-1.0.0.jar sample-data/sales.csv file report.txt
```

Alternatively, run the compiled entry point:

```sh
java -cp target/classes salesreporter.SalesReporter sample-data/sales.csv console
```

Quote paths containing spaces. File output replaces an existing output file.

## CSV format

```csv
product_id,product_name,category,quantity_sold,unit_price
P001,Wireless Mouse,Electronics,12,25.50
P002,Notebook,Stationery,35,3.75
```

The header is optional. Blank lines are ignored; each record must have five nonempty columns.
This reader supports simple comma-separated fields, not quoted fields containing commas.
Invalid arguments, missing files, or malformed rows produce an error and exit code 1.

## Report

Includes revenue per product and category, best seller by quantity, highest-revenue product,
and grand total. The supplied sample totals $871.25; Ballpoint Pen is the best seller
with 100 units and Wireless Mouse has the highest revenue at $306.00.

## Design

- `model`: product data and computed summary.
- `service`: calculation and report formatting, kept separate from file I/O.
- `ProductReader` / `CsvProductReader`: input abstraction and CSV implementation.
- `OutputWriter`: output strategy with console and file implementations.
- `OutputWriterFactory`: selects the output strategy.
- `CliArguments` and exceptions: argument validation and error reporting.

To add an output method, implement `OutputWriter` and register the new option in
`CliArguments` and `OutputWriterFactory`; calculation and formatting remain unchanged.

## Tests

JUnit tests cover calculations, best-seller detection, CSV validation and optional headers.
Process-level tests check console/file output and CLI error exit codes.
GitHub Actions builds, tests, and smoke-tests the packaged JAR.

## Submission

Prepare one group PDF containing this repository link and a section for each member
with their Git username and actual contribution. Follow the assignment handout for
submission details; the repository does not replace the required PDF.
