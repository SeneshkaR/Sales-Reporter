package salesreporter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class SalesReporterIntegrationTest {
    @TempDir Path tempDir;

    private Process run(String... args) throws Exception {
        java.util.List<String> command = new java.util.ArrayList<>();
        command.add(Path.of(System.getProperty("java.home"), "bin", "java").toString());
        command.add("-cp");
        command.add(Path.of(SalesReporter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString());
        command.add("salesreporter.SalesReporter");
        java.util.Collections.addAll(command, args);
        return new ProcessBuilder(command).redirectErrorStream(true).start();
    }

    @Test void consoleAndFileReportsMatch() throws Exception {
        Process console = run("sample-data/sales.csv", "console");
        String report = new String(console.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assertEquals(0, console.waitFor());
        assertTrue(report.contains("$871.25"));
        assertTrue(report.contains("Ballpoint Pen (100 units)"));
        assertTrue(report.contains("Wireless Mouse ($306.00)"));
        Path output = tempDir.resolve("report.txt");
        Process file = run("sample-data/sales.csv", "file", output.toString());
        file.getInputStream().readAllBytes();
        assertEquals(0, file.waitFor());
        assertEquals(report, Files.readString(output));
    }

    @Test void invalidInputsExitWithClearErrors() throws Exception {
        String[][] cases = {
            {},
            {"sample-data/sales.csv", "email"},
            {"sample-data/sales.csv", "file"},
            {tempDir.resolve("missing.csv").toString(), "console"}
        };
        for (String[] args : cases) {
            Process process = run(args);
            String error = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(1, process.waitFor());
            assertTrue(error.toLowerCase().contains("error:"));
        }
        Path malformed = tempDir.resolve("bad.csv");
        Files.writeString(malformed, "P001,Mouse,Electronics\n");
        Process process = run(malformed.toString(), "console");
        String error = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assertEquals(1, process.waitFor());
        assertTrue(error.contains("line 1"));
    }
}
