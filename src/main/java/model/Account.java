package model;

import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

public class Account {

    private final long id;
    private final long userId;
    private int moneyAmount;

    public Account(long userId, long id, int initBalance) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = initBalance;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
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
        return id == account.id && userId == account.userId && moneyAmount == account.moneyAmount;
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
