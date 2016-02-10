package ru.itsphere.itmoney.dao;

import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Мок для тестирования UserDAOTextFileImpl
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class MockDataWriterFactory implements WriterFactory {

    // в этом списке будут храниться те же строки что и в файле
    private final List<String> lines;

    public MockDataWriterFactory(List<String> lines) {
        this.lines = lines;
    }

    public PrintWriter getPrintWriter(boolean append) throws Exception {
        // если append - false, это значит что список нужно очистить и переписать заново
        // если append - true, то список не будет очищен, а "дополнится"
        if (!append) {
            lines.clear();
        }
        // созд. райтер - никакой смысловой нагрузки
        FileWriter fileWriter = new FileWriter(new FileDescriptor());
        // созд и возвр потомок класса PrintWriter (анонимный класс)
        return new PrintWriter(fileWriter) {
            @Override
            public void println(String line) {
                // записываем в список
                lines.add(line);
            }
        };
    }
}
