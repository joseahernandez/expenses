package expenses.application.service.expenses;

public class FindAllExpensesQuery {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FindAllExpensesQuery query = (FindAllExpensesQuery) o;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
