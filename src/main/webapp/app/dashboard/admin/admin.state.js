(function () {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('admin', {
            parent: 'app',
            abstract: true,
            bustCache: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/dashboard/layouts/navbar/dashboard.navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                },
                'footer@': {
                    templateUrl: 'app/dashboard/layouts/footer/dashboard.footer.html',
                    controller: 'FooterController',
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
            }
        });
    }
})();
