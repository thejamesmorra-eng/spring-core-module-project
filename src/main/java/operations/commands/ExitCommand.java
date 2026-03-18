package operations.commands;

import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements OperationCommand {

    @Override
    public void execute() {
        System.out.println("MiniBank stopped.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.EXIT;
    }
}
