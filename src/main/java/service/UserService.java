package service;

import model.Account;
import model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {

    private final AtomicInteger USER_ID_COUNTER = new AtomicInteger(1);
    private final Map<Integer, User> users;
    private final Set<String> logins;
    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.users = new HashMap<>();
        this.logins = new HashSet<>();
        this.accountService = accountService;
    }

    public User createUser(String login) {
        if (isUserExist(login)) {
            throw new IllegalArgumentException("Error: Login already exists");
        }
        User user = new User(USER_ID_COUNTER.getAndIncrement(), login);
        Account account = accountService.createAccount(user.getId());
        user.getAccountList().add(account);
        users.put(user.getId(), user);
        logins.add(login);
        return user;
    }

    public Optional<User> getUserById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    public void showAllUsers() {
        for (Map.Entry<Integer, User> entry: users.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public boolean isUserExist(String login) {
        return logins.contains(login);
    }

    public void addAccountToUser(Integer userId, Account account) {
        try {
            users.get(userId).getAccountList().add(account);
        } catch (NullPointerException e) {
            throw new NullPointerException("Error: No such userId is found");
        }
    }
}
