(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('NotificationDeleteController',NotificationDeleteController);

    NotificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Notification'];

    function NotificationDeleteController($uibModalInstance, entity, Notification) {
        var vm = this;

        vm.notification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Notification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
