import angular from 'angular';

class ExpensesService {
    constructor($http) {
        this.http = $http;
        this.endpoint = 'http://localhost:8080/expenses';
    }

    getAll() {
        return this.http.get(this.endpoint)
            .then((result) => result.data);
    }

    create(date, description, value) {
        return this.http.post(this.endpoint, {date: date, description: description, value: value});
    }

    update(expensesId, date, description, value) {
        return this.http.put(this.endpoint + '/' + expensesId,
            {date: date, description: description, value: value});
    }

    delete(expensesId) {
        return this.http.delete(this.endpoint + '/' + expensesId);
    }
}

ExpensesService.$inject = ['$http'];

export default angular.module('services.expenses', [])
    .service('ExpensesService', ExpensesService)
    .name;
