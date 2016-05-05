package ru.itsphere.itmoney.dao;

import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Мок для тестирования UserDAOTextFileImpl
 */
public class MockDataWriterFactory implements WriterFactory {

    private final List<String> lines;

    public MockDataWriterFactory(List<String> lines) {
        this.lines = lines;
    }

    public PrintWriter getPrintWriter(boolean append) throws Exception {
        if (!append) {
            lines.clear();
        }
        FileWriter fileWriter = new FileWriter(new FileDescriptor());
        return new PrintWriter(fileWriter) {
            @Override
            public void println(String line) {
                lines.add(line);
            }
        };
    }
}
