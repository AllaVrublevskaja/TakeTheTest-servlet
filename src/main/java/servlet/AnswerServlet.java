package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.AnswerService;

import java.io.IOException;

@WebServlet(urlPatterns = "/secure/answers")
public class AnswerServlet extends HttpServlet {

    AnswerService answerService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        endpoint = "/secure/questions";
        answerService = (AnswerService) config.getServletContext().getAttribute("answerService");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = TopicServlet.getParam(req, "name");
        String answerId = TopicServlet.getParam(req, "answerId");
        String changeName = TopicServlet.getParam(req, "changeName");
        String changeId = TopicServlet.getParam(req, "changeId");
        boolean isCorrect = Boolean.parseBoolean(TopicServlet.getParam(req, "isCorrect"));
        String color = "red";

        if (name != null && !name.isEmpty() && changeName.isEmpty()) {
            answerService.createAnswer(req, name, isCorrect);
        }

        if (changeName != null && !changeName.isEmpty() && !name.isEmpty()) {
            answerService.changeAnswer(name, changeId, isCorrect);
        }

        if (answerId != null && !answerId.isEmpty()) {
            answerService.checkUncheckAnswer(req, answerId, color);
        }
        answerService.addAnswer(req);
        resp.sendRedirect(endpoint);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        answerService.deleteAnswer(req);
        resp.sendRedirect(endpoint);
    }
}
