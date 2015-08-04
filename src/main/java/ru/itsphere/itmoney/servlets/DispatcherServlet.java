package ru.itsphere.itmoney.servlets;

import com.google.gson.Gson;
import ru.itsphere.itmoney.controllers.Controller;
import ru.itsphere.itmoney.controllers.Controllers;
import ru.itsphere.itmoney.controllers.UserController;
import ru.itsphere.itmoney.dao.UserDAO;
import ru.itsphere.itmoney.dao.UserDAOHashMapImpl;
import ru.itsphere.itmoney.domain.User;
import ru.itsphere.itmoney.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный сервлет принимает POST запросы с клиента и перенаправлять их контролеру
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
@WebServlet(DispatcherServlet.MAPPING_PATH)
public class DispatcherServlet extends HttpServlet {

    public static final String CHARSET_NAME = "UTF-8";
    public static final String MAPPING_PATH = "/store/*";

    private ControllerResolver controllerResolver;

    @Override
    public void init() throws ServletException {
        UserDAO userDAOHashMap = getUserDAOHashMap();

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDAO(userDAOHashMap);

        UserController userController = new UserController();
        userController.setUserService(userServiceImpl);

        controllerResolver = getControllerResolver(userController);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            ClientRequest clientRequest = getClientRequest(request);
            Controller controller = controllerResolver.getController(clientRequest);
            String serverResponse = controller.handleRequest(clientRequest);
            response.setCharacterEncoding(CHARSET_NAME);
            response.getWriter().println(serverResponse);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private ClientRequest getClientRequest(HttpServletRequest request) throws IOException {
        return new Gson().fromJson(request.getReader(), ClientRequest.class);
    }

    private ControllerResolver getControllerResolver(UserController userController) {
        ControllerResolver controllerResolver = new ControllerResolver();
        Map<Controllers, UserController> controllers = new HashMap<>();
        controllers.put(Controllers.USER, userController);
        controllerResolver.setControllers(controllers);
        return controllerResolver;
    }

    private UserDAOHashMapImpl getUserDAOHashMap() {
        return new UserDAOHashMapImpl(initStore());
    }

    private Map<Integer, User> initStore() {
        Map<Integer, User> store = new HashMap<>();
        store.put(0, new User(0, "Alex"));
        store.put(1, new User(1, "Bob"));
        return store;
    }
}
