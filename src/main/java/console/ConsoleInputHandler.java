package console;

import operations.ConsoleOperationType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputHandler {

    public void printAvailableOperations() {
        System.out.println("Please enter one of operation type:");
        for (ConsoleOperationType type : ConsoleOperationType.values()) {
            System.out.println("-" + type.name());
        }
    }

    public boolean isValidInput(String input) throws IllegalArgumentException {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input operation cannot be blanked");
        }
        return true;
    }
}
