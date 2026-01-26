package service;

import model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountService {

    private static final AtomicLong ACCOUNT_ID_COUNTER = new AtomicLong(1);
    private final UserService userService;

    // Map<AccountId, UserId> accounts
    private Map<Long, Long> accounts = new HashMap<>();

    @Value("${account.default-amount}")
    private int initBalance;

    @Value("${account.transfer-commission}")
    private int transferCommission;

//    public AccountService(UserService userService) {
//        this.userService = userService;
//    }

    public Account createAccount(Long userId) {
        return new Account(userId, ACCOUNT_ID_COUNTER.getAndIncrement(), initBalance);
    }

    public String deposit(long accountId, int depositAmount) {
        if (depositAmount < 1) {
            return "Incorrect amount, deposit could be more than 0";
        }

        userService.getUserById(accounts.get(accountId)).getAccountList();

//        for (Map.Entry<User, List<Account>> entry : accounts.entrySet()) {
//            for (Account account : entry.getValue()) {
//                if (account.getId() == accountId) {
//                    account.setMoneyAmount(account.getMoneyAmount() + depositAmount);
//                    return "Amount " + depositAmount +" deposited to account ID: " + account.getId();
//                }
//            }
//        }
        return "No such account id is found";
    }

    public String withdraw(Long accountId, int withdrawAmount) {
        if (withdrawAmount < 0) {
            return "Amount to withdraw must be positive";
        }

        for (Map.Entry<User, List<Account>> entry : accounts.entrySet()) {
            for (Account account : entry.getValue()) {
                if (account.getId() == accountId) {
                    if (account.getMoneyAmount() < withdrawAmount) {
                        return "No such money to withdraw";
                    } else {
                        account.setMoneyAmount(account.getMoneyAmount() - withdrawAmount);
                        return "The withdrawal operation was successful. Your current balance " + account.getMoneyAmount();
                    }
                }
            }
        }
        return "No such account id is found";
    }

    public String transfer(Long accountIdFrom, Long accountIdTo, int transferAmount) {
        Account accountFrom = null;
        Account accountTo = null;

        for (Map.Entry<User, List<Account>> entry : accounts.entrySet()) {
            for (Account account : entry.getValue()) {
                if (account.getId() == accountIdFrom) {
                    accountFrom = account;
                } else if (account.getId() == accountIdTo) {
                    accountTo = account;
                }
                if (accountFrom == null || accountTo == null) {
                    return "No such account is found";
                }
                if (accountFrom.getUserId() == accountTo.getUserId()) {
                    if (accountFrom.getMoneyAmount() < transferAmount) {
                        return "No such money to transfer";
                    } else {
                        deposit(accountIdTo, transferAmount);
                        withdraw(accountIdFrom, transferAmount);
                        return "Amount " + transferAmount + " transferred from account ID " + accountIdFrom +" to account ID " + accountIdTo + ".";
                    }
                } else {
                    if (accountFrom.getMoneyAmount() < (transferAmount + transferCommission)) {
                        return "No such money to transfer with commission";
                    } else {
                        deposit(accountIdTo, transferAmount);
                        withdraw(accountIdFrom, transferAmount + transferCommission);
                        return "Amount " + transferAmount + " transferred from account ID " + accountIdFrom +" to account ID " + accountIdTo + "." +
                                "Commission is " + transferCommission;
                    }
                }
            }
        }
        return "No such account id is found";
    }

    public String closeAccount(long accountId) {
        long closedAccountId = 0;
        for (Map.Entry<User, List<Account>> entry: accounts.entrySet()) {
            if (entry.getValue().size() == 1) {
                return "You only have one account, and you can't close it";
            }
            for (Account account : entry.getValue()) {
                if (account.getId() == accountId) {
                    int currentBalanceOfClosedAccount = account.getMoneyAmount();
                    Account firstAccount = entry.getValue().getFirst();
                    firstAccount.setMoneyAmount(currentBalanceOfClosedAccount + firstAccount.getMoneyAmount());
                    closedAccountId = account.getId();
                    entry.getValue().remove(account);
                }
            }
        }
        return "Account with ID " + closedAccountId + "has been closed.";
    }
}
