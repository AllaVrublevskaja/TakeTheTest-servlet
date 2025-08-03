package service;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Topic;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.baseService.BaseTopicService;
import servlet.TopicServlet;

import java.util.List;
import java.util.UUID;

public class TopicService extends BaseTopicService {
    private final EntityDao entityDao;
    private final TypeReference<List<Topic>> typeRef;

    public TopicService(EntityDao entityDao,TypeReference<List<Topic>> typeRef) {
        super(entityDao, typeRef);
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void getTopicInstance(HttpServletRequest req, String endpoint) {
        String todo = "Добавить тему";
        String heading = "Выбери тему";
        String endpointQuit = "/secure/menu";
        String todoChange = "Изменить";
        String headingChange = "Изменить тему";

        entityDao.setFile("topicFile");

        String todoTitle = getParamTodoTitle(req);
        String title = getParamTitle(req);
        String attribute = todoTitle + " " + title;

        if (title != null)
            setAttributes(req, title);

        Boolean createTopic = (Boolean) req.getSession().getAttribute("createTopic");

        TopicServlet.setRequestAttribute(req, todo, heading, endpoint, endpointQuit, todoChange, headingChange);
        List<Topic> entities = findALl();
        req.setAttribute("entities", entities);
        req.setAttribute("todoTitle", todoTitle);
        req.setAttribute("title", title);
        req.getSession().setAttribute("todoTitle", todoTitle);
        req.getSession().setAttribute("title", title);

        if (createTopic != null) {
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

    private void setAttributes(HttpServletRequest req, String title) {
        switch (title) {
            case "Тему" -> {
                req.getSession().setAttribute("createTopic", true);
                req.getSession().removeAttribute("createTest");
                req.getSession().removeAttribute("createQuestion");
                req.getSession().removeAttribute("createAnswer");
                req.getSession().removeAttribute("createUser");
            }
            case "Тест" -> {
                req.getSession().setAttribute("createTest", true);
                req.getSession().removeAttribute("createTopic");
                req.getSession().removeAttribute("createQuestion");
                req.getSession().removeAttribute("createAnswer");
                req.getSession().removeAttribute("createUser");
            }
            case "Вопрос" -> {
                req.getSession().setAttribute("createQuestion", true);
                req.getSession().removeAttribute("createTopic");
                req.getSession().removeAttribute("createTest");
                req.getSession().removeAttribute("createAnswer");
                req.getSession().removeAttribute("createUser");
            }
            case "Ответ" -> {
                req.getSession().setAttribute("createAnswer", true);
                req.getSession().removeAttribute("createTopic");
                req.getSession().removeAttribute("createTest");
                req.getSession().removeAttribute("createQuestion");
                req.getSession().removeAttribute("createUser");
            }
        }
    }

    public void createTopic(String name) {
        entityDao.setFile("topicFile");
        Topic topic = Topic.builder()
                .id(UUID.randomUUID())
                .name(name)
                .build();

        save(topic);
    }

    public void setAttributeTopic(HttpServletRequest req, String entityId) {
        entityDao.setFile("topicFile");
        UUID id = UUID.fromString(entityId);
        var topic = findById(id);
        req.getSession().setAttribute("topic", topic);
    }

    public void checkTopic(HttpServletRequest req, String entityId) {
        setAttributeTopic(req, entityId);
        Topic topic = (Topic) req.getSession().getAttribute("topic");
        if (!topic.getName().isEmpty()) {
            String colorTopic = (String) req.getSession().getAttribute("colorTopic");
            UUID topicColorId = (UUID) req.getSession().getAttribute("topicColorId");
            if (topicColorId != null && topicColorId.equals(topic.getId()))
                if (colorTopic.equals("red"))
                    colorTopic = "";
                else
                    colorTopic = "red";

            else {
                topicColorId = topic.getId();
                colorTopic = "red";
            }
            req.getSession().setAttribute("topicColorId", topicColorId);
            req.getSession().setAttribute("colorTopic", colorTopic);
        }
    }

    public void deleteTopic(HttpServletRequest req) {
        String id = req.getParameter("removeId");
        if (id != null) {
            ServletContext context = req.getServletContext();
            AnswerService answerService = (AnswerService) context.getAttribute("answerService");
            QuestionService questionService = (QuestionService) context.getAttribute("questionService");
            TestService testService = (TestService) context.getAttribute("testService");

            UUID topicId = UUID.fromString(id);
            entityDao.setFile("answerFile");
            answerService.removeByTopicId(topicId);
            entityDao.setFile("questionFile");
            questionService.removeByTopicId(topicId);
            entityDao.setFile("testFile");
            testService.removeByTopicId(topicId);
            entityDao.setFile("topicFile");
            removeById(topicId);
        }
    }
}
