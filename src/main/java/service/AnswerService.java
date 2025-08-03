package service;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Answer;
import entity.Question;
import entity.Test;
import entity.Topic;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.baseService.BaseAnswerService;

import java.util.List;
import java.util.UUID;

public class  AnswerService extends BaseAnswerService {

    private final EntityDao entityDao;
    private final TypeReference<List<Answer>> typeRef;

    public AnswerService(EntityDao entityDao, TypeReference<List<Answer>> typeRef) {
        super(entityDao, typeRef);
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void changeAnswer(String name, String changeId, boolean isCorrect) {
        UUID id = UUID.fromString(changeId);
        entityDao.setFile("answerFile");
        changeById(id, name, isCorrect);
    }

    public void changeByAnswer(UUID id, boolean newAnswer) {
        entityDao.setFile("answerFile");
        List<Answer> lists = entityDao.findAll(typeRef);
        lists.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> e.setAnswer(newAnswer));
        entityDao.saveAll(lists);
    }

    public void changeAnswerByQuestionId(UUID id, boolean newAnswer) {
        entityDao.setFile("answerFile");
        List<Answer> lists = entityDao.findAll(typeRef);
        lists.stream()
                .filter(e -> e.getTestId().equals(id))
                .forEach(e -> e.setAnswer(newAnswer));
        entityDao.saveAll(lists);
    }

    public void changeAnswerByInstance(EntityDao entityDao) {
        entityDao.setFile("answerFile");
        List<Answer> lists = entityDao.findAll(typeRef);
        lists.forEach(e -> e.setAnswer(false));
        entityDao.saveAll(lists);
    }

    public void checkUncheckAnswer(HttpServletRequest req, String answerId, String color) {
        entityDao.setFile("answerFile");
        boolean newAnswer;
        UUID id = UUID.fromString(answerId);
        var answer = findById(id);
        if (!answer.getName().isEmpty()) {
            if (answer.isAnswer()) {
                newAnswer = false;
            } else {
                newAnswer = true;
            }
            req.getSession().setAttribute("color", color);
            changeByAnswer(id, newAnswer);
        }
    }

    public void addAnswer(HttpServletRequest req) {
        entityDao.setFile("answerFile");
        Question question = (Question) req.getSession().getAttribute("question");
        List<Answer> answers = findAll();

        entityDao.setFile("questionFile");
        ServletContext context = req.getServletContext();
        QuestionService questionService = (QuestionService) context.getAttribute("questionService");

        questionService.changeByAnswers(question.getId(), answers);
        entityDao.setFile("answerFile");
    }

    public void createAnswer(HttpServletRequest req, String name, boolean isCorrect) {
        Topic topic = (Topic) req.getSession().getAttribute("topic");
        Test test = (Test) req.getSession().getAttribute("test");
        Question question = (Question) req.getSession().getAttribute("question");

        entityDao.setFile("answerFile");
        Answer answer = Answer.builder()
                .id(UUID.randomUUID())
                .name(name)
                .isCorrect(isCorrect)
                .topicId(topic.getId())
                .testId(test.getId())
                .questionId(question.getId())
                .build();

        save(answer);
    }

    public void deleteAnswer(HttpServletRequest req) {
        String answerId = req.getParameter("removeId");
        if (answerId != null) {
            entityDao.setFile("answerFile");
            removeById(UUID.fromString(answerId));
            addAnswer(req);
        }
    }
}
