package service;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Test;
import entity.Topic;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.baseService.BaseTestService;
import servlet.TopicServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestService extends BaseTestService {

    private final EntityDao entityDao;
    TypeReference<List<Test>> typeRef;

    public TestService(EntityDao entityDao, TypeReference<List<Test>> typeRef) {
        super(entityDao, typeRef);
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public UUID getParamTopicId(HttpServletRequest req) {
        UUID topicId = null;
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            topicId = UUID.fromString(req.getParameter("id"));
        } else {
            Topic topic = (Topic) req.getSession().getAttribute("topic");
            if (topic != null)
                topicId = topic.getId();
        }
        return topicId;
    }

    public void getTestInstance(HttpServletRequest req, UUID topicId, String endpoint) {
        String todo = "Добавить тест";
        String heading = "Выбери тест";
        String endpointQuit = "/secure/topics";
        String todoChange = "Изменить";
        String headingChange = "Изменить тест";

        entityDao.setFile("testFile");

        String todoTitle = (String) req.getSession().getAttribute("todoTitle");
        String title = (String) req.getSession().getAttribute("title");
        String attribute = todoTitle + " " + title;
        Boolean createTest = (Boolean) req.getSession().getAttribute("createTest");

        TopicServlet.setRequestAttribute(req, todo, heading, endpoint, endpointQuit, todoChange, headingChange);

        List<Test> entities = findAll().stream().filter(e -> e.getTopicId().equals(topicId)).toList();
        req.setAttribute("entities", entities);
        req.setAttribute("todoTitle", todoTitle);
        req.setAttribute("thisTest", true);
        req.getSession().setAttribute("thisTest", true);

        if (createTest != null) {
            req.setAttribute("heading", attribute);
            req.getSession().setAttribute("heading", attribute);
        }
    }

    public void createTest(HttpServletRequest req, String name) {
        Topic topic = (Topic) req.getSession().getAttribute("topic");
        Test test = Test.builder()
                .id(UUID.randomUUID())
                .name(name)
                .questions(new ArrayList<>())
                .topicId(topic.getId())
                .build();

        save(test);
    }

    public void setAttributeTest(HttpServletRequest req, String entityId) {
        UUID id = UUID.fromString(entityId);
        var test = findById(id);
        req.getSession().setAttribute("test", test);
    }

    public void checkTest(HttpServletRequest req, String entityId) {
        setAttributeTest(req, entityId);
        Test test = (Test) req.getSession().getAttribute("test");
        if (!test.getName().isEmpty()) {
            String colorTest = (String) req.getSession().getAttribute("colorTest");
            UUID testColorId = (UUID) req.getSession().getAttribute("testColorId");
            if (testColorId != null && testColorId.equals(test.getId()))
                if (colorTest.equals("red"))
                    colorTest = "";
                else
                    colorTest = "red";

            else {
                testColorId = test.getId();
                colorTest = "red";
            }
            req.getSession().setAttribute("index", 0);
            req.getSession().setAttribute("testColorId", testColorId);
            req.getSession().setAttribute("colorTest", colorTest);
        }
    }

    public void deleteTest(HttpServletRequest req) {

        String id = req.getParameter("removeId");
        if (id != null) {
            ServletContext context = req.getServletContext();
            AnswerService answerService = (AnswerService) context.getAttribute("answerService");
            QuestionService questionService = (QuestionService) context.getAttribute("questionService");

            UUID testId = UUID.fromString(id);
            entityDao.setFile("answerFile");
            answerService.removeByTestId(testId);
            entityDao.setFile("questionFile");
            questionService.removeByTestId(testId);
            entityDao.setFile("testFile");
            removeById(testId);
        }
    }
}
