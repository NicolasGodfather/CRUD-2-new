package ru.itsphere.itmoney.controllers;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Этот класс нужен для создания ответов сервера по шаблон
 *
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class ResponseCreator {
    public static final String OK_STATUS = "ok";
    public static final String FAIL_STATUS = "fail";

    public static final String STATUS_PROPERTY = "status";
    public static final String RESULT_PROPERTY = "result";
    public static final String ERROR_PROPERTY = "error";

    private Executable operation;

    public ResponseCreator(Executable operation) {
        this.operation = operation;
    }

    public String process(Map<String, String> params) {
        JsonObject response = new JsonObject();
        try {
            Serializable result = operation.execute(params);
            response.addProperty(STATUS_PROPERTY, OK_STATUS);
            if (result == null) {
                return response.toString();
            }
            response.addProperty(RESULT_PROPERTY, (String) result);
            return response.toString();
        } catch (Exception e) {
            response.addProperty(STATUS_PROPERTY, FAIL_STATUS);
            response.addProperty(ERROR_PROPERTY, ExceptionUtils.getStackTrace(e));
        }
        return response.toString();
    }
}
