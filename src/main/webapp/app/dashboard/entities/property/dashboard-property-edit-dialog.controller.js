(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('ProperyEditDialogController', ProperyEditDialogController);

    ProperyEditDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams', 'Location', 'Company', 'Property'];

    function ProperyEditDialogController ($timeout, $scope, $state, $stateParams, Location, Company, Property) {
        var vm = this;


        Property.get({id : $stateParams.id}, function (result) {
            vm.property = result;
            vm.location = result.location;
        })

        console.log(vm.property);



    }
})();
