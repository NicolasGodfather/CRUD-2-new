package ru.itsphere.itmoney.dao;

import java.io.PrintWriter;

/**
 * Предоставляет объекты классов, которые используются для записи в файла
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface WriterFactory {
    /**
     * Возвращает новый и готовый к использованию PrintWriter
     *
     * @param append если true, то пишет в конец файла
     *               если false, то пишет в начало файла
     * @return PrintWriter
     * @throws Exception
     */
    PrintWriter getPrintWriter(boolean append) throws Exception;
}
