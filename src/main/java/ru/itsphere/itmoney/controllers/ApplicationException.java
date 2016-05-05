package ru.itsphere.itmoney.controllers;

/**
 * Исключение приложения
 */
public class ApplicationException extends RuntimeException {
    /**
     * Исключение приложения
     *
     * @param message текст описывающий условия при которых была ошибка
     * @param cause   причина (исключение)
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
