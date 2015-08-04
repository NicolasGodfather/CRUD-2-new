package ru.itsphere.itmoney.dao;

import java.io.LineNumberReader;

/**
 * Предоставляет объекты классов, которые используются для считывания из файла
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface ReaderFactory {

    /**
     * Возвращает новый и готовый к использованию LineNumberReader
     *
     * @return LineNumberReader
     * @throws Exception
     */
    LineNumberReader getLineNumberReader() throws Exception;
}
