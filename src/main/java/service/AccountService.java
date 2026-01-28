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

    }

//    public String withdraw(Long accountId, int withdrawAmount) {
//        if (withdrawAmount < 0) {
//            return "Amount to withdraw must be positive";
//        }
//
//        for (Map.Entry<User, List<Account>> entry : accounts.entrySet()) {
//            for (Account account : entry.getValue()) {
//                if (account.getId() == accountId) {
//                    if (account.getMoneyAmount() < withdrawAmount) {
//                        return "No such money to withdraw";
//                    } else {
//                        account.setMoneyAmount(account.getMoneyAmount() - withdrawAmount);
//                        return "The withdrawal operation was successful. Your current balance " + account.getMoneyAmount();
//                    }
//                }
//            }
//        }
//        return "No such account id is found";
//    }

//    public String transfer(Long accountIdFrom, Long accountIdTo, int transferAmount) {
//        Account accountFrom = null;
//        Account accountTo = null;
//
//        for (Map.Entry<User, List<Account>> entry : accounts.entrySet()) {
//            for (Account account : entry.getValue()) {
//                if (account.getId() == accountIdFrom) {
//                    accountFrom = account;
//                } else if (account.getId() == accountIdTo) {
//                    accountTo = account;
//                }
//                if (accountFrom == null || accountTo == null) {
//                    return "No such account is found";
//                }
//                if (accountFrom.getUserId() == accountTo.getUserId()) {
//                    if (accountFrom.getMoneyAmount() < transferAmount) {
//                        return "No such money to transfer";
//                    } else {
//                        deposit(accountIdTo, transferAmount);
//                        withdraw(accountIdFrom, transferAmount);
//                        return "Amount " + transferAmount + " transferred from account ID " + accountIdFrom +" to account ID " + accountIdTo + ".";
//                    }
//                } else {
//                    if (accountFrom.getMoneyAmount() < (transferAmount + transferCommission)) {
//                        return "No such money to transfer with commission";
//                    } else {
//                        deposit(accountIdTo, transferAmount);
//                        withdraw(accountIdFrom, transferAmount + transferCommission);
//                        return "Amount " + transferAmount + " transferred from account ID " + accountIdFrom +" to account ID " + accountIdTo + "." +
//                                "Commission is " + transferCommission;
//                    }
//                }
//            }
//        }
//        return "No such account id is found";
//    }

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

            System.out.println(userAccountList.size());
            while (iterator.hasNext()) {
                Account account = iterator.next();
                if (account.getId() == accountId) {
                    accountToClose = account;
                    iterator.remove();
                }
            }
            System.out.println(userAccountList.size());

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
}
