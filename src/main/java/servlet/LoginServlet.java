package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("errorMessage");
        req.getRequestDispatcher("/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        userSaveSession(req, resp, login, password, userService);

    }

    static void userSaveSession(HttpServletRequest req, HttpServletResponse resp,
                                String login, String password,
                                UserService userService) throws IOException {

        var user = userService.findUserByCredentials(login, password);
        if(user.isPresent()) {
            req.getSession().setAttribute("user", user.get());
            req.getSession().setAttribute("admin", user.get().isAdmin());
            resp.sendRedirect("/secure/menu");
        }
         else
            resp.sendRedirect("/registration");
    }
}
