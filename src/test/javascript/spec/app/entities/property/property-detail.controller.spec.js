'use strict';

describe('Controller Tests', function() {

    describe('Property Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProperty, MockLocation, MockUser, MockPhoto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProperty = jasmine.createSpy('MockProperty');
            MockLocation = jasmine.createSpy('MockLocation');
            MockUser = jasmine.createSpy('MockUser');
            MockPhoto = jasmine.createSpy('MockPhoto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Property': MockProperty,
                'Location': MockLocation,
                'User': MockUser,
                'Photo': MockPhoto
            };
            createController = function() {
                $injector.get('$controller')("PropertyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'assessoriaTorrellesApp:propertyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
