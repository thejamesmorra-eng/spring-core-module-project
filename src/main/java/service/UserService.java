package service;

import model.Account;
import model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private static final AtomicLong USER_ID_COUNTER = new AtomicLong(1);

    private final AccountService accountService;

    private Map<Long, User> users = new HashMap<>();

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String createUser(String login) {
        User user = new User(USER_ID_COUNTER.getAndIncrement(), login);
        user.getAccountList().add(accountService.createAccount(user.getId()));
        users.put(user.getId(), user);
        return user.toString();
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
