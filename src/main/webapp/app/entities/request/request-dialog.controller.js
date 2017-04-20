(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('RequestDialogController', RequestDialogController);

    RequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Request', 'Property'];

    function RequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Request, Property) {
        var vm = this;

        vm.request = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.properties = Property.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.request.id !== null) {
                Request.update(vm.request, onSaveSuccess, onSaveError);
            } else {
                Request.save(vm.request, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:requestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
