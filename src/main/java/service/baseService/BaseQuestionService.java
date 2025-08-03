package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Question;

import java.util.List;
import java.util.UUID;

public abstract class  BaseQuestionService {

    protected final EntityDao entityDao;
    private final TypeReference<List<Question>> typeRef;

    protected BaseQuestionService(EntityDao entityDao, TypeReference<List<Question>> typeRef) {
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void save(Question question) {
        entityDao.save(question);
    }

    public List<Question> findAll(){
        List<Question> lists = entityDao.findAll(typeRef);
        return lists;
    }

    public Question findById(UUID id) {

        return entityDao.findAll(typeRef).stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(new Question());
    }

    public void changeById(UUID id, String newName) {
        List <Question> lists = entityDao.findAll(typeRef);
        lists.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> e.setName(newName));
        entityDao.saveAll(lists);
    }

    public void removeById(UUID id) {
        List <Question> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getId().equals(id)).toList();
        entityDao.saveAll(lists);
    }

    public void removeByTopicId(UUID topicId) {
        List <Question> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getTopicId().equals(topicId)).toList();
        entityDao.saveAll(lists);
    }

    public void removeByTestId(UUID testId) {
        List <Question> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getTestId().equals(testId)).toList();
        entityDao.saveAll(lists);
    }
}
