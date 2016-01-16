'use strict';

angular.module('bachpanApp')
    .controller('OrganizationDetailController', function ($scope, $rootScope, $stateParams, entity, Organization) {
        $scope.organization = entity;
        $scope.load = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
            });
        };
        $rootScope.$on('bachpanApp:organizationUpdate', function(event, result) {
            $scope.organization = result;
        });
    });
