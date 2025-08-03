package service;

import dao.EntityDao;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.baseService.BaseUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static servlet.TopicServlet.setRequestAttribute;

public class UserService extends BaseUserService {

    private EntityDao entityDao;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(EntityDao entityDao, BCryptPasswordEncoder passwordEncoder) {
        super(entityDao, passwordEncoder);
        this.entityDao = entityDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void getUserInstance(HttpServletRequest req, String endpoint) {
        String todo = "Добавить пользователя";
        String heading = "Выбери пользователя";
        String endpointQuit = "/secure/menu";
        String todoChange = "Изменить";
        String headingChange = "Изменить пользователя";

        entityDao.setFile("userFile");

        String todoTitle = getParamTodoTitle(req);
        String title = getParamTitle(req);
        String attribute = todoTitle + " " + title;

        setRequestAttribute(req, todo, heading, endpoint, endpointQuit, todoChange, headingChange);
        List<User> entities = findALl();
        req.setAttribute("entities", entities);
        req.setAttribute("todoTitle", todoTitle);
        req.setAttribute("title", title);
        req.getSession().setAttribute("todoTitle", todoTitle);
        req.getSession().setAttribute("title", title);
        req.getSession().setAttribute("createUser", true);
        Boolean createUser = (Boolean) req.getSession().getAttribute("createUser");

        if (createUser != null) {
            req.setAttribute("heading", attribute);
            req.getSession().setAttribute("heading", attribute);
        }
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

    public void createUser(String login, String password, Boolean admin) {
        entityDao.setFile("userFile");
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(login)
                .password(encodedPassword)
                .admin(admin)
                .build();
        save(user);
    }

    public Optional<User> findUserByCredentials(String login, String password) {
        entityDao.setFile("userFile");
        List<User> users = findALl();

        return users.stream()
                .filter(user -> user.getName().equals(login))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .findFirst();
    }

    public String findNameById(UUID id){
        entityDao.setFile("userFile");
        List<User> users = findALl();
        return users.stream()
                .filter(e -> e.getId().equals(id))
                .map(User::getName)
                .findFirst().get();
    }

    public Optional<String> findByName(String name){
        entityDao.setFile("userFile");
        List<User> users = findALl();
        return users.stream()
                .filter(e -> e.getName().equals(name))
                .map(User::getName)
                .findFirst();
    }

    public void deleteUser(HttpServletRequest req) {
        String id = req.getParameter("removeId");
        if (id != null) {
            UUID userId = UUID.fromString(id);
            entityDao.setFile("userFile");
            removeById(userId);
        }
    }
}
