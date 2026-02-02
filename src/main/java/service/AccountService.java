package service;

import model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public Long createAccount(Long userId) {
        Account account = new Account(userId, ACCOUNT_ID_COUNTER.getAndIncrement(), initBalance);
        userService.getUserById(userId).getAccountList().add(account);
        accounts.put(account.getId(), userId);
        return account.getId();
    }

    public String deposit(long accountId, int depositAmount) {
        if (depositAmount < 1) {
            return "Incorrect amount, deposit could be more than 0";
        }

        Long userId = accounts.get(accountId);
        if (userId == null) {
            return "No such account is found";
        }

        List<Account> userAccountList = userService.getUserById(userId).getAccountList();
        for (Account account : userAccountList) {
            if (account.getId() == accountId) {
                account.setMoneyAmount(account.getMoneyAmount() + depositAmount);
            }
        }
        return "Amount " + depositAmount +  " deposited to account ID: " + accountId;
    }

    public String withdraw(long accountId, int withdrawAmount) {
        if (withdrawAmount < 1) {
            return "Incorrect amount, withdraw could be more than 0";
        }

        Long userId = accounts.get(accountId);
        if (userId == null) {
            return "No such account is found";
        }

        List<Account> userAccountList = userService.getUserById(userId).getAccountList();
        for (Account account : userAccountList) {
            if (account.getId() == accountId) {
                if (account.getMoneyAmount() < withdrawAmount) {
                    return "Not enough money for withdrawal. Available amount is " + account.getMoneyAmount();
                } else {
                    account.setMoneyAmount(account.getMoneyAmount() - withdrawAmount);
                }
            }
        }
        return "Amount " + withdrawAmount +  " withdrawn from account ID: " + accountId;
    }

    public String transfer(long accountIdSource, long accountIdDestination, int transferAmount) {
        if (accountIdSource == accountIdDestination) {
            return "Source account id and destination is the same";
        }

        Long userIdSource = accounts.get(accountIdSource);
        Long userIdDestination = accounts.get(accountIdDestination);

        if (userIdSource == null || userIdDestination == null) {
            return "Invalid sender or recipient ID";
        }

        Account sourceAccount = getUserAccount(accountIdSource);
        Account destAccount = getUserAccount(accountIdDestination);

        if (sourceAccount == null || destAccount == null) {
            return "Check account ID, some of them is wrong";
        }

        int srcAccMoneyAmount = sourceAccount.getMoneyAmount();
        int destAccMoneyAmount = destAccount.getMoneyAmount();

        if (Objects.equals(accounts.get(accountIdSource), accounts.get(accountIdDestination))) {
            if (srcAccMoneyAmount < transferAmount) {
                return "Not enough money";
            }
            sourceAccount.setMoneyAmount(srcAccMoneyAmount - transferAmount);
            destAccount.setMoneyAmount(destAccMoneyAmount + transferAmount);
            return "The transfer was successful";
        } else {
            if (srcAccMoneyAmount < (transferAmount + transferCommission)) {
                return "Not enough money";
            }
            sourceAccount.setMoneyAmount(srcAccMoneyAmount - (transferAmount + transferCommission));
            destAccount.setMoneyAmount(destAccMoneyAmount + transferAmount);
            return "The transfer was successful";
        }
    }

    public String closeAccount(Long accountId) {
        Long userId = accounts.get(accountId);

        if (userId == null) {
            return "No such accountId is found";
        }

        int sizeOfUserAccountList = userService.getUserById(userId).getAccountList().size();

        if (sizeOfUserAccountList < 2) {
            return "You only have one account, and it cannot be deleted";
        } else {
            List<Account> userAccountList = userService.getUserById(userId).getAccountList();
            Iterator<Account> iterator = userAccountList.iterator();
            Account accountToClose = null;

            while (iterator.hasNext()) {
                Account account = iterator.next();
                if (account.getId() == accountId) {
                    accountToClose = account;
                    iterator.remove();
                }
            }

            userAccountList.sort(new Comparator<Account>() {
                @Override
                public int compare(Account o1, Account o2) {
                    return (int) (o1.getId() - o2.getId());
                }
            });

            Account firstAccount = userAccountList.getFirst();
            firstAccount.setMoneyAmount(firstAccount.getMoneyAmount() + accountToClose.getMoneyAmount());
        }
        return "Account with ID " + accountId + " has been closed.";
    }

    private Account getUserAccount(Long accountId) {
        Long userId = accounts.get(accountId);
        List<Account> userAccountList = userService.getUserById(userId).getAccountList();
        for (Account account : userAccountList) {
            if (account.getId() == accountId) {
                return account;
            }
        }
        return null;
    }
}
