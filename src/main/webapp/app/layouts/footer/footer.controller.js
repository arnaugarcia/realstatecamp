(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$scope', '$state', 'Contact', 'Company', 'NgMap','Property'];

    function FooterController ($scope, $state, Contact, Company, NgMap, Property) {
        var vm = this;

        NgMap.getMap().then(function(map) {

        });

        vm.contacts = [];
        vm.companies = [];

        loadAll();

        function loadAll() {
            Contact.query(function(result) {
                vm.contacts = result;
            });

            Property.top5(function(result) {
                vm.propertiesTop5 = result;
            });

            Company.get(function (result) {
                vm.company = result;
            });
        }

    }
})();
