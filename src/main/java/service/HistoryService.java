package service;

import dao.EntityDao;
import entity.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static servlet.TopicServlet.getParam;

@AllArgsConstructor
public class HistoryService {

    ResultService resultService;
    QuestionService questionService;
    TestService testService;
    TopicService topicService;
    UserService userService;
    AnswerService answerService;
    EntityDao entityDao;

    public void getHistoryInstance(HttpServletRequest req, UUID userId) {
        entityDao.setFile("resultFile");
        List<History> histories = new ArrayList<>();
        List<Result> results;
        if(userId != null)
            results = resultService.findByUserId(userId);
        else
            results = resultService.findALl();

        for (Result result : results) {
            userId = result.getUserId();
            String userName = userService.findNameById(userId);
            UUID testId = result.getTestId();
            entityDao.setFile("testFile");
            Test test = testService.findById(testId);
            String testName = test.getName();
            UUID topicId = test.getTopicId();
            entityDao.setFile("topicFile");
            Topic topic = topicService.findById(topicId);
            String topicName = topic.getName();
            entityDao.setFile("questionFile");
            List<Question> questions = questionService.findByTestId(testId);
            int countQuestions = questions.size();
            entityDao.setFile("answerFile");
            int countTrueAnswers = (int) answerService.findByTestId(testId)
                    .stream().filter(e -> e.isCorrect()).count();
            int countSelectedAnswers = result.getSelectedAnswerLists().size();

            histories.add(new History(userId, userName, testId, testName,
                    topicId, topicName, result.getDate(), countQuestions, countTrueAnswers,
                    countSelectedAnswers));
        }

        req.setAttribute("histories", histories);
    }

    public void delete(HttpServletRequest req) {
        String id = getParam(req, "removeId");
        if (id != null && !id.isEmpty()) {
            UUID userId = UUID.fromString(id);
            deleteHistoryByUserId(userId);
        }else
            deleteHistory();
    }

    public void deleteHistoryByUserId(UUID userId){
        entityDao.setFile("resultFile");
        List<Result> removed = resultService.findALl().stream()
                .filter(e -> !e.getUserId().equals(userId)).toList();
        entityDao.saveAll(removed);
    }

    public void deleteHistory() {
        entityDao.setFile("resultFile");
        List<Result> results = new ArrayList<>();
        entityDao.saveAll(results);
    }
}
