(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('AboutController', AboutController);

    AboutController.$inject = ['$scope', '$state', 'About'];

    function AboutController ($scope, $state, About) {
        var vm = this;
        
        vm.abouts = [];

        loadAll();

        function loadAll() {
            About.query(function(result) {
                vm.abouts = result;
            });
        }
    }
})();
