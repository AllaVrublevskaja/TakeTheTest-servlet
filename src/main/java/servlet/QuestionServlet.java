package servlet;

import entity.Question;
import entity.Test;
import entity.Topic;
import entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.QuestionService;
import service.TestService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/secure/questions/*")
public class QuestionServlet extends HttpServlet {

    QuestionService questionService;
    TestService testService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        questionService = (QuestionService) config.getServletContext().getAttribute("questionService");
        testService = (TestService) config.getServletContext().getAttribute("testService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID testId = questionService.getParamTestId(req);
        if (testId == null) {
            resp.sendRedirect("/secure/menu");
            return;
        }

        endpoint = "/secure/questions?id=" + testId;

        questionService.getQuestionInstance(req, testId, endpoint);
        req.getRequestDispatcher("/secure/questions.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = TopicServlet.getParam(req, "name");
        String changeName = TopicServlet.getParam(req, "changeName");
        String changeId = TopicServlet.getParam(req, "changeId");
        String next = TopicServlet.getParam(req, "next");
        String quit = TopicServlet.getParam(req, "quit");
        String check = TopicServlet.getParam(req, "check");

        Topic topic = (Topic) req.getSession().getAttribute("topic");
        UUID topicId = topic.getId();
        Test test = (Test) req.getSession().getAttribute("test");
//        UUID testId = test.getId();
        User user = (User) req.getSession().getAttribute("user");
        UUID userId = user.getId();

        if (name != null && !name.isEmpty() && changeName.isEmpty()) {
            questionService.createQuestion(req, name, topic, test, user);
        }

        if (changeName != null && !changeName.isEmpty() && !name.isEmpty()) {
            UUID id = UUID.fromString(changeId);
            questionService.changeById(id, name);
        }

        if (next != null && !next.isEmpty()) {
            int index = (int) req.getSession().getAttribute("index");
            index++;
            req.getSession().setAttribute("index", index);
            req.getSession().removeAttribute("check");
        }

        if (req.getSession().getAttribute("question") == null) {
            resp.sendRedirect("/secure/tests?id=" + topicId);
            return;
        }

        Question question1 = (Question) req.getSession().getAttribute("question");
        UUID questionId = question1.getId();

        if (quit != null && !question1.getName().isEmpty()) {
            questionService.quitQuestion(req, test, testService, userId, questionId);
            if (req.getSession().getAttribute("todoTitle") != null)
                resp.sendRedirect("/secure/tests?id=" + topicId);
            else
                resp.sendRedirect("/secure/menu");
            return;
        }

        if (check != null && !question1.getName().isEmpty()) {
            questionService.checkQuestion(req, question1);
            req.getSession().setAttribute("check", check);
        }

        resp.sendRedirect(endpoint);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        questionService.deleteQuestion(req);
        resp.sendRedirect(endpoint);
    }
}
