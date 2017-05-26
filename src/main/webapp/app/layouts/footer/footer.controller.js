(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['Company', 'NgMap','Property'];

    function FooterController (Company, NgMap, Property) {
        var vm = this;

        NgMap.getMap().then(function(map) {

        });

        loadAll();

        function loadAll() {

            Property.top5(function(result) {
                vm.propertiesTop5 = result;
            });

            Company.get(function (result) {
                vm.company = result;
            });
        }

    }
})();
