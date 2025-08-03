package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MenuAdminService;

import java.io.IOException;

@WebServlet("/secure/menu_admin")
public class MenuAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MenuAdminService menuAdminService = new MenuAdminService();
        req.getSession().setAttribute("menuAdmin", "menuAdmin");
        menuAdminService.getAttributeInstance(req);

        req.getRequestDispatcher("/secure/menu_admin.jsp").forward(req, resp);
    }
}
