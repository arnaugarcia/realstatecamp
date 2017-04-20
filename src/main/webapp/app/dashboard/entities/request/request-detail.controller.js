(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('RequestDetailController', RequestDetailController);

    RequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Request', 'Property'];

    function RequestDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Request, Property) {
        var vm = this;

        vm.request = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:requestUpdate', function(event, result) {
            vm.request = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
