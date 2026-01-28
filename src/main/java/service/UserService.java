package service;

import model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private static final AtomicLong USER_ID_COUNTER = new AtomicLong(1);

    private Map<Long, User> users = new HashMap<>();

    public long createUser(String login) {
        User user = new User(USER_ID_COUNTER.getAndIncrement(), login);
        users.put(user.getId(), user);
        return user.getId();
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public void showAllUsers() {
        for (Map.Entry<Long, User> entry: users.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
