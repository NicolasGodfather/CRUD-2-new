package ru.itsphere.itmoney.services;

/**
 * Исключение service слоя
 */
public class ServiceException extends RuntimeException {
    /**
     * Исключение service слоя
     *
     * @param message текст описывающий условия при которых была ошибка
     * @param cause   причина (исключение)
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
