(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('HomeController', DashboardHomeController);

    DashboardHomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function DashboardHomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

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
