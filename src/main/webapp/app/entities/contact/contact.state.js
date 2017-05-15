(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contact', {
            parent: 'entity',
            url: '/contact',
            data: {
                pageTitle: 'assessoriaTorrellesApp.contact.home.title'
            },
            ncyBreadcrumb: {
                label: '{{ "assessoriaTorrellesApp.contact.home.title" | translate }}'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contact/contacts.html',
                    controller: 'ContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contact');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
