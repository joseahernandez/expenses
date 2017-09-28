
import 'bootstrap/dist/css/bootstrap.css'
import '../style/app.css'
import angular from 'angular';
import angularMoment from 'angular-moment';
import uiBootstrap from 'angular-ui-bootstrap';
import expenses from './expenses/index';

let app = () => {
    return {
        template: require('./app.html'),
        controller: 'AppCtrl',
        controllerAs: 'app'
    }
};

class AppCtrl {
    constructor() {
        this.url = "Some url";
    }
}

const MODULE_NAME = 'app';

angular.module('app', [expenses, angularMoment, uiBootstrap])
    .directive('app', app)
    .controller('AppCtrl', AppCtrl);

export default MODULE_NAME;