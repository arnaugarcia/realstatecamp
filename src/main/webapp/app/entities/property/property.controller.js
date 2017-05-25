(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PropertyController', PropertyController);

    PropertyController.$inject = ['$scope', '$state','Principal' ,'DataUtils', 'Property', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants','searchCriteria', 'Request'];

    function PropertyController ($scope, $state,Principal, DataUtils, Property, ParseLinks, AlertService, pagingParams, paginationConstants,searchCriteria, Request) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        //TODO intentar cambiar directamente el paginationConstants ya que lo está inyectando en el controller
        //paginationsConstants.itemsPerPage = model...
        //vm.itemsPerPage = paginationConstants.itemsPerPage
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.listByFilter = [];

        //********** PROBLEM **********//

        //Cuando se pagina, se recarga el controller, por lo que
        //vm.filterCritera = {}; siempre estará vacío
        //Si hago una busqueda con location Barcelona
        //Al recargar el controller, la location estará vacía

        //Ademas de esto, el vm.itemsPerPage es por defecto 20 ya que viene dado por aginationConstants.itemsPerPage
        //Como model en el properties.html tenemos un select y option para seleccionar el tamaño de properties por pagina
        //Pero este no se actualiza automaticamente, hay que hacer un ng-change y refrescar la busqueda con ese parametro
        //Dentro del ng-change hacemos un vm.searchByFilters()
        //Pero los parametros se pierden ya que se vuelve a cargar el controller... Ouroboros

        //********** ****** **********//

        vm.filterCritera = searchCriteria;

        vm.test = searchCriteria;
        // vm.filterCritera = Property.criteria;

        // vm.filterCritera.location = 'Barcelona';
        vm.isAuthenticated = null;
        vm.account = null;

        vm.orderCriteria = {};
        vm.orderCriteria.criteria = 'price';
        vm.orderCriteria.rev = true;

        vm.changeOrder = function (criteria) {
            vm.orderCriteria.criteria = criteria;
            if(vm.orderCriteria.rev === true){
                vm.orderCriteria.rev = false;
            }else if(vm.orderCriteria.rev === false){
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
                buildingType: vm.filterCritera.buildingType,
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()

            }, onSuccessByFilter, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onError(error) {
                toastr.error(error.data.error, 'Error!');
            }

            function onSuccessByFilter(data,headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.listByFilter = data;
                vm.page = pagingParams.page;
            }

        };

        vm.removeFilters = function () {
            vm.filterCritera = {};
            searchCriteria = {};

        }

        loadAll();

        function loadAll () {
            // Property.query({
            //     page: pagingParams.page - 1,
            //     size: vm.itemsPerPage,
            //     sort: sort()
            // }, onSuccess, onError);
            //
            // //console.log("aaaaa");
            // // ?location=Barcelona&ac=true
            // //
            // //     Space.byFilters({minprice: firstPrice, maxprice: lastPrice, numpers: numPers, services: servicesStr, address: address}, function (result) {
            // //         $scope.spaces = result;
            // //     });
            //
            // // Property.byFilters({
            // //     // es igual a..
            // //     //?location=Barcelona
            // //     //parametro:valor
            // //     location: vm.filterCritera.location
            // //
            // // }, onSuccessByFilter, onError);
            //
            // function sort() {
            //     var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            //     if (vm.predicate !== 'id') {
            //         result.push('id');
            //     }
            //     return result;
            // }
            // function onSuccess(data, headers) {
            //     vm.links = ParseLinks.parse(headers('link'));
            //     vm.totalItems = headers('X-Total-Count');
            //     vm.queryCount = vm.totalItems;
            //     vm.listByFilter = data;
            //     vm.page = pagingParams.page;
            // }
            // function onError(error) {
            //     AlertService.error(error.data.message);
            // }

            vm.searchByFilters();


        }

        vm.changeItemsPerPage = function(){
            paginationConstants.itemsPerPage = vm.itemsPerPage;
            vm.searchByFilters();
        };

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
