(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('DashboardFooterController', DashboardFooterController);

    DashboardFooterController.$inject = ['Company'];

    function DashboardFooterController (Company) {
        var vm = this;

        vm.company = [];

        loadAll();

        function loadAll() {

            Company.get(function (result) {
                vm.company = result;
            });
        }

    }
})();
