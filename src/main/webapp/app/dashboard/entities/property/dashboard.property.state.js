(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dashboard-property', {
            parent: 'dashboard-entity',
            url: '/dashboard/property?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                css: [
                    'content/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.css',
                    {
                        name: 'wysihtml',
                        href: 'content/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.css'
                    },
                    'content/css/AdminLTE.css',
                    {
                        name: 'dashboard',
                        href: 'content/css/AdminLTE.css'
                    },
                    'content/css/skin-green.min.css',
                    {
                        name: 'dashboard-skin',
                        href: 'content/css/skin-green.min.css'
                    },
                    'content/plugins/iCheck/all.css',
                    {
                        name: 'iCheck',
                        href: 'content/plugins/iCheck/all.css'
                    }
                ],
                pageTitle: 'assessoriaTorrellesApp.property.home.title'
            },
            ncyBreadcrumb: {
                label: 'Property'
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/entities/property/dashboard.property-new.html',
                    controller: 'PropertyController',
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
                    $translatePartialLoader.addPart('property');
                    $translatePartialLoader.addPart('location');
                    $translatePartialLoader.addPart('buildingType');
                    $translatePartialLoader.addPart('serviceType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }).state('dashboard-property.new', {
            parent: 'dashboard-property',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/entities/property/dashboard.property-new.html',
                    controller: 'locationPropertyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                propertyEntity: function () {
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
                },
                locationEntity: function () {
                    return {
                        ref: null,
                        province: null,
                        town: null,
                        typeOfRoad: null,
                        nameRoad: null,
                        number: null,
                        apartment: null,
                        building: null,
                        door: null,
                        stair: null,
                        urlgmaps: null,
                        latitude: null,
                        longitude: null,
                        id: null
                    };
                }
            }
            }).state('dashboard-property.edit', {
                parent: 'dashboard-property',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views:{
                    'content@': {
                        templateUrl: 'app/dashboard/entities/property/dashboard.property-new.html',
                        controller: 'locationPropertyController',
                        controllerAs: 'vm'
                    }
                }

            })

            .state('dashboard-property.list', {
                parent: 'dashboard-property',
                url: '/list',
                data: {
                    authorities: ['ROLE_USER'],
                    css: [
                        'content/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.css',
                        {
                            name: 'wysihtml',
                            href: 'content/css/bootstrap-wysihtml5/bootstrap3-wysihtml5.css'
                        },
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
                    ],
                    pageTitle: 'assessoriaTorrellesApp.property.home.title'
                },
                ncyBreadcrumb: {
                    label: 'Properties list'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/dashboard/entities/property/dashboard.properties.html',
                        controller: 'PropertyListController',
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
                        $translatePartialLoader.addPart('property');
                        $translatePartialLoader.addPart('location');
                        $translatePartialLoader.addPart('buildingType');
                        $translatePartialLoader.addPart('serviceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('dashboard-property.delete', {
            parent: 'dashboard-property',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/dashboard/entities/property/dashboard-property-delete-dialog.html',
                    controller: 'DashboardPropertyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Property', function(Property) {
                            return Property.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dashboard-property.list', null, { reload: 'dashboard-property.list' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
