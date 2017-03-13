(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PropertyController', PropertyController);

    PropertyController.$inject = ['$scope', '$state','Principal' ,'DataUtils', 'Property', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function PropertyController ($scope, $state,Principal, DataUtils, Property, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.listByFilter = [];
        vm.filterCritera = {};
        // vm.filterCritera.location = 'Barcelona';
        vm.isAuthenticated = null;
        vm.account = null;

        vm.orderCriteria = {};
        vm.orderCriteria.criteria = 'price';
        vm.orderCriteria.rev = true;

        vm.changeOrder = function (criteria) {
            vm.orderCriteria.criteria = criteria;
            if(vm.orderCriteria.rev ===true){
                vm.orderCriteria.rev = false;
            }else if(vm.orderCriteria.rev ===false){
                vm.orderCriteria.rev = true;
            }
        };

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        loadAll();

        vm.searchByFilters = function () {

            Property.byFilters({
                // es igual a..
                //?location=Barcelona
                //parametro:valor

                location: vm.filterCritera.location,
                furnished: vm.filterCritera.furnished,
                pool: vm.filterCritera.pool,
                garage: vm.filterCritera.garage,
                ac: vm.filterCritera.ac,
                elevator: vm.filterCritera.elevator,
                terrace: vm.filterCritera.terrace,
                numberBedroom: vm.filterCritera.numberBedroom,
                numberWc: vm.filterCritera.numberWc,
                minPrice: vm.filterCritera.minPrice,
                maxPrice: vm.filterCritera.maxPrice,
                minSize: vm.filterCritera.minSize,
                maxSize: vm.filterCritera.maxSize,
                serviceType: vm.filterCritera.serviceType,
                buildingType: vm.filterCritera.buildingType

            }, onSuccessByFilter, onError);

            function onError(error) {
                AlertService.error(error.data.message);
            }

            function onSuccessByFilter(data,headers) {
                // vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listByFilter = data;
                vm.page = pagingParams.page;
            }

        }

        function loadAll () {
            Property.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            //console.log("aaaaa");
            // ?location=Barcelona&ac=true
            //
            //     Space.byFilters({minprice: firstPrice, maxprice: lastPrice, numpers: numPers, services: servicesStr, address: address}, function (result) {
            //         $scope.spaces = result;
            //     });

            // Property.byFilters({
            //     // es igual a..
            //     //?location=Barcelona
            //     //parametro:valor
            //     location: vm.filterCritera.location
            //
            // }, onSuccessByFilter, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listByFilter = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

            function onSuccessByFilter(data,headers) {
                vm.listByFilter = data;
                // for(var i = 0;i<data.length;i++){
                //     vm.listByFilter.push(data[i]);
                // }
            }

        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
