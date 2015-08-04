package ru.itsphere.itmoney.controllers;

import ru.itsphere.itmoney.servlets.ClientRequest;

import java.util.Map;

/**
 * Все контроллеры должны реализовывать этот интерфейс
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public interface Controller {
    
    /**
     * Находит правильный обработчик и выполняет запрос
     * Затем сериализует результат
     *
     * @param clientRequest запрос клиента
     * @return сериализуемый результат выполнения запроса
     */
    default String handleRequest(ClientRequest clientRequest) {
        Executable handler = getHandlers().get(clientRequest.getAction());
        return new ResponseCreator(handler).process(clientRequest.getParams());
    }

    /**
     * Возвращает все доступные обработчики
     *
     * @return все доступные обработчики
     */
    Map<Actions, Executable> getHandlers();
}
