require.config({
    baseUrl: 'js/scripts',

    paths: {
        'angular': '../../lib/angular/angular',
        'angular-route': '../../lib/angular-route/angular-route',

        'angular-ui-router': '../../lib/angular-ui-router/release/angular-ui-router',
        'angular-ui-bootstrap': '../../lib/angular-bootstrap/ui-bootstrap-tpls',

        'angularAMD': '../../lib/angularAMD/angularAMD',
        'ngload': '../../lib/angularAMD/ngload'
    },

    shim: {
        'angularAMD': ['angular'],
        'angular-route': ['angular'],

        'angular-ui-router': ['angular'],
        'angular-ui-bootstrap': ['angular']
    },

    deps: ['app']
});