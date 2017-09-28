package expenses.application.service.expenses;

public class ExpensesNotFoundException extends RuntimeException {
    public ExpensesNotFoundException(String message) {
        super(message);
    }
}
