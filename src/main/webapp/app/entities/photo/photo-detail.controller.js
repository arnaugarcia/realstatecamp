(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PhotoDetailController', PhotoDetailController);

    PhotoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Photo', 'Property'];

    function PhotoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Photo, Property) {
        var vm = this;

        vm.photo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:photoUpdate', function(event, result) {
            vm.photo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
