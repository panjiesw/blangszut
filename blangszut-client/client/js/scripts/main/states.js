/**
 * Created by Panjie SW on 9/29/2014.
 */
define(function () {
    return function(app, angularAMD) {
        app.config(['$stateProvider', function($stateProvider) {
            $stateProvider
                .state('root', angularAMD.route({
                    url: '',
                    abstract: true,
                    templateUrl: '/partials/main/root.html'
                }))
                .state('root.main', angularAMD.route({
                    url: '/',
                    templateUrl: '/partials/main/index/index.html'
                }));
        }]);
    };
});