# Expenses

Implement an AngularJs/Spring application to create, edit and list expenses. You must use Spring MVC to create the REST web application and AngularJs 1.6 for the Frontend part. Each expenses entry consists of a description (String), a value (Double) and a date (Date).

## Execution

Run the server side of the application with the following commands:

```
> gradle build
> java -jar build/libs/expenses-1.0-SNAPSHOT.jar
```

Then run the frontend application with:

```
> npm install
> npm run server
```

After that browse to the url:

```
http://localhost:3000
```

## Improvements

These are some points that I would like to do to improve the exercise, but due to time problems I couldn't make them: 

### Backend

* I would have liked to create a command bus that would be in charge of sending command between the controllers and the `ExpensesService`. The command bus could be fitted with decorators that handle transactions, write logs... without interfering in the services and decoupling these kinds of tasks from them.
* I decided put all the logic for each handler of the commands inside `ExpensesService` because the logic for each one is very small. Maybe, but with a more boilerplate work, it would have been better to extract every part of the logic to an external class in order to fulfill the single responsibility principle. Then the `ExpensesService` would have been the orchestrator to execute each class. But again, I have decided to leave it this way because it gets simpler.
* I put the error messages to return from the API to the client in the controller. A better way would be in a resource file to extract them from the logic and to make easier the translations.


### Frontend

* Forms validation (no empty values, date formats...)
* Instead of one angular component `expenses` it would have been better create one component that list the expenses and another to handle the creation and edition of them.
* Caching all the expenses for a time and update the list of expenses in the component would improve the performance, because in each operation it would not be necessary call the server to get all the items.
* Improvements in alert messages, confirm messages...




