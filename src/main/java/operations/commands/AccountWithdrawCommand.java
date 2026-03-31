package operations.commands;

import console.ConsoleInputHandler;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.AccountService;

@Component
public class AccountWithdrawCommand implements OperationCommand {

    private final AccountService accountService;
    private final ConsoleInputHandler consoleInputHandler;

    public AccountWithdrawCommand(AccountService accountService, ConsoleInputHandler consoleInputHandler) {
        this.accountService = accountService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id: ");
        Integer accountId = consoleInputHandler.readPositiveNum();
        System.out.println("Enter amount:");
        Integer withdrawAmount = consoleInputHandler.readPositiveNum();
        accountService.withdraw(accountId, withdrawAmount);
        System.out.println("Withdrawn " + withdrawAmount + " from account " + accountId + "\n" +
                "New balance: " + accountService.getAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("No such account is found")).getMoneyAmount());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
