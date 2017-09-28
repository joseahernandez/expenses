package expenses.model.expenses;

public class InvalidExpensesValueException extends RuntimeException {
    public InvalidExpensesValueException(String message) {
        super(message);
    }
}
