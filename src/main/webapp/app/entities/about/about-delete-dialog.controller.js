(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('AboutDeleteController',AboutDeleteController);

    AboutDeleteController.$inject = ['$uibModalInstance', 'entity', 'About'];

    function AboutDeleteController($uibModalInstance, entity, About) {
        var vm = this;

        vm.about = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            About.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
