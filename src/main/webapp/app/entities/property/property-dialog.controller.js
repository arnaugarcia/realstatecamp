(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PropertyDialogController', PropertyDialogController);

    PropertyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Property', 'Location', 'User', 'Photo'];

    function PropertyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Property, Location, User, Photo) {
        var vm = this;

        vm.property = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.locations = Location.query({filter: 'property-is-null'});
        $q.all([vm.property.$promise, vm.locations.$promise]).then(function() {
            if (!vm.property.location || !vm.property.location.id) {
                return $q.reject();
            }
            return Location.get({id : vm.property.location.id}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.users = User.query();
        vm.photos = Photo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.property.id !== null) {
                Property.update(vm.property, onSaveSuccess, onSaveError);
            } else {
                Property.save(vm.property, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:propertyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
