import angular from 'angular';
import expensesCtrl from './expenses.controller';
import expensesList from './expenses.directive';
import ExpensesService from '../services/expenses.service';

export default angular.module('app.expenses', [expensesList, ExpensesService])
    .controller('ExpensesCtrl', expensesCtrl)
    .name;