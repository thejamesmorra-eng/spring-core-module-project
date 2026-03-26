package console;

import jakarta.annotation.PostConstruct;
import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Service;
import service.AccountService;
import service.UserService;

import java.util.*;

@Service
public class ConsoleListener {

    private final Map<ConsoleOperationType, OperationCommand> commands;
    private final ConsoleInputHandler consoleInputHandler;

    public ConsoleListener(ConsoleInputHandler consoleInputHandler, List<OperationCommand> operationCommandList) {
        this.consoleInputHandler = consoleInputHandler;
        this.commands = new HashMap<>();
        for (OperationCommand operationCommand : operationCommandList) {
            commands.put(operationCommand.getOperationType(), operationCommand);
        }
    }

    private void process() {
        consoleInputHandler.printAvailableOperations();
        consoleInputHandler.p
    }

    public void start() {
        System.out.println("MiniBank application is started\n");
        consoleInputHandler.printAvailableOperations();
    }
}
