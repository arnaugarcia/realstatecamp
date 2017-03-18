(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('ContactDeleteController',ContactDeleteController);

    ContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'Contact'];

    function ContactDeleteController($uibModalInstance, entity, Contact) {
        var vm = this;

        vm.contact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Contact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
