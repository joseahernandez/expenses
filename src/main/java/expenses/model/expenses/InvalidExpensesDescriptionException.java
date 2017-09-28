package expenses.model.expenses;

public class InvalidExpensesDescriptionException extends RuntimeException {
    public InvalidExpensesDescriptionException(String message) {
        super(message);
    }
}
