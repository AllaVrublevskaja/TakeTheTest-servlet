package servlet;

import entity.Topic;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.TestService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/secure/tests/*")
public class TestServlet extends HttpServlet {

    TestService testService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testService = (TestService) config.getServletContext().getAttribute("testService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID topicId = testService.getParamTopicId(req);
        if (topicId == null) {
            resp.sendRedirect("/secure/menu");
            return;
        }

        endpoint = "/secure/tests?id=" + topicId;
        testService.getTestInstance(req, topicId, endpoint);

        req.getRequestDispatcher("/secure/entities.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = TopicServlet.getParam(req, "name");
        String entityId = TopicServlet.getParam(req, "entityId");
        String changeName = TopicServlet.getParam(req, "changeName");
        String changeId = TopicServlet.getParam(req, "changeId");

        Topic topic = (Topic) req.getSession().getAttribute("topic");

        if (name != null && !name.isEmpty() && changeName.isEmpty()) {
            testService.createTest(req, name);
        }

        if (changeName != null && !changeName.isEmpty() && !name.isEmpty()) {
            UUID id = UUID.fromString(changeId);
            testService.changeById(id, name);
        }

        if (entityId != null && !entityId.isEmpty()) {
            Boolean createAnswer = (Boolean) req.getSession().getAttribute("createAnswer");
            Boolean createQuestion = (Boolean) req.getSession().getAttribute("createQuestion");
            if (createQuestion != null || createAnswer != null) {
                testService.setAttributeTest(req, entityId);
                resp.sendRedirect("/secure/questions?id=" + entityId);
                return;
            }

            Boolean createTest = (Boolean) req.getSession().getAttribute("createTest");
            if (createTest == null) {
                testService.checkTest(req, entityId);
                resp.sendRedirect("/secure/tests?id=" + topic.getId());
                return;
            }
        }
        resp.sendRedirect(endpoint);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        testService.deleteTest(req);
        resp.sendRedirect(endpoint);
    }
}
