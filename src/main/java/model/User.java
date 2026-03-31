package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private final Integer id;
    private final String login;
    private final List<Account> accountList;

    public User(Integer id, String login) {
        this.id = id;
        this.login = login;
        this.accountList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id) && Objects.equals(accountList, user.accountList) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountList, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
