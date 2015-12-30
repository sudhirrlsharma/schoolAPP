'use strict';

angular.module('schoolAPPApp')
    .controller('OrganizationDetailController', function ($scope, $rootScope, $stateParams, entity, Organization) {
        $scope.organization = entity;
        $scope.load = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
            });
        };
        var unsubscribe = $rootScope.$on('schoolAPPApp:organizationUpdate', function(event, result) {
            $scope.organization = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
