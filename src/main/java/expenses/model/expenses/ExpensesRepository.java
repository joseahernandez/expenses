package expenses.model.expenses;

import java.util.List;

public interface ExpensesRepository {

    Expenses findById(ExpensesId id);

    List<Expenses> findAll();

    Expenses save(Expenses expenses);

    void delete(ExpensesId id);

    ExpensesId nextIdentity();
}
