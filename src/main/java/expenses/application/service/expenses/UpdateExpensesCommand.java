package expenses.application.service.expenses;

import expenses.model.expenses.ExpensesId;
import java.util.Date;

public class UpdateExpensesCommand {
    public ExpensesId id;
    public String description;
    public Double value;
    public Date date;

    public UpdateExpensesCommand(ExpensesId id, String description, Double value, Date date) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateExpensesCommand command = (UpdateExpensesCommand) o;

        if (id != null ? !id.equals(command.id) : command.id != null) return false;
        if (description != null ? !description.equals(command.description) : command.description != null) return false;
        if (value != null ? !value.equals(command.value) : command.value != null) return false;
        return date != null ? date.equals(command.date) : command.date == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
