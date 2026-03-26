package operations.commands;

import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements OperationCommand {

    @Override
    public void execute() {

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
