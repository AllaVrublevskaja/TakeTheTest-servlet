package service;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.baseService.BaseQuestionService;
import servlet.TopicServlet;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionService extends BaseQuestionService {

    private final ResultService resultService;
    private EntityDao entityDao;
    private final TypeReference<List<Question>> typeRef;
    public long testStartTime;

    public QuestionService(EntityDao entityDao, ResultService resultService, TypeReference<List<Question>> typeRef) {
        super(entityDao, typeRef);
        this.entityDao = entityDao;
        this.resultService = resultService;
        this.typeRef = typeRef;
    }

    public UUID getParamTestId(HttpServletRequest req) {
        UUID testId = null;
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            testId = UUID.fromString(req.getParameter("id"));
        } else {
            Test test = (Test) req.getSession().getAttribute("test");
            if (test != null) {
                testId = test.getId();
            }
        }
        return testId;
    }

    public void getQuestionInstance(HttpServletRequest req, UUID testId, String endpoint) {
        String todo = "Добавить вопрос";
        String heading = "Вопрос";
        String endpointQuit = "/secure/tests";
        String todoChange = "Изменить";
        String headingChange = "Изменить вопрос";

        entityDao.setFile("questionFile");

        TopicServlet.setRequestAttribute(req, todo, heading, endpoint, endpointQuit, todoChange, headingChange);

        int index;
        if (req.getSession().getAttribute("index") != null)
            index = (int) req.getSession().getAttribute("index");
        else
            index = 0;

        req.getSession().setAttribute("index", index);

        entityDao.setFile("questionFile");

        List<Question> questions = findAll().stream()
                .filter(e -> e.getTestId().equals(testId))
                .toList();
        int questionsSize = questions.size();

        List<Answer> answers;
        if (!questions.isEmpty()) {
            ServletContext context = req.getServletContext();
            AnswerService answerService = (AnswerService) context.getAttribute("answerService");
            getInstanceAnswer(questions, answerService);
            Question question = questions.get(index);
            answers = question.getAnswers();
            req.getSession().setAttribute("question", question);
            req.setAttribute("question", question);

            req.getSession().setAttribute("answers", answers);
            req.getSession().setAttribute("questions", questions);
            entityDao.setFile("questionFile");
        }

        String todoTitle = (String) req.getSession().getAttribute("todoTitle");
        String title = (String) req.getSession().getAttribute("title");
        String attribute = todoTitle + " " + title;
        Boolean createAnswer = (Boolean) req.getSession().getAttribute("createAnswer");
        Boolean createQuestion = (Boolean) req.getSession().getAttribute("createQuestion");

        req.getSession().setAttribute("questionsSize", questionsSize);
        req.setAttribute("todoTitle", todoTitle);

        if (createQuestion != null || createAnswer != null) {
            req.setAttribute("heading", attribute);
            req.getSession().setAttribute("heading", attribute);
        }
        //   init testTime
        if(req.getSession().getAttribute("testStartTime") == null &&
                req.getSession().getAttribute("menuAdmin") == null) {
            testStartTime = System.currentTimeMillis();
            req.getSession().setAttribute("testStartTime", testStartTime);
        }
    }

    private void getInstanceAnswer(List<Question> questions, AnswerService answerService) {
        List<Answer> answers;
        for (Question question : questions) {
            entityDao.setFile("answerFile");
            answers = answerService.findAll().stream()
                    .filter(e -> e.getQuestionId().equals(question.getId()))
                    .toList();
            question.setAnswers(answers);
            entityDao.setFile("questionFile");
            removeById(question.getId());
            save(question);
        }
    }

    public void createQuestion(HttpServletRequest req, String name, Topic topic,
                               Test test, User user) {
        entityDao.setFile("questionFile");
        Question question = Question.builder()
                .id(UUID.randomUUID())
                .name(name)
                .createdBy(user.getName())
                .answers(new ArrayList<>())
                .topicId(topic.getId())
                .testId(test.getId())
                .build();

        save(question);

        int questionsSize = (int) findAll().stream()
                .filter(e -> e.getTestId().equals(test.getId()))
                .count();
        int index = questionsSize - 1;
        req.getSession().setAttribute("index", index);
        req.getSession().setAttribute("questionsSize", questionsSize);
        req.getSession().setAttribute("question", question);
    }

    public void quitQuestion(HttpServletRequest req, Test test, TestService testService, UUID userId,
                             UUID questionId) {

        UUID testId = test.getId();
        ServletContext context = req.getServletContext();
        AnswerService answerService = (AnswerService) context.getAttribute("answerService");

        List<Question> questions = (List<Question>) req.getSession().getAttribute("questions");

        test.setQuestions(questions);
        entityDao.setFile("testFile");
        testService.saveQuestionList(test);

        resultTest(req, userId, testId, questions);

        answerService.changeAnswerByQuestionId(testId, false);

        Boolean createQuestion = (Boolean) req.getSession().getAttribute("createQuestion");
        if (createQuestion != null)
            req.getSession().setAttribute("index", 0);
    }

    public void changeByAnswers(UUID id, List<Answer> answers) {
        entityDao.setFile("questionFile");
        List<Question> lists = entityDao.findAll(typeRef);
        lists.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> e.setAnswers(answers));
        entityDao.saveAll(lists);
    }

    public void checkQuestion(HttpServletRequest req, Question question) {
        long countTrue = question.getAnswers().stream().filter(e -> e.isAnswer() && e.isCorrect()).count();
        long countChecked = question.getAnswers().stream().filter(e -> e.isAnswer()).count();
        String answerToReturn;
        if (countTrue > 0 && countTrue == countChecked)
            answerToReturn = "Правильный ответ!!! Поздравляю!";
        else
            answerToReturn = "Ошибка!!!";

        req.getSession().setAttribute("answerToReturn", answerToReturn);
    }

    public void deleteQuestion(HttpServletRequest req) {
        ServletContext context = req.getServletContext();
        int index = (int) req.getSession().getAttribute("index");
        String id = req.getParameter("removeId");
        if (id != null) {
            AnswerService answerService = (AnswerService) context.getAttribute("answerService");
            UUID questionId = UUID.fromString(id);
            entityDao.setFile("answerFile");
            answerService.removeByQuestionId(questionId);
            entityDao.setFile("questionFile");
            removeById(questionId);
            if (index > 0)
                index--;
            req.getSession().setAttribute("index", index);
        }
    }

    public void resultTest(HttpServletRequest req, UUID userId, UUID testId, List<Question> questions) {
        req.getSession().removeAttribute("result");
        List<SelectedAnswer> lists = new ArrayList<>();
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers().stream().filter(e -> e.isAnswer() && e.isCorrect()).toList();
            if (answers.size() > 0) {
                for (Answer answer : answers) {
                    lists.add(new SelectedAnswer(UUID.randomUUID(), answer.getQuestionId(),
                            answer.getId(), answer.getName(), answer.isCorrect()));
                }
            }
        }
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        Result result = Result.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .testId(testId)
                .date(date)
                .selectedAnswerLists(lists)
                .build();

        req.getSession().setAttribute("result", result);
        entityDao.setFile("resultFile");
        resultService.save(result);
    }

    public List<Question> findByTestId(UUID testId) {
        entityDao.setFile("questionFile");
        List<Question> lists = entityDao.findAll(typeRef);
        return lists.stream()
                .filter(e -> e.getTestId().equals(testId))
                .toList();
    }
}
