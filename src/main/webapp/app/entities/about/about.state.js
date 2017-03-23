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
                authorities: ['ROLE_USER'],
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
        .state('about-detail', {
            parent: 'entity',
            url: '/about/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assessoriaTorrellesApp.about.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/about/about-detail.html',
                    controller: 'AboutDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('about');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'About', function($stateParams, About) {
                    return About.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'about',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('about-detail.edit', {
            parent: 'about-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/about/about-dialog.html',
                    controller: 'AboutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['About', function(About) {
                            return About.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('about.new', {
            parent: 'about',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/about/about-dialog.html',
                    controller: 'AboutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('about', null, { reload: 'about' });
                }, function() {
                    $state.go('about');
                });
            }]
        })
        .state('about.edit', {
            parent: 'about',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/about/about-dialog.html',
                    controller: 'AboutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['About', function(About) {
                            return About.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('about', null, { reload: 'about' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('about.delete', {
            parent: 'about',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/about/about-delete-dialog.html',
                    controller: 'AboutDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['About', function(About) {
                            return About.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('about', null, { reload: 'about' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
