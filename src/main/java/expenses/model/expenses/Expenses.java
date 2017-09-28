package expenses.model.expenses;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Expenses {
    @EmbeddedId
    private ExpensesId id;
    private String description;
    private Double value;
    private Date date;

    public Expenses() {

    }

    public Expenses(ExpensesId id, String description, Double value, Date date) {
        this.id = id;
        setDescription(description);
        setValue(value);
        setDate(date);
    }

    public ExpensesId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    private void setDescription(String description) {
        if (description == null || description.trim().equals("")) {
            throw new InvalidExpensesDescriptionException("Expenses description can't be null or empty");
        }

        this.description = description;
    }

    private void setValue(Double value) {
        if (value == null) {
            throw new InvalidExpensesValueException("Expenses value can't be null");
        }

        this.value = value;
    }

    private void setDate(Date date) {
        if (date == null) {
            throw new InvalidExpensesDateException("Expenses date can't be null");
        }

        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expenses expenses = (Expenses) o;

        if (id != null ? !id.equals(expenses.id) : expenses.id != null) return false;
        if (description != null ? !description.equals(expenses.description) : expenses.description != null)
            return false;
        if (value != null ? !value.equals(expenses.value) : expenses.value != null) return false;
        return date != null ? date.equals(expenses.date) : expenses.date == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expenses{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", value=" + value +
            ", date=" + date +
            '}';
    }
}
