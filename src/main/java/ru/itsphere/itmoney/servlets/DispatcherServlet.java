package ru.itsphere.itmoney.servlets;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.itsphere.itmoney.controllers.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Данный сервлет принимает POST запросы с клиента и перенаправлять их контролеру
 * <p>
 * http://it-channel.ru/
 *
 * @author Budnikov Aleksandr
 */
@Component
public class DispatcherServlet extends HttpServlet {
    /**
     * Подключили логгер к текущему классу
     */
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    public static final String CHARSET_NAME = "UTF-8";
    public static final String MAPPING_PATH = "/store/*";

    private final ControllerResolver controllerResolver;

    public DispatcherServlet(ControllerResolver controllerResolver) {
        this.controllerResolver = controllerResolver;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            ClientRequest clientRequest = getClientRequest(request);
            AbstractController controller = controllerResolver.getController(clientRequest);
            String serverResponse = controller.handleRequest(clientRequest);
            response.setCharacterEncoding(CHARSET_NAME);
            response.getWriter().println(serverResponse);
        } catch (Exception e) {
            logger.error(String.format("DispatcherServlet has thrown an exception"), e);
            throw new ServletException(e);
        }
    }

    private ClientRequest getClientRequest(HttpServletRequest request) throws IOException {
        return new Gson().fromJson(request.getReader(), ClientRequest.class);
    }
}
