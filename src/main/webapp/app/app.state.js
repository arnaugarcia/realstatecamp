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
                'footer@': {
                    templateUrl: 'app/layouts/footer/footer.html',
                    controller: 'FooterController',
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
            template: 'bootstrap3'
        });
    }
})();
