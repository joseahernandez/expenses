package expenses.application.service.expenses;

import expenses.model.expenses.Expenses;
import expenses.model.expenses.ExpensesId;
import expenses.model.expenses.ExpensesRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class ExpensesService {

    private ExpensesRepository repository;

    public ExpensesService(ExpensesRepository repository) {
        this.repository = repository;
    }

    public Expenses handle(CreateExpensesCommand command) {
        ExpensesId id = repository.nextIdentity();
        Expenses expenses = new Expenses(id, command.description, command.value, command.date);
        repository.save(expenses);

        return expenses;
    }

    public Expenses handle(UpdateExpensesCommand command) {
        Expenses currentExpenses = handle(new FindExpensesByIdQuery(command.id));
        Expenses expenses = new Expenses(currentExpenses.getId(), command.description, command.value, command.date);
        repository.save(expenses);

        return expenses;
    }

    public void handle(DeleteExpensesCommand command) {
        try {
            repository.delete(command.id);
        } catch (EmptyResultDataAccessException e) { }
    }

    public Expenses handle(FindExpensesByIdQuery query) {
        Expenses expenses = repository.findById(query.id);
        if (expenses == null) {
            throw new ExpensesNotFoundException("Expenses with id " + query.id + " not found");
        }

        return expenses;
    }

    public List<Expenses> handle(FindAllExpensesQuery query) {
        return repository.findAll();
    }
}
