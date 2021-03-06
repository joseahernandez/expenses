package expenses.application.service.expenses;

import expenses.model.expenses.ExpensesId;

public class DeleteExpensesCommand {
    public ExpensesId id;

    public DeleteExpensesCommand(ExpensesId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteExpensesCommand that = (DeleteExpensesCommand) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
