package service.baseService;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.EntityDao;
import entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

public abstract class BaseUserService {
    private final EntityDao entityDao;
    private final TypeReference<List<User>> typeRef;
    private final BCryptPasswordEncoder passwordEncoder;

    protected BaseUserService(EntityDao entityDao, BCryptPasswordEncoder passwordEncoder) {
        this.entityDao = entityDao;
        this.typeRef = new TypeReference<List<User>>() {};
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        entityDao.save(user);
    }

    public List<User> findALl() {
        return entityDao.findAll(typeRef);
    }

    public User findById(UUID id) {
        List<User> users = entityDao.findAll(typeRef);
        User user = new User();
        for (User t : users)
            if (t.getId().equals(id)) {
                user = t;
                break;
            }

        return user;
    }

    public void removeById(UUID id) {
        List<User> users = entityDao.findAll(typeRef);
        List<User> removed = users.stream().filter(e -> !e.getId().equals(id)).toList();
        entityDao.saveAll(removed);

    }

    public void changeById(UUID id, String newName, String newPassword, Boolean admin) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        List<User> users = entityDao.findAll(typeRef);
        users.stream()
                .filter(e -> e.getId().equals(id))
                .forEach(e -> {
                    e.setName(newName);
                    e.setPassword(encodedPassword);
                    e.setAdmin(admin);
                });
        entityDao.saveAll(users);
    }

    public void changeByIdAndFields(UUID id, String newName, String newPassword, Boolean admin) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        List<User> users = entityDao.findAll(typeRef);
        boolean change = false;
        for (User user : users) {
            if (user.getName().equals(newName))
                if (user.getId().equals(id)) {
                    if (!user.getPassword().equals(encodedPassword))
                        user.setPassword(encodedPassword);

                    if (user.isAdmin() != admin)
                        user.setAdmin(admin);
                    change = true;
                    break;
                }
        }
        if (change)
            entityDao.saveAll(users);
    }
}

