package expenses.model.expenses;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;
import java.util.UUID;

public class ExpensesTest {
    @Test
    public void createExpenses() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Supermarket";
        Double value = 42.50;
        Date date = new Date();

        Expenses expenses = new Expenses(id, description, value, date);

        Assert.assertEquals(id, expenses.getId());
        Assert.assertEquals(description, expenses.getDescription());
        Assert.assertEquals(value, expenses.getValue());
        Assert.assertEquals(date, expenses.getDate());
    }

    @Test(expected = InvalidExpensesDescriptionException.class)
    public void expensesWithNullDescription() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = null;
        Double value = 42.50;
        Date date = new Date();

        new Expenses(id, description, value, date);
    }

    @Test(expected = InvalidExpensesValueException.class)
    public void expensesWithNullValue() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Supermarket";
        Double value = null;
        Date date = new Date();

        new Expenses(id, description, value, date);
    }

    @Test(expected = InvalidExpensesDateException.class)
    public void expensesWithNullDate() {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Supermarket";
        Double value = 42.50;
        Date date = null;

        new Expenses(id, description, value, date);
    }
}
