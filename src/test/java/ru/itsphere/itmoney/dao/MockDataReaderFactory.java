package ru.itsphere.itmoney.dao;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.List;

/**
 * Мок для тестирования UserDAOTextFileImpl
 */
public class MockDataReaderFactory implements ReaderFactory {

    private final List<String> lines;

    public MockDataReaderFactory(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public LineNumberReader getLineNumberReader() throws Exception {
        final Iterator<String> iterator = lines.iterator();
        FileReader fileReader = new FileReader(new FileDescriptor());
        return new LineNumberReader(fileReader) {

            private int lineNumber = 0;

            @Override
            public String readLine() throws IOException {
                if (iterator.hasNext()) {
                    lineNumber++;
                    return iterator.next();
                }
                return null;
            }

            @Override
            public int getLineNumber() {
                return lineNumber;
            }
        };
    }
}
