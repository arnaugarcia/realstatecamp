(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('dashboard-home', {
            parent: 'dashboard',
            url: '/dashboard',
            ncyBreadcrumb: {
                label: 'Dashboard',
            },
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/home/dashboard.home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm',
                },
                'navbar@': {
                    templateUrl: 'app/dashboard/layouts/navbar/dashboard.navbar.html',
                    //controller: 'NavbarController',
                    //controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
