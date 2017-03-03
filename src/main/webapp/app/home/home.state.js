(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            ncyBreadcrumb: {
                label: 'Home',
            },
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });

        $stateProvider.state('dashboard', {
            parent: 'app',
            url: '/dashboard',
            ncyBreadcrumb: {
                label: 'Dashboard',
            },
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/dashboard.html',
                    controller: 'HomeController',
                    controllerAs: 'vm',
                },
                'navbar@': {
                    templateUrl: 'app/dashboard/dashboard-navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                //TODO Translate
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
