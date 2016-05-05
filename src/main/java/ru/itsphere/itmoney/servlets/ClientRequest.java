package ru.itsphere.itmoney.servlets;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Этот класс имитирует пользовательский запрос
 */

public class ClientRequest {

    private String action;
    private String controller;
    private Map<String, String> params;

    public ClientRequest(String action, String controller, Map<String, String> params) {
        this.action = action;
        this.controller = controller;
        this.params = params;
    }

    public String getAction() {
        return action;
    }

    public String getController() {
        return controller;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "action='" + action + '\'' +
                ", controller='" + controller + '\'' +
                ", params=" + params +
                '}';
    }
}
