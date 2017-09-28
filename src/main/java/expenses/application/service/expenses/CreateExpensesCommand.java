package expenses.application.service.expenses;

import java.util.Date;

public class CreateExpensesCommand {
    public String description;
    public Double value;
    public Date date;

    public CreateExpensesCommand(String description, Double value, Date date) {
        this.description = description;
        this.value = value;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateExpensesCommand command = (CreateExpensesCommand) o;

        if (description != null ? !description.equals(command.description) : command.description != null) return false;
        if (value != null ? !value.equals(command.value) : command.value != null) return false;
        return date != null ? date.equals(command.date) : command.date == null;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
