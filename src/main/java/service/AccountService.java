package service;

import config.AccountProperties;
import model.Account;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AccountService {

    private final AtomicInteger ACCOUNT_ID_COUNTER = new AtomicInteger(1);
    private final Map<Integer, Account> accounts;
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accounts = new HashMap<>();
        this.accountProperties = accountProperties;
    }

    public Account createAccount(Integer userId) {
        Account account = new Account(ACCOUNT_ID_COUNTER.getAndIncrement(), userId, accountProperties.getInitBalance());
        accounts.put(account.getId(), account);
        return account;
    }

    public void deposit(Integer accountId, int depositAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Error: There is no such account");
        }
        if (depositAmount < 1) {
            throw new IllegalArgumentException("Error: The deposit amount must be more than 0");
        }
        int currAccBalance = account.getMoneyAmount();
        account.setMoneyAmount(currAccBalance + depositAmount);
    }

    public void withdraw(Integer accountId, int withdrawAmount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Error: There is no such account");
        }
        if (withdrawAmount < 1) {
            throw new IllegalArgumentException("Error: The withdraw amount must be more than 0");
        }
        int currAccBalance = account.getMoneyAmount();
        if (currAccBalance < withdrawAmount) {
            throw new IllegalArgumentException("Error: insufficient funds on account id=" + accountId +
                    ", moneyAmount=" + currAccBalance +
                    ", attempted withdraw=" + withdrawAmount);
        }
        account.setMoneyAmount(currAccBalance - withdrawAmount);
    }

    public void transfer(Integer sourceAccountId, Integer destAccountId, int transferAmount) {
        Account sourceAcc = accounts.get(sourceAccountId);
        Account destAcc = accounts.get(destAccountId);
        if (sourceAcc == null || destAcc == null) {
            throw new IllegalArgumentException("Error: Not found specified account"); // Change
        }
        if (transferAmount < 1) {
            throw new IllegalArgumentException("Error: Amount to transfer must be more 0");
        }

        boolean isSelfTransfer = sourceAcc.getUserId().equals(destAcc.getUserId());

        int sourceAccBalance = sourceAcc.getMoneyAmount();
        if (isSelfTransfer) {
            if (sourceAccBalance < transferAmount) {
                throw new RuntimeException("Error: insufficient funds on account id= " + sourceAccountId +
                        ", moneyAmount= " + sourceAccBalance +
                        ", attempted transfer= " + transferAmount);
            }
            int destAccBalance = destAcc.getMoneyAmount();
            destAcc.setMoneyAmount(destAccBalance + transferAmount);
            sourceAcc.setMoneyAmount(sourceAccBalance - transferAmount);
        } else {
            int commission = accountProperties.getTransferCommission();
            if (sourceAccBalance < transferAmount + commission) {
                throw new IllegalStateException("Error: insufficient funds on account id= " + sourceAccountId +
                        ", moneyAmount= " + sourceAccBalance +
                        ", attempted transfer= " + (transferAmount + commission));
            }
            int destAccBalance = destAcc.getMoneyAmount();
            destAcc.setMoneyAmount(destAccBalance + transferAmount);
            sourceAcc.setMoneyAmount(sourceAccBalance - (transferAmount + commission));
        }
    }

    public Account closeAccount(Integer accountId) {
        Account accountToClose = accounts.get(accountId);
        if (accountToClose == null) {
            throw new IllegalArgumentException("Error: No such account is found");
        }
        Integer userId = accountToClose.getUserId();
        List<Account> userAccountList = getUserAccountList(userId);
        if (userAccountList.size() == 1) {
            throw new IllegalStateException("Error: You have only one account, you cannot close it");
        }
        int accountToCloseBalance = accountToClose.getMoneyAmount();
        accounts.remove(accountId);
        if (accountToCloseBalance > 0) {
            Account firstUserAccount = getUserAccountList(userId).getFirst();
            deposit(firstUserAccount.getId(), accountToCloseBalance);
        }
        return accountToClose;
    }

    public Optional<Account> getAccountById(Integer accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    private List<Account> getUserAccountList(Integer userId) {
        return accounts.values().stream()
                .filter(account -> Objects.equals(account.getUserId(), userId))
                .toList();
    }
}
