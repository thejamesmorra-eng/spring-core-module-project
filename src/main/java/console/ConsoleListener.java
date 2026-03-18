package console;

import org.springframework.stereotype.Service;
import service.AccountService;
import service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class ConsoleListener {

    private final UserService userService;
    private final AccountService accountService;
    private final Scanner scanner;

    public ConsoleListener(UserService userService, AccountService accountService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void executor() {
        showMenu();
        String operation = scanner.nextLine();

        while (!operation.equalsIgnoreCase("EXIT")) {
            switch (operation.toUpperCase()) {
                case "USER_CREATE" -> {
                    System.out.println("Enter login for new user:");
                    String login = scanner.nextLine();

                    while (login == null || login.isEmpty()) {
                        System.out.println("Invalid login, please repeat the input");
                        login = scanner.nextLine();
                    }

                    if (!userService.isUserExist(login)) {
                        long userId = userService.createUser(login);
                        accountService.createAccount(userId);
                        System.out.println("User created: " + userService.getUserById(userId));
                    } else {
                        System.out.println("Login already exists");
                    }
                }

                case "SHOW_ALL_USERS" -> {
                    System.out.println("List of all users:");
                    userService.showAllUsers();
                }

                case "ACCOUNT_CREATE" -> {
                    System.out.println("Enter the user id for which to create an account:");
                    try {
                        long userId = scanner.nextInt();
                        long accountId = accountService.createAccount(userId);
                        System.out.println("New account created with ID: " + accountId + " for user: " + userService.getUserById(userId).getLogin());
                    } catch (InputMismatchException e) {
                        System.out.println("Id is incorrect");
                    }
                }

                case "ACCOUNT_CLOSE" -> {
                    System.out.println("Enter account ID to close:");
                    try {
                        long accountId = scanner.nextInt();
                        System.out.println(accountService.closeAccount(accountId));
                    } catch (InputMismatchException e) {
                        System.out.println("Id is incorrect");
                    }
                }

                case "ACCOUNT_DEPOSIT" -> {
                    System.out.println("Enter account ID:");
                    try {
                        long id = scanner.nextInt();
                        System.out.println("Enter amount to deposit:");
                        int amount = scanner.nextInt();
                        System.out.println(accountService.deposit(id, amount));
                    } catch (InputMismatchException e) {
                        System.out.println("Id or amount is incorrect");
                    }
                }

                case "ACCOUNT_TRANSFER" -> {
                    try {
                        System.out.println("Enter source account ID:");
                        long srcAccId = scanner.nextInt();
                        System.out.println("Enter destination account ID:");
                        long destAcctId = scanner.nextInt();
                        System.out.println("Enter transfer amount:");
                        int transferAmount = scanner.nextInt();
                        System.out.println(accountService.transfer(srcAccId, destAcctId, transferAmount));
                    } catch (InputMismatchException e) {
                        System.out.println("Id(s) is incorrect");
                    }
                }

                case "ACCOUNT_WITHDRAW" -> {
                    System.out.println("Enter account ID:");
                    try {
                        long id = scanner.nextInt();
                        System.out.println("Enter amount to withdraw:");
                        int amount = scanner.nextInt();
                        System.out.println(accountService.withdraw(id, amount));
                    } catch (InputMismatchException e) {
                        System.out.println("Id or amount is incorrect");
                    }
                }

                default -> {
                    System.out.println("Unknown operation, repeat the input");
                }
            }
            showMenu();
            operation = scanner.nextLine();
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\nPlease enter one of operation type:\n" +
                "-ACCOUNT_CREATE\n" +
                "-SHOW_ALL_USERS\n" +
                "-ACCOUNT_CLOSE\n" +
                "-ACCOUNT_WITHDRAW\n" +
                "-ACCOUNT_DEPOSIT\n" +
                "-ACCOUNT_TRANSFER\n" +
                "-USER_CREATE\n" +
                "-EXIT\n");
    }
}
