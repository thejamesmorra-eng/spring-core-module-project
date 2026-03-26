package operations.commands;

import console.ConsoleInputHandler;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class UserCreateCommand implements OperationCommand {

    private final UserService userService;
    private final ConsoleInputHandler consoleInputHandler;

    public UserCreateCommand(UserService userService, ConsoleInputHandler consoleInputHandler) {
        this.userService = userService;
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public void execute() {
        String login = scanner
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return null;
    }
}
