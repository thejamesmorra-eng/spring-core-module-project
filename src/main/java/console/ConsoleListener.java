package console;

import operations.ConsoleOperationType;
import operations.OperationCommand;
import org.springframework.stereotype.Service;

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
        while (true) {
            consoleInputHandler.printAvailableOperations();
            try {
                ConsoleOperationType operation = consoleInputHandler.processOperation();
                commands.get(operation).execute();
                if (operation == ConsoleOperationType.EXIT) {
                    break;
                }
            } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void start() {
        System.out.println("MiniBank application is started");
        process();
    }
}
