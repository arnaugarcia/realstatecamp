(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'FilterService'];

    function HomeController ($scope, Principal, LoginService, $state, FilterService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.filtersList = null;

        loadAll();

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        function loadAll (){
            FilterService.home(function (result) {
                vm.filtersList = result;
            });
        }

        function getAccount() {

            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
