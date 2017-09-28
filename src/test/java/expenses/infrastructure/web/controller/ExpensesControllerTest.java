package expenses.infrastructure.web.controller;

import expenses.application.service.expenses.*;
import expenses.model.expenses.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpensesController.class)
public class ExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpensesService service;

    @Test
    public void getAllExpenses() throws Exception {
        List<Expenses> listResult = new ArrayList<>();
        listResult.add(new Expenses(new ExpensesId(UUID.randomUUID()), "Supermarket", 34.23, new Date()));
        listResult.add(new Expenses(new ExpensesId(UUID.randomUUID()), "Netflix", 10.99, new Date()));


        when(service.handle(new FindAllExpensesQuery())).thenReturn(listResult);

        MvcResult result = mockMvc.perform(get("/expenses"))
            .andExpect(status().isOk())
            .andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void getAllExpensesWithoutElements() throws Exception {
        List<Expenses> listResult = new ArrayList<>();
        when(service.handle(new FindAllExpensesQuery())).thenReturn(listResult);

        mockMvc.perform(get("/expenses"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

    @Test
    public void getOneExpensesById() throws Exception {
        ExpensesId id = new ExpensesId(UUID.fromString("9ebe33cc-c203-44c5-85d9-50c5ca3953e4"));
        when(service.handle(new FindExpensesByIdQuery(id))).thenReturn(new Expenses(id, "Oil", 14.34, new Date(1506587535794L)));

        String expectedResult = "{\"id\":{\"id\":\"9ebe33cc-c203-44c5-85d9-50c5ca3953e4\"},\"description\":\"Oil\",\"value\":14.34,\"date\":1506587535794}";

        mockMvc.perform(get("/expenses/" + id.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult));
    }

    @Test
    public void getInvalidExpensesId() throws Exception {
        ExpensesId invalidId = new ExpensesId(UUID.randomUUID());
        when(service.handle(new FindExpensesByIdQuery(invalidId))).thenThrow(new ExpensesNotFoundException("Error"));

        mockMvc.perform(get("/expenses/" + invalidId.getId().toString()))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void createExpenses() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Netflix";
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        when(service.handle(new CreateExpensesCommand(description, value, date)))
            .thenReturn(new Expenses(id, description, value, date));
        String requestContent = "{\"description\": \"" + description + "\", \"value\": \"" + value +
            "\", \"date\": \"" + date.getTime() + "\"}";

        String expectedResult = "{\"id\":{\"id\":\"" + id.getId().toString() +
            "\"},\"description\":\"Netflix\",\"value\":10.99,\"date\":1506587535794}";

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult));
    }

    @Test
    public void createExpensesWithInvalidDescription() throws Exception {
        String description = null;
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        when(service.handle(new CreateExpensesCommand(description, value, date)))
            .thenThrow(new InvalidExpensesDescriptionException("Invalid description"));

        String requestContent = "{\"value\": \"" + value + "\", \"date\": \"" + date.getTime() + "\"}";

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(requestContent))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void createExpensesWithInvalidValue() throws Exception {
        String description = "Netflix";
        Double value = null;
        Date date = new Date(1506587535794L);

        when(service.handle(new CreateExpensesCommand(description, value, date)))
            .thenThrow(new InvalidExpensesValueException("Invalid value"));

        String requestContent = "{\"description\": \"" + description + "\", \"date\": \"" + date.getTime() + "\"}";

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(requestContent))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void createExpensesWithInvalidDate() throws Exception {
        String description = "Netflix";
        Double value = 10.99;
        Date date = null;

        when(service.handle(new CreateExpensesCommand(description, value, date)))
            .thenThrow(new InvalidExpensesDateException("Invalid date"));

        String requestContent = "{\"description\": \"" + description + "\", \"value\": \"" + value + "\"}";

        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON).content(requestContent))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateExpenses() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Netflix";
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        String descriptionUpdated = "HBO";
        Double valueUpdated = 11.99;
        Date dateUpdated = new Date(1506587535894L);

        when(service.handle(new FindExpensesByIdQuery(id))).thenReturn(new Expenses(id, description, value, date));
        when(service.handle(new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated)))
            .thenReturn(new Expenses(id, descriptionUpdated, valueUpdated, dateUpdated));

        String requestContent = "{\"description\": \"" + descriptionUpdated + "\", \"value\": \"" + valueUpdated +
            "\", \"date\": \"" + dateUpdated .getTime()+ "\"}";

        String expectedResult = "{\"id\":{\"id\":\"" + id.getId().toString()
            + "\"},\"description\":\"HBO\",\"value\":11.99,\"date\":1506587535894}";

        mockMvc.perform(
            put("/expenses/" + id.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult));
    }


    @Test
    public void updateExpensesWithInvalidDescription() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Netflix";
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        String descriptionUpdated = null;
        Double valueUpdated = 11.99;
        Date dateUpdated = new Date(1506587535894L);

        when(service.handle(new FindExpensesByIdQuery(id))).thenReturn(new Expenses(id, description, value, date));
        when(service.handle(new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated)))
            .thenThrow(new InvalidExpensesDescriptionException("Invalid description"));

        String requestContent = "{\"value\": \"" + valueUpdated +
            "\", \"date\": \"" + dateUpdated .getTime()+ "\"}";

        mockMvc.perform(
            put("/expenses/" + id.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
            )
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateExpensesWithInvalidValue() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Netflix";
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        String descriptionUpdated = "HBO";
        Double valueUpdated = null;
        Date dateUpdated = new Date(1506587535894L);

        when(service.handle(new FindExpensesByIdQuery(id))).thenReturn(new Expenses(id, description, value, date));
        when(service.handle(new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated)))
            .thenThrow(new InvalidExpensesValueException("Invalid value"));

        String requestContent = "{\"description\": \"" + descriptionUpdated + "\", \"date\": \"" +
            dateUpdated .getTime()+ "\"}";

        mockMvc.perform(
            put("/expenses/" + id.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateExpensesWithInvalidDate() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());
        String description = "Netflix";
        Double value = 10.99;
        Date date = new Date(1506587535794L);

        String descriptionUpdated = "HBO";
        Double valueUpdated = 11.98;
        Date dateUpdated = null;

        when(service.handle(new FindExpensesByIdQuery(id))).thenReturn(new Expenses(id, description, value, date));
        when(service.handle(new UpdateExpensesCommand(id, descriptionUpdated, valueUpdated, dateUpdated)))
            .thenThrow(new InvalidExpensesDateException("Invalid date"));

        String requestContent = "{\"description\": \"" + descriptionUpdated + "\", \"value\": \"" + valueUpdated + "\"}";

        mockMvc.perform(
            put("/expenses/" + id.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteExpenses() throws Exception {
        ExpensesId id = new ExpensesId(UUID.randomUUID());

        mockMvc.perform(delete("/expenses/" + id.getId()))
            .andExpect(status().isOk());
    }
}
