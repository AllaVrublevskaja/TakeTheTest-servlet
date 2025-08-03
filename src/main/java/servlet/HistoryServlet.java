package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.HistoryService;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/secure/histories/*")
public class HistoryServlet extends HttpServlet {
    HistoryService historyService;
    String endpoint;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        historyService = (HistoryService) config.getServletContext().getAttribute("historyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = null;
        String id = req.getParameter("id");
        if (id != null) {
            userId = UUID.fromString(req.getParameter("id"));
            endpoint = "/secure/histories?id=" + userId;
        } else
            endpoint = "/secure/histories";

        historyService.getHistoryInstance(req, userId);

        req.getRequestDispatcher("/secure/history.jsp").forward(req, resp);
    }
}
