package expenses.model.expenses;

import java.io.Serializable;
import java.util.UUID;

public class ExpensesId implements Serializable {
    private UUID id;

    public ExpensesId(UUID id) {
        this.id = id;
    }

    public ExpensesId() {

    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpensesId that = (ExpensesId) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExpensesId{" +
            "id=" + id +
            '}';
    }
}
