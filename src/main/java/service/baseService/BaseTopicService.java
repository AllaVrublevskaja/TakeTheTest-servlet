package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Topic;

import java.util.List;
import java.util.UUID;

public abstract class BaseTopicService {
    private final EntityDao entityDao;
    private final TypeReference<List<Topic>> typeRef;

    protected BaseTopicService(EntityDao entityDao, TypeReference<List<Topic>> typeRef) {
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void save(Topic topic) {
        entityDao.save(topic);
    }

    public List<Topic> findALl() {
        return entityDao.findAll(typeRef);
    }

    public Topic findById(UUID id) {
        List<Topic> topics = entityDao.findAll(typeRef);
        Topic topic = new Topic();
        for(Topic t :topics)
            if(t.getId().equals(id)) {
                topic = t;
                break;
            }

        return topic;
    }

    public void removeById(UUID id) {
        List<Topic> topics = entityDao.findAll(typeRef);
        List<Topic> removed =  topics.stream().filter(e -> !e.getId().equals(id)).toList();
        entityDao.saveAll(removed);

    }

    public void changeById(UUID id, String newName) {
        List<Topic> topics = entityDao.findAll(typeRef);
        topics.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> e.setName(newName));
        entityDao.saveAll(topics);
    }
}
