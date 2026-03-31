package operations.commands;

import console.ConsoleInputHandler;
import model.User;
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
        System.out.println("Enter your login: ");
        String login = consoleInputHandler.readString();
        User user = userService.createUser(login);
        System.out.println("User created: " + user);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
