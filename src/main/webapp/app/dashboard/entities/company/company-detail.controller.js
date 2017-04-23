(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Company', 'Location'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, Company, Location) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
