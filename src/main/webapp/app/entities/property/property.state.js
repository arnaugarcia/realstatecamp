(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('property', {
            parent: 'entity',
            url: '/property?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'assessoriaTorrellesApp.property.home.title'
            },
            ncyBreadcrumb: {
                label: '{{ "assessoriaTorrellesApp.property.home.title" | translate }}'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/property/properties.html',
                    controller: 'PropertyController',
                    controllerAs: 'vm'
                },
                'header-search' : {
                    templateUrl : 'app/layouts/header/header.search.html',
                    controller: 'HeaderController',
                    controllerAs: 'hcs'
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
                    $translatePartialLoader.addPart('property');
                    $translatePartialLoader.addPart('buildingType');
                    $translatePartialLoader.addPart('serviceType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }).state('property.list', {
                parent: 'property',
                data: {
                    authorities: [],
                    pageTitle: 'assessoriaTorrellesApp.property.home.title'
                },
                ncyBreadcrumb: {
                    label: 'Property'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/property/properties-list.html',
                        controller: 'PropertyController',
                        controllerAs: 'vm'
                    },
                    'header-search' : {
                        templateUrl : 'app/layouts/header/header.search.html',
                        controller: 'HeaderController',
                        controllerAs: 'hcs'
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
                        $translatePartialLoader.addPart('property');
                        $translatePartialLoader.addPart('buildingType');
                        $translatePartialLoader.addPart('serviceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

        .state('property.detail', {
            parent: 'property',
            url: '/{id}',
            data: {
                authorities: [],
                pageTitle: 'assessoriaTorrellesApp.property.detail.title'
            },
            ncyBreadcrumb: {
                label: 'Info of property'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/property/property-detail.html',
                    controller: 'PropertyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('property');
                    $translatePartialLoader.addPart('buildingType');
                    $translatePartialLoader.addPart('serviceType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Property', function($stateParams, Property) {
                    return Property.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'property',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('property.edit', {
            parent: 'property',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/dashboard.property-new.html',
                    controller: 'PropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('property.new', {
            parent: 'property',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            ncyBreadcrumb: {
                label: 'New Property'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/dashboard.property-new.html',
                    controller: 'PropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                description: null,
                                buildingType: null,
                                serviceType: null,
                                ref: null,
                                visible: null,
                                sold: null,
                                terrace: null,
                                m2: null,
                                numberBedroom: null,
                                elevator: null,
                                furnished: null,
                                pool: null,
                                garage: null,
                                numberWc: null,
                                ac: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('property', null, { reload: 'property' });
                }, function() {
                    $state.go('property');
                });
            }]
        })
        .state('property.delete', {
            parent: 'property',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/property/property-delete-dialog.html',
                    controller: 'PropertyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('property', null, { reload: 'property' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
