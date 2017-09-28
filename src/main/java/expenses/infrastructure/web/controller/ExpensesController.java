package expenses.infrastructure.web.controller;

import expenses.application.service.expenses.*;
import expenses.infrastructure.web.form.ExpensesForm;
import expenses.model.expenses.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin("*")
public class ExpensesController {

    private static Map<Class<? extends RuntimeException>, String> errorMessages = new HashMap<>();
    static {
        errorMessages.put(ExpensesNotFoundException.class, "Invalid ExpensesId");
        errorMessages.put(InvalidExpensesDateException.class, "Date parameter is invalid");
        errorMessages.put(InvalidExpensesDescriptionException.class, "Description parameter is invalid");
        errorMessages.put(InvalidExpensesValueException.class, "Value parameter is invalid");
    }

    private ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    @GetMapping("/expenses/{id}")
    public Expenses getOne(@PathVariable String id) {
        return service.handle(new FindExpensesByIdQuery(new ExpensesId(UUID.fromString(id))));
    }

    @GetMapping("/expenses")
    public List<Expenses> getAll() {
        return service.handle(new FindAllExpensesQuery());
    }

    @PostMapping("/expenses")
    public Expenses post(@RequestBody ExpensesForm expensesForm) {
        return service.handle(new CreateExpensesCommand(expensesForm.getDescription(),
            expensesForm.getValue(), expensesForm.getDate())
        );
    }

    @PutMapping("/expenses/{id}")
    public Expenses put(@PathVariable String id, @RequestBody ExpensesForm expensesForm) {
        return service.handle(new UpdateExpensesCommand(new ExpensesId(UUID.fromString(id)),
            expensesForm.getDescription(), expensesForm.getValue(), expensesForm.getDate())
        );
    }

    @DeleteMapping("/expenses/{id}")
    public void delete(@PathVariable String id) {
        service.handle(new DeleteExpensesCommand(new ExpensesId(UUID.fromString(id))));
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @ExceptionHandler({InvalidExpensesDateException.class, InvalidExpensesDescriptionException.class,
        InvalidExpensesValueException.class, ExpensesNotFoundException.class})
    void handleBadRequest(HttpServletResponse response, RuntimeException exception) throws IOException {
        if (!errorMessages.containsKey(exception.getClass())) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error");
            return;
        }

        response.sendError(HttpStatus.BAD_REQUEST.value(), errorMessages.get(exception.getClass()));
    }
}
