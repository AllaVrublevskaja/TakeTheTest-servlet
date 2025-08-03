package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/secure/menu")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("createTopic");
        req.getSession().removeAttribute("createTest");
        req.getSession().removeAttribute("createQuestion");
        req.getSession().removeAttribute("createAnswer");
        req.getSession().removeAttribute("todoTitle");
        req.getSession().removeAttribute("title");
        req.getSession().removeAttribute("thisTest");
        req.getSession().removeAttribute("index");
        req.getSession().removeAttribute("check");
        req.getSession().removeAttribute("questionStartTime");
        req.getSession().removeAttribute("topicColorId");
        req.getSession().removeAttribute("colorTopic");
        req.getSession().removeAttribute("testColorId");
        req.getSession().removeAttribute("colorTest");
        req.getSession().removeAttribute("color");
        req.getSession().removeAttribute("menuAdmin");

        req.getRequestDispatcher("/secure/menu.jsp").forward(req, resp);
    }
}
