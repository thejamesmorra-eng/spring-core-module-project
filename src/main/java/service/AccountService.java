package service;

import config.AccountProperties;
import model.Account;
import model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccountService {
    // Подумать над возвращаемыми значениями в методах
    private static final AtomicLong ACCOUNT_ID_COUNTER = new AtomicLong(1);
    private final Map<Long, Account> accounts = new HashMap<>();
    private final UserService userService;
    private final AccountProperties accountProperties;

    public AccountService(UserService userService, AccountProperties accountProperties) {
        this.userService = userService;
        this.accountProperties = accountProperties;
    }

    public Account createAccount(Long userId) {
        Account account = new Account(ACCOUNT_ID_COUNTER.getAndIncrement(), userId, accountProperties.getInitBalance());
        accounts.put(account.getId(), account);
        userService.addAccountToUser(userId, account);
        return account;
    }

    public String deposit(Long accountId, int depositAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("There is no such account");
        }
        if (depositAmount < 1) {
            throw new RuntimeException("The deposit amount must be more than 0");
        }
        int currAccBalance = account.getMoneyAmount();
        account.setMoneyAmount(currAccBalance + depositAmount);
        return "depositFunc"; // Change
    }

    public String withdraw(Long accountId, int withdrawAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new RuntimeException("There is no such account");
        }
        if (withdrawAmount < 1) {
            throw new RuntimeException("The withdraw amount must be more than 0");
        }

        int currAccBalance = account.getMoneyAmount();
        if (currAccBalance > withdrawAmount) {
            throw new RuntimeException("Error: insufficient funds on account id= " + accountId +
                    ", moneyAmount= " + currAccBalance +
                    ", attempted withdraw= " + withdrawAmount);
        }
        account.setMoneyAmount(currAccBalance - withdrawAmount);
        return "WitdrawFunc"; // Change
    }

    public String transfer(Long idSource, Long idDestination, int transferAmount) {
        Account sourceAcc = accounts.get(idSource);
        Account destAcc = accounts.get(idDestination);
        if (sourceAcc == null || destAcc == null) {
            throw new RuntimeException("Not found specified account"); // Change
        }
        if (transferAmount < 1) {
            throw new RuntimeException("Amount to transfer must be more 0");
        }

        boolean isSelfTransfer = sourceAcc.getUserId().equals(destAcc.getUserId());

        // self-transfer
        // Подумать как сделать проще - вынести общие части???
        int sourceAccBalance = sourceAcc.getMoneyAmount();
        if (isSelfTransfer) {
            if (sourceAccBalance < transferAmount) {
                throw new RuntimeException("Error: insufficient funds on account id= " + idSource +
                        ", moneyAmount= " + sourceAccBalance +
                        ", attempted transfer= " + transferAmount);
            }
            int destAccBalance = destAcc.getMoneyAmount();
            destAcc.setMoneyAmount(destAccBalance + transferAmount);
            sourceAcc.setMoneyAmount(sourceAccBalance - transferAmount);
        } else {
            // not self-transfer
            int commission = accountProperties.getTransferCommission();
            if (sourceAccBalance < transferAmount + commission) {
                throw new RuntimeException("Error: insufficient funds on account id= " + idSource +
                        ", moneyAmount= " + sourceAccBalance +
                        ", attempted transfer= " + transferAmount + commission);
            }
            int destAccBalance = destAcc.getMoneyAmount();
            destAcc.setMoneyAmount(destAccBalance + transferAmount);
            sourceAcc.setMoneyAmount(sourceAccBalance - (transferAmount + commission));
        }
        return "Transfer success"; // Change
    }

    public String closeAccount(Long accountId) {
        Account accountToClose = accounts.get(accountId);
        if (accountToClose == null) {
            throw new RuntimeException("No such account is found");
        }

        User user = userService.getUserById(accountToClose.getUserId());
        if (user.getAccountList().size() < 2) {
            throw new RuntimeException("Just one account, can not be closed");
        }
        int accountToCloseBalance = accountToClose.getMoneyAmount();
        user.getAccountList().remove(accountToClose);
        if (accountToCloseBalance > 0) {
            deposit(user.getAccountList().getFirst().getId(), accountToCloseBalance);
        }
        return "Account is closed"; // Change
    }
}
