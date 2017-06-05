(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('DashboardNavbarController', DashboardNavbarController);

    DashboardNavbarController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService','AlertService', '$translate', 'Notification', 'Request'];

    function DashboardNavbarController ($scope, $state, Auth, Principal, ProfileService, LoginService, AlertService, $translate, Notification, Request) {
        var vm = this;

        vm.account = null;

        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.totalRequests = 0;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
        vm.loadPage = loadPage;

        vm.transition = transition;

        loadAll();

        getAccount();

        function loadAll () {

            Notification.byUserActive({
                size: vm.itemsPerPage
            }, onSuccessNotification, onError);

            function onSuccessNotification(data) {
                vm.notifications = data;
                vm.notifications.seen = getUnSeenNotificationsNumber(data);
            }

            Request.active({
                size: vm.itemsPerPage
            },onSuccessRequest, onError);

            function onSuccessRequest(data, headers) {
                vm.requests = data;
                vm.totalRequests = headers('X-Total-Count');

            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function getUnSeenNotificationsNumber(data) {
            var seen = 0;
            for (var i = 0; i<data.length; i++){
                if (data[i].seen == undefined || data[i].seen == false){
                    seen++;
                }
            }
            return seen;
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }


        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }


    }
})();
