(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Company', 'Property'];

    function LocationDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Company, Property) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
