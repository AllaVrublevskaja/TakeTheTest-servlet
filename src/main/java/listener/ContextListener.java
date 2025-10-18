package listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.EntityDao;
import entity.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.*;

import java.io.File;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {

    private static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = "D:\\JavaRushUniver\\TakeTheTest-servlet\\src\\main\\resources\\data\\";
        servletContext = sce.getServletContext();

        ObjectMapper objectMapper = MapperInstatce.getMapper();

        File userFile = new File(path + "users/user.json");
        File topicFile = new File(path + "tests/topic.json");
        File testFile = new File(path + "tests/test.json");
        File questionFile = new File(path + "tests/question.json");
        File answerFile = new File(path + "tests/answer.json");
        File resultFile = new File(path + "results/result.json");

        TypeReference<List<Topic>> typeRefTopic = new TypeReference<List<Topic>>() {};
        TypeReference<List<Test>> typeRefTest = new TypeReference<List<Test>>() {};
        TypeReference<List<Question>> typeRefQuestion = new TypeReference<List<Question>>() {};
        TypeReference<List<Answer>> typeRefAnswer = new TypeReference<List<Answer>>() {};
        TypeReference<List<Result>> typeRefResult = new TypeReference<List<Result>>() {};


        EntityDao entityDao = new EntityDao(objectMapper);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserService userService = new UserService(entityDao, passwordEncoder);
        TopicService topicService = new TopicService(entityDao, typeRefTopic);

        TestService testService = new TestService(entityDao, typeRefTest);
        ResultService resultService = new ResultService(entityDao, typeRefResult);
        QuestionService questionService = new QuestionService(entityDao, resultService, typeRefQuestion);
        AnswerService answerService = new AnswerService(entityDao, typeRefAnswer);
        HistoryService historyService = new HistoryService(resultService, questionService, testService,
                topicService, userService, answerService, entityDao);


        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("topicService", topicService);
        servletContext.setAttribute("testService", testService);
        servletContext.setAttribute("questionService", questionService);
        servletContext.setAttribute("answerService", answerService);
        servletContext.setAttribute("resultService", resultService);
        servletContext.setAttribute("historyService", historyService);
        servletContext.setAttribute("entityDao", entityDao);
        servletContext.setAttribute("userFile", userFile);
        servletContext.setAttribute("topicFile", topicFile);
        servletContext.setAttribute("testFile", testFile);
        servletContext.setAttribute("questionFile", questionFile);
        servletContext.setAttribute("answerFile", answerFile);
        servletContext.setAttribute("resultFile", resultFile);
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
