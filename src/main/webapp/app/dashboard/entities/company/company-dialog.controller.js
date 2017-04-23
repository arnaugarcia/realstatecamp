(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('CompanyDialogController', CompanyDialogController);

    CompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Company', 'Location'];

    function CompanyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Company, Location) {
        var vm = this;

        vm.company = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query({filter: 'company-is-null'});
        $q.all([vm.company.$promise, vm.locations.$promise]).then(function() {
            if (!vm.company.location || !vm.company.location.id) {
                return $q.reject();
            }
            return Location.get({id : vm.company.location.id}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.company.id !== null) {
                Company.update(vm.company, onSaveSuccess, onSaveError);
            } else {
                Company.save(vm.company, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:companyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
