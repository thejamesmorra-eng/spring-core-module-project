package service;

import model.Account;
import model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private static final AtomicLong USER_ID_COUNTER = new AtomicLong(1);
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> logins = new HashSet<>();

    public User createUser(String login) {
        if (isUserExist(login)) {
            throw new RuntimeException("Login already exists");
        }
        User user = new User(USER_ID_COUNTER.getAndIncrement(), login);
        users.put(user.getId(), user);
        logins.add(login);
        return user;
    }

    // Optional<User>???
    public User getUserById(Long id) {
        return users.get(id);
    }

    public void showAllUsers() {
        for (Map.Entry<Long, User> entry: users.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public boolean isUserExist(String login) {
        return logins.contains(login);
    }

    public void addAccountToUser(Long userId, Account account) {
        users.get(userId).getAccountList().add(account);
    }
}
