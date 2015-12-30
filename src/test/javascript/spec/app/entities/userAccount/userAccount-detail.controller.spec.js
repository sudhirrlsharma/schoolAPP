'use strict';

describe('Controller Tests', function() {

    describe('UserAccount Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUserAccount, MockOrganization;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUserAccount = jasmine.createSpy('MockUserAccount');
            MockOrganization = jasmine.createSpy('MockOrganization');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UserAccount': MockUserAccount,
                'Organization': MockOrganization
            };
            createController = function() {
                $injector.get('$controller')("UserAccountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'schoolAPPApp:userAccountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
