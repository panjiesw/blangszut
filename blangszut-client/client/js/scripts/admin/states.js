/**
 * Created by Panjie SW on 9/29/2014.
 */
define(function () {
    return function(app, angularAMD) {
        app.config(['$stateProvider', function($stateProvider) {
            $stateProvider
                .state('admin', angularAMD.route({
                    url: '/admin',
                    abstract: true,
                    templateUrl: '/partials/admin/root.html'
                }))
                .state('admin.dashboard', angularAMD.route({
                    url: '',
                    templateUrl: '/partials/admin/dashboard/index.html'
                }));
        }]);
    };
});