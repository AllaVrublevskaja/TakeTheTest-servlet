package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

import static listener.ContextListener.getServletContext;

public class EntityDao {
    private final ObjectMapper objectMapper;
    private File file;

    public EntityDao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setFile(String param) {
        this.file = (File) getServletContext().getAttribute(param);
    }

    @SneakyThrows
    public <T> void save(T t) {
        List<T> list = findAll(new TypeReference<List<T>>() {});
        list.add(t);
        objectMapper.writeValue(file, list);
    }

    @SneakyThrows
    public <T> List<T> findAll(TypeReference<List<T>> typeRef) {
        List<T> lists = objectMapper.readValue(file, typeRef);
        return lists;
    }

    @SneakyThrows
    public<T> void saveAll(List<T> list) {
        objectMapper.writeValue(file, list);
    }
}
