'use strict';

angular.module('schoolAPPApp')
    .controller('DivisionDetailController', function ($scope, $rootScope, $stateParams, entity, Division) {
        $scope.division = entity;
        $scope.load = function (id) {
            Division.get({id: id}, function(result) {
                $scope.division = result;
            });
        };
        var unsubscribe = $rootScope.$on('schoolAPPApp:divisionUpdate', function(event, result) {
            $scope.division = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
