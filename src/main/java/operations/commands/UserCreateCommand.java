package operations.commands;

import operations.OperationCommand;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class UserCreateCommand implements OperationCommand {

    private final UserService userService;

    public UserCreateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        userService.createUser()
    }
}
