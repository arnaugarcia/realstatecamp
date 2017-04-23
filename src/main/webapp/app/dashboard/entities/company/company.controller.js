(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('CompanyController', CompanyController);

    CompanyController.$inject = ['$scope', '$state', 'Company', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function CompanyController ($scope, $state, Company, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {
            Company.query({

            }, onSuccess, onError);

            function onSuccess(data) {

                vm.company = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
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
