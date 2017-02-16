(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('photo', {
            parent: 'entity',
            url: '/photo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assessoriaTorrellesApp.photo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/photo/photos.html',
                    controller: 'PhotoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('photo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('photo-detail', {
            parent: 'entity',
            url: '/photo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assessoriaTorrellesApp.photo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/photo/photo-detail.html',
                    controller: 'PhotoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('photo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Photo', function($stateParams, Photo) {
                    return Photo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'photo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('photo-detail.edit', {
            parent: 'photo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/photo/photo-dialog.html',
                    controller: 'PhotoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Photo', function(Photo) {
                            return Photo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('photo.new', {
            parent: 'photo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/photo/photo-dialog.html',
                    controller: 'PhotoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                created: null,
                                image: null,
                                imageContentType: null,
                                description: null,
                                url: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('photo', null, { reload: 'photo' });
                }, function() {
                    $state.go('photo');
                });
            }]
        })
        .state('photo.edit', {
            parent: 'photo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/photo/photo-dialog.html',
                    controller: 'PhotoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Photo', function(Photo) {
                            return Photo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('photo', null, { reload: 'photo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('photo.delete', {
            parent: 'photo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/photo/photo-delete-dialog.html',
                    controller: 'PhotoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Photo', function(Photo) {
                            return Photo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('photo', null, { reload: 'photo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
