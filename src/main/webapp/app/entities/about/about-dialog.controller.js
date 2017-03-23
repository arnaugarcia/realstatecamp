(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('AboutDialogController', AboutDialogController);

    AboutDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'About'];

    function AboutDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, About) {
        var vm = this;

        vm.about = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.about.id !== null) {
                About.update(vm.about, onSaveSuccess, onSaveError);
            } else {
                About.save(vm.about, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:aboutUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
