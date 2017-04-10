
(function() {
    'use strict';

    angular
        .module('assessoriaTorrellesApp')
        .controller('locationPropertyController', locationPropertyController);

    locationPropertyController.$inject = ['$timeout', '$scope', '$state' ,'$stateParams', '$q', 'DataUtils', 'propertyEntity','locationEntity', 'Property', 'Location', 'User', 'Photo'];

    function locationPropertyController ($timeout, $scope, $state, $stateParams, $q, DataUtils, propertyEntity,locationEntity, Property, Location, User, Photo) {
        var vm = this;
        //** GUARDA PROPERTY
        vm.property = propertyEntity;
        vm.location = locationEntity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = saveStart;
        // vm.locations = Location.query({filter: 'property-is-null'});
        // $q.all([vm.property.$promise, vm.locations.$promise]).then(function() {
        //     if (!vm.property.location || !vm.property.location.id) {
        //         return $q.reject();
        //     }
        //     return Location.get({id : vm.property.location.id}).$promise;
        // }).then(function(location) {
        //     vm.locations.push(location);
        // });
        // vm.users = User.query();
        // vm.photos = Photo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save (result) {
            // vm.isSaving = true;
            console.log(result);
            vm.property.location = {id: result.id};
            if (vm.property.id !== null) {
                Property.update(vm.property, onSaveSuccess, onSaveError);
            } else {
                Property.save(vm.property, onSaveSuccess, onSaveError);
            }
        }

        function saveStart() {
            console.log(vm.location);
            vm.isSaving = true;
            if (vm.location.id !== null) {
                Location.update(vm.location, save, onSaveError);
            } else {
                Location.save(vm.location, save, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assessoriaTorrellesApp:propertyUpdate', result);
            $state.go('property', null, { reload: 'property' });
            vm.isSaving = false;
            toaster.pop({
                type: 'error',
                title: 'Title text',
                body: 'Body text',
                timeout: 3000
            });
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        /**OBETENER LOCATION Y GUARDAR LOCATION
         *
         */

        /*
            ng-flow setup
         */
        // flowFactoryProvider.defaults = {
        //     target: '',
        //     permanentErrors: [500, 501],
        //     maxChunkRetries: 1,
        //     chunkRetryInterval: 5000,
        //     simultaneousUploads: 1
        // };
        // flowFactoryProvider.on('catchAll', function (event) {
        //     console.log('catchAll', arguments);
        // });
        // // Can be used with different implementations of Flow.js
        // flowFactoryProvider.factory = fustyFlowFactory;

    }
})();
