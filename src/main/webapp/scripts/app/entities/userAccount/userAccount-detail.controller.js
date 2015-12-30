'use strict';

angular.module('schoolAPPApp')
    .controller('UserAccountDetailController', function ($scope, $rootScope, $stateParams, entity, UserAccount, Organization) {
        $scope.userAccount = entity;
        $scope.load = function (id) {
            UserAccount.get({id: id}, function(result) {
                $scope.userAccount = result;
            });
        };
        var unsubscribe = $rootScope.$on('schoolAPPApp:userAccountUpdate', function(event, result) {
            $scope.userAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
