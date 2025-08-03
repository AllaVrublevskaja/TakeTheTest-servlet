package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.TopicService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/secure/topics")
public class TopicServlet extends HttpServlet {

    TopicService topicService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        endpoint = "/secure/topics";
        topicService = (TopicService) config.getServletContext().getAttribute("topicService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        topicService.getTopicInstance(req, endpoint);
        req.getRequestDispatcher("/secure/entities.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String name = getParam(req, "name");
        String entityId = getParam(req, "entityId");
        String changeName = getParam(req, "changeName");
        String changeId = getParam(req, "changeId");
        String endpoint = (String) req.getSession().getAttribute("endpoint");

        if (name != null && !name.isEmpty() && changeName.isEmpty()) {
            topicService.createTopic(name);
        }

        if (changeName != null && !changeName.isEmpty() && name != null && !name.isEmpty()) {
            UUID id = UUID.fromString(changeId);
            topicService.changeById(id, name);
        }

        if (entityId != null && !entityId.isEmpty()) {
            Boolean createAnswer = (Boolean) req.getSession().getAttribute("createAnswer");
            Boolean createQuestion = (Boolean) req.getSession().getAttribute("createQuestion");
            Boolean createTest = (Boolean) req.getSession().getAttribute("createTest");
            if (createTest != null || createQuestion != null || createAnswer != null) {
                topicService.setAttributeTopic(req, entityId);
                resp.sendRedirect("/secure/tests?id=" + entityId);
                return;
            }
            Boolean createTopic = (Boolean) req.getSession().getAttribute("createTopic");
            if (createTopic == null) {
                topicService.checkTopic(req, entityId);
                resp.sendRedirect("/secure/topics");
                return;
            }
        }
        resp.sendRedirect(endpoint);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        topicService.deleteTopic(req);
        resp.sendRedirect(endpoint);
    }

    public static String getParam(HttpServletRequest req, String param) {
        param = req.getParameter(param);

        return param;
    }

    public static void setRequestAttribute(HttpServletRequest req, String todo, String heading, String endpoint,
                                           String endpointQuit, String todoChange, String headingChange) {
        boolean admin = (boolean) req.getSession().getAttribute("admin");
        String changeName = req.getParameter("change-name");

        req.getSession().setAttribute("todo", todo);
        req.getSession().setAttribute("heading", heading);
        req.getSession().setAttribute("endpoint", endpoint);
        req.getSession().setAttribute("endpointQuit", endpointQuit);
        req.getSession().setAttribute("changeName", changeName);
        req.getSession().setAttribute("todoChange", todoChange);
        req.getSession().setAttribute("headingChange", headingChange);

        req.setAttribute("admin", admin);
        req.setAttribute("todo", todo);
        req.setAttribute("heading", heading);
        req.setAttribute("endpoint", endpoint);
        req.setAttribute("endpointQuit", endpointQuit);
        req.setAttribute("changeName", changeName);
        req.setAttribute("todoChange", todoChange);
        req.setAttribute("headingChange", headingChange);
    }
}
