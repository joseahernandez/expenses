package expenses.infrastructure.web.form;

import java.util.Date;

public class ExpensesForm {
    private String description;
    private Double value;
    private Date date;

    public ExpensesForm() {

    }

    public ExpensesForm(String description, Double value, Date date) {
        this.description = description;
        this.value = value;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
