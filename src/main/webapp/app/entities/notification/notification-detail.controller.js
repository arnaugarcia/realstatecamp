(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('NotificationDetailController', NotificationDetailController);

    NotificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Notification', 'User'];

    function NotificationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Notification, User) {
        var vm = this;

        vm.notification = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:notificationUpdate', function(event, result) {
            vm.notification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
