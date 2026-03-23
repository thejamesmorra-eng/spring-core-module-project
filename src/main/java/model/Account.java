package model;

import java.util.Objects;

public class Account {
    // Record???
    private final Long id;
    private final Long userId;
    private int moneyAmount;

    public Account(Long id, Long userId, int initBalance) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = initBalance;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
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
