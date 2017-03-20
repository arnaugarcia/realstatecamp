(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('LocationDialogController', LocationDialogController);

    LocationDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams', 'entity', 'Location', 'Company', 'Property'];

    function LocationDialogController ($timeout, $scope, $state, $stateParams, entity, Location, Company, Property) {
        var vm = this;

        vm.location = entity;
        vm.clear = clear;
        vm.save = save;
        vm.companies = Company.query();
        vm.properties = Property.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $state.go('location', null, { reload: 'location' });
        }

        function save () {
            vm.isSaving = true;
            if (vm.location.id !== null) {
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:locationUpdate', result);
            $state.go('location', null, { reload: 'location' });
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
