package filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class HttpMethodFilter extends HttpFilter {

    List<String> httpMethods = List.of("DELETE", "PUT", "PATCH");

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        String method = req.getParameter("_method");
        if (method != null && httpMethods.contains(method)) {
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest) {
                @Override
                public String getMethod() {
                    return method;
                }
            };
            chain.doFilter(requestWrapper, res);
        } else {
            chain.doFilter(req, res);
        }
    }
}
