(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('about', {
            parent: 'entity',
            url: '/about',
            data: {
                pageTitle: 'assessoriaTorrellesApp.about.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/about/abouts.html',
                    controller: 'AboutController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('about');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
    }

})();
