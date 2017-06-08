(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('NotificationController', NotificationController);

    NotificationController.$inject = ['$scope', '$state', 'DataUtils', 'Notification', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function NotificationController ($scope, $state, DataUtils, Notification, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.notifications = [];

        loadAll();

        function loadAll () {
            Notification.byUser({
            }, onSuccess, onError);
            function onSuccess(data) {
                vm.notifications = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.deleteNotifications = function deleteNotifications() {
            console.log("ids" + getAllChecked());
                Notification.multipleDelete({"ids" : getAllChecked()}, $state.go($state.current, {}, {reload: true}));

        };

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        vm.toggleAll = function() {
            angular.forEach(vm.notifications, function(itm){ itm.selected = vm.isAllSelected; });

        };

        vm.optionToggled = function(){
            $scope.isAllSelected = vm.notifications.every(function(itm){ return itm.selected; })
        };

        function getAllChecked() {
            var allChecked = "";
            angular.forEach(vm.notifications, function (itm) {
                if (itm.selected){
                    if (allChecked == ""){
                        allChecked = itm.id;
                    }else{
                        allChecked = allChecked + "-" + itm.id;
                    }
                }
            });
            console.log(allChecked);
            return allChecked;

        }
    }
})();
