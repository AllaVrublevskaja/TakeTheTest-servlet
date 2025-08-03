package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;
import service.UserService;

import java.io.IOException;
import java.util.UUID;

import static servlet.TopicServlet.getParam;

@WebServlet(urlPatterns = "/secure/users")
public class UserServlet extends HttpServlet {

    UserService userService;
    HistoryService historyService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
        historyService = (HistoryService) config.getServletContext().getAttribute("historyService");
        endpoint = "/secure/users";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.getUserInstance(req, endpoint);
        req.getRequestDispatcher("/secure/entities.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = getParam(req, "name");
        String changeName = getParam(req, "changeName");
        String changeId = getParam(req, "changeId");
        String password = req.getParameter("password");
        Boolean newAdmin = Boolean.valueOf(req.getParameter("admin"));
        String endpoint = (String) req.getSession().getAttribute("endpoint");

        if (name != null && !name.isEmpty() && changeName.isEmpty()) {
            if (userService.findByName(name).isPresent()) {
                req.setAttribute("error", "Пользователь с таким именем уже есть");
                resp.sendRedirect("/secure/users");
                return;
            }
            userService.createUser(name, password, newAdmin);
        }

        if (changeName != null && !changeName.isEmpty() && name != null && !name.isEmpty()) {
            UUID id = UUID.fromString(changeId);
            if (userService.findByName(name).isPresent()) {
                userService.changeByIdAndFields(id, name, password, newAdmin);
                resp.sendRedirect("/secure/users");
                return;
            }
            userService.changeById(id, name, password, newAdmin);
        }

        resp.sendRedirect(endpoint);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = (String) req.getSession().getAttribute("title");
        if (title != null && title.equals("Историю")) {
            historyService.delete(req);
        } else
            userService.deleteUser(req);
        resp.sendRedirect(endpoint);
    }
}
