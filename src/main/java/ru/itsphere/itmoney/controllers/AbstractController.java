package ru.itsphere.itmoney.controllers;

import ru.itsphere.itmoney.servlets.ClientRequest;

import java.io.Serializable;
import java.util.Map;

/**
 * Все контроллеры должны расширять этот класс
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public abstract class AbstractController {
    
    /**
     * Находит правильный обработчик и выполняет запрос
     * Затем сериализует результат
     *
     * @param clientRequest запрос клиента
     * @return сериализуемый результат выполнения запроса
     */
    public String handleRequest(ClientRequest clientRequest) {
        try {
            Executable handler = getHandlers().get(clientRequest.getAction());
            Serializable result = handler.execute(clientRequest.getParams());
            return ResponseCreator.process(result);
        } catch (Exception e) {
            return ResponseCreator.processError(e);
        }
    }

    /**
     * Возвращает все доступные обработчики
     *
     * @return все доступные обработчики
     */
    protected abstract Map<Actions, Executable> getHandlers();
}
