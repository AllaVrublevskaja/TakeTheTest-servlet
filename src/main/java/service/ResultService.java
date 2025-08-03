package service;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Question;
import entity.Result;
import entity.SelectedAnswer;
import jakarta.servlet.http.HttpServletRequest;
import service.baseService.BaseResultService;

import java.util.List;
import java.util.UUID;

public class ResultService extends BaseResultService {
    private final EntityDao entityDao;
    private final TypeReference<List<Result>> typeRef;

    public ResultService(EntityDao entityDao, TypeReference<List<Result>> typeRef) {
        super(entityDao, typeRef);
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public List<Result> findByUserId(UUID userId) {
        entityDao.setFile("resultFile");
        List<Result> results = findALl();

        return results.stream().filter(e -> e.getUserId().equals(userId)).toList();
    }

    public UUID getParamResultId(HttpServletRequest req) {
        UUID resultId = null;
        if (req.getParameter("id") != null && !req.getParameter("id").isEmpty()) {
            resultId = UUID.fromString(req.getParameter("id"));
        } else {
            Result result = (Result) req.getSession().getAttribute("result");
            if (result != null)
                resultId = result.getId();
        }
        return resultId;
    }

    public void getResultInstance(HttpServletRequest req, QuestionService questionService) {
        Result result = (Result) req.getSession().getAttribute("result");
        List<SelectedAnswer> selectedAnswers = result.getSelectedAnswerLists();
        UUID testId = result.getTestId();
        entityDao.setFile("questionFile");
        List<Question> questions = questionService.findAll().stream()
                .filter(e -> e.getTestId().equals(testId))
                .toList();
        req.setAttribute("questions", questions);
        req.setAttribute("selectedAnswers", selectedAnswers);
    }
}
