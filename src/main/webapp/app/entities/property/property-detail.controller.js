(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('PropertyDetailController', PropertyDetailController);

    PropertyDetailController.$inject = ['$scope', '$timeout', '$rootScope', '$stateParams', '$translate', 'previousState', 'DataUtils', 'entity','Request', 'AlertService', 'Company','Photo','NgMap'];

    function PropertyDetailController($scope, $timeout, $rootScope, $stateParams, $translate, previousState, DataUtils, entity, Request, AlertService, Company,Photo, NgMap) {
        var vm = this;

        vm.property = entity;
        vm.company = null;
        vm.request = null;
        vm.contactForm = null;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        Photo.getPhotos({id : $stateParams.id}, function (result) {
            vm.storedPhotos = result;
        });

        vm.language = function () {
            return $translate.proposedLanguage();
        };

        var unsubscribe = $rootScope.$on('assessoriaTorrellesApp:propertyUpdate', function(event, result) {
            vm.property = result;
        });

        vm.company = [];

        loadAll();

        function loadAll() {

            Company.get(function (result) {
                vm.company = result;
            });

        }

        vm.showMap = function loadMap() {

            NgMap.getMap().then(function(map) {
                vm.map = map;
            });

            vm.googleMapsUrl = 'https://maps.google.com/maps/api/js';
            vm.pauseLoading=true;

            $timeout(function() {
                vm.pauseLoading=false;
            }, 2000);

        };

        vm.sendRequest = function () {
            vm.request = {
                "comment": vm.contactForm.comment,
                "date": new Date(),
                "email": vm.contactForm.email,
                "name": vm.contactForm.name,
                "phone": vm.contactForm.phone,
                "property": vm.property,
                "state": "open"
            };
            Request.save(vm.request, onSaveSuccess, onSaveError);
            function onSaveSuccess() {
                AlertService.success("Hey!");
            }
            function onSaveError(error) {
                AlertService.error(error);
            }
        };

        $scope.$on('$destroy', unsubscribe);
    }
})();
