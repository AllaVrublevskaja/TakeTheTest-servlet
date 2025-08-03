package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Answer;

import java.util.List;
import java.util.UUID;

public abstract class BaseAnswerService {

    private final EntityDao entityDao;
    private final TypeReference<List<Answer>> typeRef;

    protected BaseAnswerService(EntityDao entityDao, TypeReference<List<Answer>> typeRef) {
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void save(Answer answer) {
        entityDao.save(answer);
    }

    public List<Answer> findAll(){

        return entityDao.findAll(typeRef);
    }

    public Answer findById(UUID id) {

        return entityDao.findAll(typeRef).stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(new Answer());
    }

    public List <Answer> findByTestId(UUID testId) {
        List <Answer> lists = entityDao.findAll(typeRef);

        return lists.stream()
                .filter(e -> e.getTestId().equals(testId))
                .toList();
    }

    public void removeById(UUID id) {
        List <Answer> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getId().equals(id)).toList();
        entityDao.saveAll(lists);
    }

    public void removeByQuestionId(UUID questionId) {
        List <Answer> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getQuestionId().equals(questionId)).toList();
        entityDao.saveAll(lists);
    }

    public void removeByTestId(UUID testId) {
        List <Answer> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getTestId().equals(testId)).toList();
        entityDao.saveAll(lists);
    }

    public void removeByTopicId(UUID topicId) {
        List <Answer> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getTopicId().equals(topicId)).toList();
        entityDao.saveAll(lists);
    }

    public void changeById(UUID id, String newName, boolean isCorrect) {
        List <Answer> lists = entityDao.findAll(typeRef);
        lists.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> {
                    e.setName(newName);
                    e.setCorrect(isCorrect);
                });
        entityDao.saveAll(lists);
    }
}
