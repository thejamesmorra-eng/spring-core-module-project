package operations.commands;

import console.ConsoleInputHandler;
import model.Account;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.AccountService;
import service.UserService;

@Component
public class AccountCreateCommand implements OperationCommand {

    private final AccountService accountService;
    private final UserService userService;
    private final ConsoleInputHandler consoleInputHandler;

    public AccountCreateCommand(AccountService accountService, UserService userService, ConsoleInputHandler consoleInputHandler) {
        this.accountService = accountService;
        this.userService = userService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() throws NullPointerException {
        System.out.println("Enter user id: ");
        Integer userId = consoleInputHandler.readPositiveNum();
        Account account = accountService.createAccount(userId);
        userService.addAccountToUser(userId, account);
        System.out.println("Account created: " + account);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
