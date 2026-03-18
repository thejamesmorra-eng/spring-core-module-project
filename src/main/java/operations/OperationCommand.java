package operations;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
