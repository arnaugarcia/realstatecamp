(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider','$breadcrumbProvider'];

    function stateConfig($stateProvider,$breadcrumbProvider) {
        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                },

            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                }]
            }
        });

        $stateProvider.state('app.dashboard', {
            abstract: true,
            views: {
                'navbar@dashboard': {
                    templateUrl: 'app/layouts/navbar/navbar-admin.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                }]
            }
        });
        $breadcrumbProvider.setOptions({
            prefixStateName: 'home',
            template: '<ol class="breadcrumb"><li><a href="#"><i class="fa fa-dashboard"></i> DashBoard</a></li><li ng-repeat="step in steps" class="active"><a href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a></li></ol>'
        });
    }
})();
