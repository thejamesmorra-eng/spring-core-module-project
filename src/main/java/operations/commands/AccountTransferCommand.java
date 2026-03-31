package operations.commands;

import console.ConsoleInputHandler;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.AccountService;

import java.util.Objects;

@Component
public class AccountTransferCommand implements OperationCommand {

    private final AccountService accountService;
    private final ConsoleInputHandler consoleInputHandler;

    public AccountTransferCommand(AccountService accountService, ConsoleInputHandler consoleInputHandler) {
        this.accountService = accountService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() {
        System.out.println("Enter source account id: ");
        Integer sourceAccountId = consoleInputHandler.readPositiveNum();
        System.out.println("Enter target account id:");
        Integer destAccountId = consoleInputHandler.readPositiveNum();
        if (Objects.equals(sourceAccountId, destAccountId)) {
            throw new IllegalArgumentException("Error: Source and target account id must be different");
        }
        System.out.println("Enter amount to transfer:");
        Integer transferAmount = consoleInputHandler.readPositiveNum();
        accountService.transfer(sourceAccountId, destAccountId, transferAmount);
        System.out.printf("Transfer completed from account %s to account %s.\n", sourceAccountId, destAccountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
