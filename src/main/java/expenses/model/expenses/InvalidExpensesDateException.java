package expenses.model.expenses;

public class InvalidExpensesDateException extends RuntimeException {
    public InvalidExpensesDateException(String message) {
        super(message);
    }
}
