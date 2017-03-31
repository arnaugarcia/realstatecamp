(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$scope', '$state', 'Contact', 'Company'];

    function FooterController ($scope, $state, Contact, Company) {
        var vm = this;

        vm.contacts = [];
        vm.companies = [];

        vm.companyInfo = {
            "name": "",
            "phone": "",
            "email": "",
            "cif": "",
            "location":{
                "town":"",
                "province":"",
                "road":"",
                "typeOfRoad":"",
                "nameRoad":""
            }
        };

        loadAll();

        function loadAll() {
            Contact.query(function(result) {
                vm.contacts = result;
            });
            Company.query(function (result) {
                vm.companies = result;
                vm.companyInfo.name = vm.companies[0].name;
                vm.companyInfo.phone = vm.companies[0].phone;
                vm.companyInfo.email = vm.companies[0].email;
                vm.companyInfo.cif = vm.companies[0].cif;
                vm.companyInfo.location.town = vm.companies[0].location.town;
                vm.companyInfo.location.province = vm.companies[0].location.province;
                vm.companyInfo.location.road = vm.companies[0].location.typeOfRoad + " " + vm.companies[0].location.typeOfRoad;
                vm.companyInfo.location.typeOfRoad = vm.companies[0].location.typeOfRoad;
            });
        }

    }
})();
