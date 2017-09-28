import './expenses.css'

export default class ExpensesCtrl {
    constructor(moment, ExpensesService) {
        this.moment = moment;
        this.expensesService = ExpensesService;

        this.expensesList = [];
        this.alerts = [];
        this.sortType = 'date';
        this.sortReverse = false;
        this.date = new Date();
        this.popupOpened = false;
        this.popupEditOpened = false;
        this.newExpenses = {};
        this.edit = {};
        this.editId = undefined;

        this.dateOptions = {
            formatYear: 'yyyy',
            maxDate: new Date(2020, 5, 22),
            startingDay: 1
        };

        this.refresh();
    }

    openDatePopup() {
        this.popupOpened = true;
    }

    openEditDatePopup() {
        this.popupEditOpened = true;
    }

    addAlert(type, message) {
        this.alerts.push({type: type, msg: message});
    }

    closeAlert() {
        this.alerts.splice(0, 1);
    }

    addExpenses() {
        let date = this.moment(this.newExpenses.date).format('YYYY-MM-DD');
        this.expensesService.create(date, this.newExpenses.description, this.newExpenses.value)
            .then((result) => {
                this.addAlert('success', 'Expenses add successfully');
                this.newExpenses = {};
                this.refresh();
            }, (error) => {
                this.addAlert('danger', 'Invalid data to add expenses');
            });
    }

    editExpenses(expensesId) {
        let expenses = this.expensesList.filter((expenses) => expenses.id.id === expensesId);
        if (expenses.length === 1) {
            this.editId = expensesId;
            this.edit.date = expenses[0].date;
            this.edit.description = expenses[0].description;
            this.edit.value = expenses[0].value;
        }

    }

    saveEditedExpenses() {
        this.expensesService.update(this.editId, this.edit.date, this.edit.description, this.edit.value)
            .then((result) => {
                this.addAlert('success', 'Expenses updated successfully');
                this.cancelEditedExpenses();
                this.refresh();
            }, (error) => {
                this.addAlert('danger', 'Invalid data to update expenses');
            });
    }

    cancelEditedExpenses() {
        this.editId = undefined;
        this.edit = {};
    }

    refresh() {
        this.expensesService.getAll()
            .then((result) => this.expensesList = result);
    }

    deleteExpenses(expensesId) {
        if (confirm('Do you want delete that expenses?')) {
            this.expensesService.delete(expensesId)
                .then(() => {
                    this.addAlert('success', 'Expenses deleted successfully');
                    this.refresh();
                }, (error) => {
                    this.addAlert('danger', 'Error deleting expenses');
                });
        }
    }
}

ExpensesCtrl.$inject = ['moment', 'ExpensesService'];
