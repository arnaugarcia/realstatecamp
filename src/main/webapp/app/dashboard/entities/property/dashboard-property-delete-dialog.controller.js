(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('DashboardPropertyDeleteController',DashboardPropertyDeleteController);

    DashboardPropertyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Property'];

    function DashboardPropertyDeleteController($uibModalInstance, entity, Property) {
        var vm = this;

        vm.property = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Property.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
