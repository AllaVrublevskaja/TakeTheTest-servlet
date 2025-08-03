package filter;

import dao.EntityDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AnswerService;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class TestTimeFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse resp = (HttpServletResponse) res;
        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("testStartTime") != null &&
                session.getAttribute("menuAdmin") == null ) {
            long testingTime = 5;
            if (session.getAttribute("testingTime") != null)
                testingTime = (long) session.getAttribute("testingTime");

            long TIME_LIMIT_MS = testingTime * 60 * 1000;   //  минуты
            long testStartTime = (long) session.getAttribute("testStartTime");
            long currentTime = System.currentTimeMillis();
            if (currentTime - testStartTime > TIME_LIMIT_MS) {

                ServletContext servletContext = getServletContext();
                EntityDao entityDao = (EntityDao) servletContext.getAttribute("entityDao");
                AnswerService answerService = (AnswerService) servletContext.getAttribute("answerService");
                answerService.changeAnswerByInstance(entityDao);

                session.removeAttribute("testStartTime");

                String testTimeFinish = "Время прохождения теста истекло! Попробуйте еще раз.";
                session.setAttribute("testTimeFinish", testTimeFinish);
                session.removeAttribute("topic");
                session.removeAttribute("test");

                resp.sendRedirect("/secure/menu");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
