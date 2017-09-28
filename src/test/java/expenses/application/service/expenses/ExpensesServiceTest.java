package expenses.application.service.expenses;

import expenses.model.expenses.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpensesServiceTest {

    private ExpensesRepository repository;
    private ExpensesService service;

    @Before
    public void setUp() throws Exception {
        repository = mock(ExpensesRepository.class);
        when(repository.nextIdentity()).thenReturn(new ExpensesId(UUID.randomUUID()));

        service = new ExpensesService(repository);
    }

    @Test
    public void createExpense() {
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();

        CreateExpensesCommand command = new CreateExpensesCommand(description, value, date);
        Expenses expenses = service.handle(command);

        Assert.assertNotNull(expenses.getId());
        Assert.assertEquals(description, expenses.getDescription());
        Assert.assertEquals(value, expenses.getValue());
        Assert.assertEquals(date, expenses.getDate());
    }

    @Test(expected = InvalidExpensesDescriptionException.class)
    public void createExpensesWithoutDescription() {
        String description = null;
        Double value = 34.29;
        Date date = new Date();

        CreateExpensesCommand command = new CreateExpensesCommand(description, value, date);
        service.handle(command);
    }

    @Test(expected = InvalidExpensesValueException.class)
    public void createExpensesWithoutValue() {
        String description = "Gas";
        Double value = null;
        Date date = new Date();

        CreateExpensesCommand command = new CreateExpensesCommand(description, value, date);
        service.handle(command);
    }

    @Test(expected = InvalidExpensesDateException.class)
    public void createExpensesWithoutDate() {
        String description = "Gas";
        Double value = 34.29;
        Date date = null;

        CreateExpensesCommand command = new CreateExpensesCommand(description, value, date);
        service.handle(command);
    }

    @Test
    public void updateExpense() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();
        when(repository.findById(id)).thenReturn(new Expenses(id, description, value, date));

        String descriptionUpdated = "Oil";
        Double valueUpdated = 43.13;
        Date dateUpdated = new Date();

        UpdateExpensesCommand commandUpdate = new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated);
        Expenses expensesUpdated = service.handle(commandUpdate);

        Assert.assertEquals(id, expensesUpdated.getId());
        Assert.assertEquals(descriptionUpdated, expensesUpdated.getDescription());
        Assert.assertEquals(valueUpdated, expensesUpdated.getValue());
        Assert.assertEquals(dateUpdated, expensesUpdated.getDate());
    }

    @Test(expected = InvalidExpensesDescriptionException.class)
    public void updateExpensesWithoutDescription() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();
        when(repository.findById(id)).thenReturn(new Expenses(id, description, value, date));

        String descriptionUpdated = null;
        Double valueUpdated = 43.13;
        Date dateUpdated = new Date();

        UpdateExpensesCommand commandUpdate = new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated);
        service.handle(commandUpdate);
    }

    @Test(expected = InvalidExpensesValueException.class)
    public void updateExpensesWithoutValue() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();
        when(repository.findById(id)).thenReturn(new Expenses(id, description, value, date));

        String descriptionUpdated = "Oil";
        Double valueUpdated = null;
        Date dateUpdated = new Date();


        UpdateExpensesCommand commandUpdate = new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated);
        service.handle(commandUpdate);
    }

    @Test(expected = InvalidExpensesDateException.class)
    public void updateExpensesWithoutDate() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();
        when(repository.findById(id)).thenReturn(new Expenses(id, description, value, date));

        String descriptionUpdated = "Oil";
        Double valueUpdated = 43.13;
        Date dateUpdated = null;

        UpdateExpensesCommand commandUpdate = new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated);
        service.handle(commandUpdate);
    }

    @Test(expected = ExpensesNotFoundException.class)
    public void updateExpensesWithInvalidId() {
        ExpensesId invalidId = new ExpensesId(UUID.randomUUID());
        String descriptionUpdated = "Oil";
        Double valueUpdated = 43.13;
        Date dateUpdated = new Date();

        when(repository.findById(invalidId))
            .thenThrow(new ExpensesNotFoundException("Expenses with id " + invalidId + " not found"));

        UpdateExpensesCommand commandUpdate = new UpdateExpensesCommand(invalidId, descriptionUpdated, valueUpdated, dateUpdated);
        service.handle(commandUpdate);
    }

    @Test
    public void deleteExpenses() {
        ExpensesId expensesId = new ExpensesId(UUID.randomUUID());
        DeleteExpensesCommand command = new DeleteExpensesCommand(expensesId);

        service.handle(command);
        Assert.assertTrue(true);
    }

    @Test
    public void findExpensesById() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Gas";
        Double value = 34.29;
        Date date = new Date();
        when(repository.findById(id)).thenReturn(new Expenses(id, description, value, date));

        FindExpensesByIdQuery query = new FindExpensesByIdQuery(id);
        Expenses expenses = service.handle(query);

        Assert.assertEquals(id, expenses.getId());
        Assert.assertEquals(description, expenses.getDescription());
        Assert.assertEquals(value, expenses.getValue());
        Assert.assertEquals(date, expenses.getDate());
    }

    public void findExpensesByInvalidId() {
        ExpensesId invalidId = new ExpensesId(UUID.randomUUID());
        when(repository.findById(invalidId))
            .thenThrow(new ExpensesNotFoundException("Expenses with id " + invalidId + " not found"));

        FindExpensesByIdQuery query = new FindExpensesByIdQuery(invalidId);
        service.handle(query);
    }

    @Test
    public void findAllExpenses() {
        ExpensesId id1 = new ExpensesId(UUID.randomUUID());
        String description1 = "Gas";
        Double value1 = 34.29;
        Date date1 = new Date();

        ExpensesId id2 = new ExpensesId(UUID.randomUUID());
        String description2 = "Supermarket";
        Double value2 = 12.57;
        Date date2 = new Date();

        List<Expenses> list = new ArrayList<>();
        list.add(new Expenses(id1, description1, value1, date1));
        list.add(new Expenses(id2, description2, value2, date2));
        when(repository.findAll()).thenReturn(list);

        FindAllExpensesQuery query = new FindAllExpensesQuery();
        List<Expenses> result = service.handle(query);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(id1, result.get(0).getId());
        Assert.assertEquals(id2, result.get(1).getId());
    }
}
