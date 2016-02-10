package ru.itsphere.itmoney.dao;

import java.io.PrintWriter;

/**
 * Created by User on 04.02.2016.
 */
public interface WriterFactory {

    // return new ready to use - PrintWriter
    /**
     * Возвращает новый и готовый к использованию PrintWriter
     *
     * @return PrintWriter
     * @throws Exception
     */
    PrintWriter getPrintWriter(boolean append) throws Exception;
}
