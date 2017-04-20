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
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    $translatePartialLoader.addPart('request');
                    $translatePartialLoader.addPart('status');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
