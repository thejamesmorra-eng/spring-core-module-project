package model;

import java.util.Objects;

public class Account {

    private final Integer id;
    private final Integer userId;
    private int moneyAmount;

    public Account(Integer id, Integer userId, int initBalance) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = initBalance;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return id.equals(account.id) && userId.equals(account.userId) && moneyAmount == account.moneyAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moneyAmount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
