/**
 * Created by Panjie SW on 29/09/14.
 */
define(function () {
    return function(app, angularAMD) {
        app.config(['$stateProvider', function($stateProvider) {
            $stateProvider
                .state('login', angularAMD.route({
                    url: '/login',
                    templateUrl: '/partials/auth/login.html'
                }));
        }]);
    };
});
