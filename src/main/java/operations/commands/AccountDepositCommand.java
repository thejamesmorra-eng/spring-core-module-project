package operations.commands;

import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositCommand implements OperationCommand {

    @Override
    public void execute() {

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
