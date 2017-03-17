(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('ContactController', ContactController);

    ContactController.$inject = ['$scope', '$state', 'Contact', 'Company'];

    function ContactController ($scope, $state, Contact, Company) {

        var vm = this;

        vm.contacts = [];
        vm.companies = [];

        vm.companyInfo = {
            "name": "",
            "phone": "",
            "email": "",
            "cif": "",
            "lat": "",
            "long": ""
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
                vm.companyInfo.lat = vm.companies[0].location.latitude;
                vm.companyInfo.long = vm.companies[0].location.longitude;

                $("#map").gmap3({
                    map: {
                        options: {
                            center: [vm.companyInfo.lat,vm.companyInfo.long],
                            zoom: 15,
                            scrollwheel: false
                        }
                    },
                    marker:{
                        latLng: [vm.companyInfo.lat,vm.companyInfo.long]
                    }
                });
            });
        }
    }
})();
