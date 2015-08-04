package ru.itsphere.itmoney.servlets;

import ru.itsphere.itmoney.controllers.Controller;
import ru.itsphere.itmoney.controllers.Controllers;
import ru.itsphere.itmoney.controllers.UserController;

import java.util.Map;

/**
 * По clientRequest определяет какой контроллер должен обработать запрос
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class ControllerResolver {

    private Map<Controllers, UserController> controllers;

    public Controller getController(ClientRequest clientRequest) {
        Controller controller = controllers.get(clientRequest.getController());
        if (controller == null) {
            throw new RuntimeException("Controller '" + clientRequest.getController() + "' wasn't registered");
        }
        return controller;
    }

    public void setControllers(Map<Controllers, UserController> controllers) {
        this.controllers = controllers;
    }
}
