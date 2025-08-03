package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;

import java.io.IOException;

@WebFilter(urlPatterns = "/registration")
public class UserValidateFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String userName = req.getParameter("login");
        String password = req.getParameter("password");
        req.getSession().removeAttribute("errorMessage");

        boolean valid = true;
        String errorMessage = "";

        if (userName == null || userName.isEmpty()) {
            errorMessage = "Имя пользователя обязательно.";
            valid = false;
        } else if (password == null || password.isEmpty()) {
            errorMessage = "Пароль обязателен.";
            valid = false;
        } else {
            ServletContext servletContext = getServletContext();
            UserService userService = (UserService) servletContext.getAttribute("userService");
            var user = userService.findUserByCredentials(userName, password);
            if (user.isPresent()) {
                errorMessage = "Пользователь уже существует.";
                valid = false;
            }
        }

        if (valid)
            chain.doFilter(request, response);
        else {
            req.getSession().setAttribute("errorMessage", errorMessage);
            resp.sendRedirect("/registration.jsp");
        }
    }
}
