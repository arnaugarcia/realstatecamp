(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PropertyController', PropertyController);

    PropertyController.$inject = ['$scope', '$state', 'DataUtils', 'Property', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function PropertyController ($scope, $state, DataUtils, Property, ParseLinks, AlertService, pagingParams, paginationConstants) {
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
        vm.filterCritera.location = 'Barcelona';

        loadAll();

        vm.searchByFilters = function () {
            Property.byFilters({
                // es igual a..
                //?location=Barcelona
                //parametro:valor
                location: vm.filterCritera.location

            }, onSuccessByFilter, onError);

            function onError(error) {
                AlertService.error(error.data.message);
            }

            function onSuccessByFilter(data,headers) {
                vm.listByFilter = [];
                for(var i = 0;i<data.length;i++){
                    vm.listByFilter.push(data[i]);
                }
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

            Property.byFilters({
                // es igual a..
                //?location=Barcelona
                //parametro:valor
                location: vm.filterCritera.location

            }, onSuccessByFilter, onError);

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
                vm.properties = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

            function onSuccessByFilter(data,headers) {
                for(var i = 0;i<data.length;i++){
                    vm.listByFilter.push(data[i]);
                }
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
