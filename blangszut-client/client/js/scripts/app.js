define([
    'require',
    'angularAMD',
    'angular-route',
    'angular-ui-router',
    'angular-ui-bootstrap',
    'common/init',
    'main/states',
    'auth/states',
    'admin/states'
], function(require, angularAMD) {
    var app = angular.module('Blangszut', ['ngRoute', 'ui.router', 'ui.bootstrap']);

    app.config(['$urlRouterProvider', '$locationProvider', function($urlRouterProvider, $locationProvider) {
        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise('/404');
    }]);

    var mainStates = require('main/states');
    mainStates(app, angularAMD);

    var authStates = require('auth/states');
    authStates(app, angularAMD);

    var adminStates = require('admin/states');
    adminStates(app, angularAMD);

    var commonInit = require('common/init');
    commonInit(app);

    return angularAMD.bootstrap(app);
});
