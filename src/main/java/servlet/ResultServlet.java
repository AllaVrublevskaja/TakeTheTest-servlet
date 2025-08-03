package servlet;

import dao.EntityDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.QuestionService;
import service.ResultService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/secure/results/*")
public class ResultServlet extends HttpServlet {
    ResultService resultService;
    QuestionService questionService;
    EntityDao entityDao;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        entityDao = (EntityDao) config.getServletContext().getAttribute("entityDao");
        questionService = (QuestionService) config.getServletContext().getAttribute("questionService");
        resultService = (ResultService) config.getServletContext().getAttribute("resultService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID resultId = resultService.getParamResultId(req);
        if (resultId == null) {
            resp.sendRedirect("/secure/menu");
            return;
        }

        endpoint = "/secure/results?id=" + resultId;

        resultService.getResultInstance(req, questionService);
        req.getRequestDispatcher("/secure/result.jsp").forward(req, resp);
    }
}