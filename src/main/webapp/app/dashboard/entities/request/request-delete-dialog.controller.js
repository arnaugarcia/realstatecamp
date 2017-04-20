(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('RequestDeleteController',RequestDeleteController);

    RequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'Request'];

    function RequestDeleteController($uibModalInstance, entity, Request) {
        var vm = this;

        vm.request = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Request.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
