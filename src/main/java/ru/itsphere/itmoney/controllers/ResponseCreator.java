package ru.itsphere.itmoney.controllers;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Этот класс нужен для создания ответов сервера по шаблон
 *
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
@Component
public class ResponseCreator {
    public static final String OK_STATUS = "ok";
    public static final String FAIL_STATUS = "fail";

    public static final String STATUS_PROPERTY = "status";
    public static final String RESULT_PROPERTY = "result";
    public static final String ERROR_PROPERTY = "error";

    public static String process(Serializable result) {
        JsonObject response = new JsonObject();
        response.addProperty(STATUS_PROPERTY, OK_STATUS);
        if (result == null) {
            return response.toString();
        }
        response.addProperty(RESULT_PROPERTY, (String) result);
        return response.toString();
    }

    public static String processError(Exception e) {
        JsonObject response = new JsonObject();
        response.addProperty(STATUS_PROPERTY, FAIL_STATUS);
        response.addProperty(ERROR_PROPERTY, ExceptionUtils.getStackTrace(e));
        return response.toString();
    }
}
