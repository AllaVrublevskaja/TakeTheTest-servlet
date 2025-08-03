package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.Result;

import java.util.List;
import java.util.UUID;

public abstract class BaseResultService {

    private final EntityDao entityDao;
    private final TypeReference<List<Result>> typeRef;

    protected BaseResultService(EntityDao entityDao, TypeReference<List<Result>> typeRef) {
        this.entityDao = entityDao;
        this.typeRef = typeRef;
    }

    public void save(Result result) {
        entityDao.save(result);
    }

    public List<Result> findALl() {
        return entityDao.findAll(typeRef);
    }

    public Result findById(UUID id) {
        List<Result> results = entityDao.findAll(typeRef);
        Result result = new Result();
        for (Result r : results)
            if (r.getId().equals(id)) {
                result = r;
                break;
            }

        return result;
    }

    public void removeById(UUID id) {
        List<Result> results = entityDao.findAll(typeRef);
        List<Result> removed = results.stream().filter(e -> !e.getId().equals(id)).toList();
        entityDao.saveAll(removed);

    }
}
