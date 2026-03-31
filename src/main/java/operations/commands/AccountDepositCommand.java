package operations.commands;

import console.ConsoleInputHandler;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.AccountService;

@Component
public class AccountDepositCommand implements OperationCommand {

    private final AccountService accountService;
    private final ConsoleInputHandler consoleInputHandler;

    public AccountDepositCommand(AccountService accountService, ConsoleInputHandler consoleInputHandler) {
        this.accountService = accountService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id: ");
        Integer accountId = consoleInputHandler.readPositiveNum();
        System.out.println("Enter amount:");
        Integer depositAmount = consoleInputHandler.readPositiveNum();
        accountService.deposit(accountId, depositAmount);
        System.out.println("Deposited " + depositAmount + " to account " + accountId + "\n" +
                "New balance: " + accountService.getAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("No such account is found")).getMoneyAmount());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
