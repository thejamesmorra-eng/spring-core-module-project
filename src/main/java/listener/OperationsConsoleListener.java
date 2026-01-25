package listener;

import org.springframework.stereotype.Service;
import service.AccountService;
import service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class OperationsConsoleListener {

    private final UserService userService;
    private final AccountService accountService;

    public OperationsConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void executor() {
        showMenu();

        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("User created: " + userService.createUser(login));
                }

                case "SHOW_ALL_USERS" -> {
                    System.out.println("List of all users:");
                    userService.showAllUsers();
                }

                case "ACCOUNT_CREATE" -> {
                    System.out.println("Enter the user id for which to create an account:");
                    try {
                        long id = scanner.nextInt();
                        userService.getUserById(id).getAccountList().add(accountService.createAccount(id));
                        System.out.println("New account created with ID: " + " for user: " + userService.getUserById(id).getLogin());
                    } catch (InputMismatchException e) {
                        System.out.println("Id is incorrect, please repeat the input");
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
