package console;

import operations.ConsoleOperationType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInputHandler {

    private final Scanner scanner;

    public ConsoleInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printAvailableOperations() {
        System.out.println("\nPlease enter one of operation type:");
        for (ConsoleOperationType type : ConsoleOperationType.values()) {
            System.out.println("-" + type.name());
        }
        System.out.println();
    }

    public ConsoleOperationType processOperation() {
        String input = scanner.nextLine().toUpperCase();
        if (!input.isBlank()) {
            try {
                return getOperationType(input);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error: No such operation is found");
            }
        } else {
            throw new IllegalArgumentException("Error: Command must not be blank");
        }
    }

    public String readString() {
        String input = scanner.nextLine().trim();
        if (!input.isBlank()) {
            return input;
        } else {
            throw new IllegalArgumentException("Error: Input must not be blank");
        }
    }

    public Integer readPositiveNum() {
        String input = scanner.nextLine().trim();
        if (!input.isBlank()) {
            try {
                int num = Integer.parseInt(input);
                if (num < 0) {
                    throw new IllegalArgumentException("Error: number must be positive");
                }
                return num;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: Must be a number");
            }
        } else {
            throw new IllegalArgumentException("Error: Input must not be blank");
        }
    }

    private ConsoleOperationType getOperationType(String operation) throws IllegalArgumentException {
        return ConsoleOperationType.valueOf(operation);
    }
}
