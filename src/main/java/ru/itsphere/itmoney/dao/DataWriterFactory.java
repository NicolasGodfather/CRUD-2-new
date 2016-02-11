package ru.itsphere.itmoney.dao;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by User on 04.02.2016.
 */
public class DataWriterFactory implements WriterFactory {

    public static final String CHARSET_NAME = "UTF-8";
    public final String FILE_NAME;

    public DataWriterFactory(String file_name) {
        FILE_NAME = file_name;
    }

    @Override
    public PrintWriter getPrintWriter(boolean append) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME, append); // forgot get here append
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, CHARSET_NAME);
        return new PrintWriter(outputStreamWriter);
    }
}
