(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboard', {
            parent: 'app',
            abstract: true,
            bustCache: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/dashboard/layouts/navbar/dashboard.navbar.html',
                    controller: 'DashboardNavbarController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/dashboard/layouts/footer/dashboard.footer.html',
                    controller: 'DashboardFooterController',
                    controllerAs: 'vm'
                }
            },
            data: {
                css: [
                    'content/css/AdminLTE.css',
                    {
                        name: 'dashboard',
                        href: 'content/css/AdminLTE.css'
                    },
                    'content/css/skin-green.min.css',
                    {
                        name: 'dashboard-skin',
                        href: 'content/css/skin-green.min.css'
                    }
                ]
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('dashboard');
                }]
            }
        });
    }
})();
