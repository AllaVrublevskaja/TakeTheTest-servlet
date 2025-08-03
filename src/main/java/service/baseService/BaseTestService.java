package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseTestService {
    private final EntityDao entityDao;
    private final TypeReference<List<Test>> typeRef;

    protected BaseTestService(EntityDao entityDao, TypeReference<List<Test>> typeRef) {
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void save(Test test) {
        entityDao.save(test);
    }

    public List<Test> findAll() {
        List<Test> tests = entityDao.findAll(typeRef);
        return tests;
    }

    public Test findById(UUID testId) {
        List<Test> tests = entityDao.findAll(typeRef);
        Test test = new Test();
        for (Test t : tests)
            if (t.getId().equals(testId)) {
                test = t;
                break;
            }
        return test;
    }

    public void removeById(UUID testId) {
        List<Test> tests = entityDao.findAll(typeRef).stream().filter(e -> !e.getId().equals(testId)).toList();
        entityDao.saveAll(tests);
    }

    public void removeByTopicId(UUID topicId) {
        List<Test> lists = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getTopicId().equals(topicId)).toList();
        entityDao.saveAll(lists);
    }

    public void changeById(UUID id, String newName) {
        List<Test> tests = entityDao.findAll(typeRef);
        tests.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> e.setName(newName));
        entityDao.saveAll(tests);
    }

    public void saveQuestionList(Test test) {
        UUID id = test.getId();
        List<Test> tests = entityDao.findAll(typeRef).stream()
                .filter(e -> !e.getId().equals(id))
                .collect(Collectors.toList());
        tests.add(test);
        entityDao.saveAll(tests);
    }
}
