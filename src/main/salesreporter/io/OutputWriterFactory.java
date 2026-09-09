package io;

import CliArguments;
import exception.InvalidArgumentException;

public final class OutputWriterFactory {

    private OutputWriterFactory() {}

    public static OutputWriter create(CliArguments args) {
        switch (args.getOutputMethod()) {
            case "console":
                return new ConsoleOutputWriter();
            case "file":
                return new FileOutputWriter(args.getOutputFilePath());
            default:
                throw new InvalidArgumentException(
                        "Unsupported output method: " + args.getOutputMethod());
        }
    }
}