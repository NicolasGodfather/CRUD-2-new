package ru.itsphere.itmoney.controllers;

import java.io.Serializable;
import java.util.Map;

/**
 * Этот интерфейс должны реализовывать Actions контроллера
 * Можно использовать для lambda
 *
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface Executable {
    /**
     * Выполняет действие и сериализует результат
     *
     * @param params параметры
     * @return сериализуемый результат
     * @throws Exception
     */
    Serializable execute(Map<String, String> params) throws Exception;
}
