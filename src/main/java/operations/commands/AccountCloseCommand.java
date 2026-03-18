package operations.commands;

import console.ConsoleInputHandler;
import model.Account;
import model.User;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.AccountService;
import service.UserService;

@Component
public class AccountCloseCommand implements OperationCommand {

    private final AccountService accountService;
    private final UserService userService;
    private final ConsoleInputHandler consoleInputHandler;

    public AccountCloseCommand(AccountService accountService, UserService userService, ConsoleInputHandler consoleInputHandler) {
        this.accountService = accountService;
        this.userService = userService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id to close:");
        Integer accountId = consoleInputHandler.readPositiveNum();
        Account closedAccount = accountService.closeAccount(accountId);
        User user = userService.getUserById(closedAccount.getUserId()).orElseThrow(() -> new IllegalArgumentException("No such user is found"));
        user.getAccountList().remove(closedAccount);
        System.out.println("Account " + accountId + " closed");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
