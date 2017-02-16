(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PhotoDeleteController',PhotoDeleteController);

    PhotoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Photo'];

    function PhotoDeleteController($uibModalInstance, entity, Photo) {
        var vm = this;

        vm.photo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Photo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
