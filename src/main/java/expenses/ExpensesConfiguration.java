package expenses;

import expenses.application.service.expenses.ExpensesService;
import expenses.model.expenses.ExpensesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpensesConfiguration {

    @Bean
    public ExpensesService service(ExpensesRepository repository) {
        return new ExpensesService(repository);
    }
}
