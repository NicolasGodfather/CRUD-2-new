package ru.itsphere.itmoney.controllers;

import com.google.gson.Gson;
import ru.itsphere.itmoney.domain.User;
import ru.itsphere.itmoney.services.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Это класс контроллер для работы с пользователями он обрабатывает запросы DispatcherServlet
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
public class UserController implements Controller {
    private UserService userService;
    private Map<Actions, Executable> handlers;

    public UserController() {
        handlers = new HashMap<>();
        handlers.put(Actions.GET_BY_ID, getById);
        handlers.put(Actions.SAVE, save);
        handlers.put(Actions.GET_ALL, getAll);
        handlers.put(Actions.DELETE_BY_ID, deleteById);
    }

    private Executable getById = (params) -> {
        if (params.get("id") == null) {
            return null;
        }
        int id = Integer.parseInt(params.get("id"));
        User user = userService.getById(id);
        return wrap(user);
    };

    private Executable save = (params) -> {
        User newUser = convertMapToUser(params);
        if (params.get("id") == null) {
            return wrap(userService.save(newUser));
        } else {
            return wrap(userService.update(newUser));
        }
    };

    private Executable getAll = (params) -> {
        return wrap(userService.getAll());
    };

    private Executable deleteById = (params) -> {
        if (params.get("id") == null) {
            return null;
        }
        int id = Integer.parseInt(params.get("id"));
        userService.deleteById(id);
        return null;
    };

    private String wrap(Object object) {
        return new Gson().toJson(object);
    }

    private User convertMapToUser(Map<String, String> params) {
        String name = params.get("name");
        if (params.get("id") == null) {
            return new User(name);
        }
        int id = Integer.parseInt(params.get("id"));
        return new User(id, name);
    }

    @Override
    public Map<Actions, Executable> getHandlers() {
        return this.handlers;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
