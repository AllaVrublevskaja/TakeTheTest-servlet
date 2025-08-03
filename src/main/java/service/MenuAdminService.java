package service;

import jakarta.servlet.http.HttpServletRequest;

public class MenuAdminService {
    public void getAttributeInstance(HttpServletRequest req) {
        String todoTitle = getParamTodoTitle(req);
        String title = getParamTitle(req);

        req.setAttribute("todoTitle", todoTitle);
        req.setAttribute("title", title);
        req.getSession().setAttribute("todoTitle", todoTitle);
        req.getSession().setAttribute("title", title);
        req.getSession().removeAttribute("topic");
        req.getSession().removeAttribute("topicColorId");
        req.getSession().removeAttribute("colorTopic");
        req.getSession().removeAttribute("test");
        req.getSession().removeAttribute("testColorId");
        req.getSession().removeAttribute("colorTest");
        req.getSession().removeAttribute("question");
        req.getSession().removeAttribute("color");
        req.getSession().removeAttribute("createTopic");
        req.getSession().removeAttribute("createTest");
        req.getSession().removeAttribute("createQuestion");
        req.getSession().removeAttribute("createAnswer");
        req.getSession().removeAttribute("todoTitle");
        req.getSession().removeAttribute("thisTest");
        req.getSession().removeAttribute("index");
    }

    private String getParamTodoTitle(HttpServletRequest req) {
        String todoTitle;
        if (req.getParameter("todoTitle") != null && !req.getParameter("todoTitle").isEmpty()) {
            todoTitle = req.getParameter("todoTitle");
        } else {
            todoTitle = (String) req.getSession().getAttribute("todoTitle");

        }
        return todoTitle;
    }

    private String getParamTitle(HttpServletRequest req) {
        String title;
        if (req.getParameter("title") != null && !req.getParameter("title").isEmpty()) {
            title = req.getParameter("title");
        } else {
            title = (String) req.getSession().getAttribute("title");

        }
        return title;
    }
}
