(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboard-entity', {
            abstract: true,
            parent: 'dashboard',
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
