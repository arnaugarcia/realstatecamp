(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('DashboardHomeController', DashboardHomeController);

    DashboardHomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Request', 'AlertService'];

    function DashboardHomeController ($scope, Principal, LoginService, $state, Request, AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        loadAll();

        getAccount();

        function loadAll(){
            Request.active({

            }, onSuccess, onError);

            function onSuccess(data) {
                vm.requests = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
