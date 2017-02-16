(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('CompanyDeleteController',CompanyDeleteController);

    CompanyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Company'];

    function CompanyDeleteController($uibModalInstance, entity, Company) {
        var vm = this;

        vm.company = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Company.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
