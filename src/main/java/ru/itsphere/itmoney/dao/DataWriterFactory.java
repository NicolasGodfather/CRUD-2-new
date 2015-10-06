package ru.itsphere.itmoney.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class DataWriterFactory implements WriterFactory {

    public static final String CHARSET_NAME = "UTF-8";
    public final String FILE_NAME;

    public DataWriterFactory(String fileName) {
        this.FILE_NAME = fileName;
    }

    @Override
    public PrintWriter getPrintWriter(boolean append) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, append);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET_NAME);
        return new PrintWriter(outputStreamWriter);
    }
}
