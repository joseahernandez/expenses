import angular from 'angular';

function expensesList() {
    return {
        template: require('./expenses.html'),
        controller: 'ExpensesCtrl',
        controllerAs: 'ctrl'
    }
}

export default angular.module('directives.expensesList', [])
    .directive('expensesList', expensesList)
    .name