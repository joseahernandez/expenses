package expenses.infrastructure.model.expenses;

import expenses.model.expenses.Expenses;
import expenses.model.expenses.ExpensesId;
import expenses.model.expenses.ExpensesRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;


public interface JPAExpensesRepository extends ExpensesRepository, CrudRepository<Expenses, ExpensesId> {
    @Override
    default ExpensesId nextIdentity() {
        return new ExpensesId(UUID.randomUUID());
    }
}
