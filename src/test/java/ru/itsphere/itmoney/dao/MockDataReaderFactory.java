package ru.itsphere.itmoney.dao;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.List;

/**
 * Мок для тестирования UserDAOTextFileImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class MockDataReaderFactory implements ReaderFactory {

    // в этом списке будут храниться те же строки что и в файле
    private final List<String> lines;

    public MockDataReaderFactory(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public LineNumberReader getLineNumberReader() throws Exception {
        // создаем итератор для перебора
        final Iterator<String> iterator = lines.iterator();
        // созд. ридер - никакой смысловой нагрузки
        FileReader fileReader = new FileReader(new FileDescriptor());
        return new LineNumberReader(fileReader) {
            // номер текущей строки
            private int lineNumber = 0;

            @Override
            public String readLine() throws IOException {
                // проверяем что список не пустой
                if (iterator.hasNext()) {
                    // при переходе на нов строку изменяем текущую строку на +1
                    lineNumber++;
                    // возвр следующую строку
                    return iterator.next();
                }
                // если строк в "файле" (списке) больше нет, то возвр null
                return null;
            }

            @Override
            public int getLineNumber() {
                return lineNumber;
            }
        };
    }
}
