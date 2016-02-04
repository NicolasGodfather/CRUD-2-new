package ru.itsphere.itmoney.dao;

import java.io.PrintWriter;

/**
 * Created by User on 04.02.2016.
 */
public interface WriterFactory {

    PrintWriter getPrintWriter(boolean append) throws Exception;
}
