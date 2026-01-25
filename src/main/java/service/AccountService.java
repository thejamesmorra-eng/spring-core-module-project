package service;

import model.Account;
import model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountService {

    private static final AtomicLong ACCOUNT_ID_COUNTER = new AtomicLong(1);

    private Map<User, List<Account>> accounts = new HashMap<>();

    @Value("${account.default-amount}")
    private int initBalance;

    public Account createAccount(Long userId) {
        return new Account(userId, ACCOUNT_ID_COUNTER.getAndIncrement(), initBalance);
    }

    public void deposit(Long accountId, int depositAmount) {

    }

    public void withdraw(Long accountId, int withdrawAmount) {

    }

    public void transfer(Long accountIdFrom, Long accountIdTo, int transferAmount) {

    }

    public void closeAccount(Long accountId) {

    }
}
