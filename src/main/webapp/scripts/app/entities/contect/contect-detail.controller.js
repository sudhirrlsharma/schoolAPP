'use strict';

angular.module('schoolAPPApp')
    .controller('ContectDetailController', function ($scope, $rootScope, $stateParams, entity, Contect) {
        $scope.contect = entity;
        $scope.load = function (id) {
            Contect.get({id: id}, function(result) {
                $scope.contect = result;
            });
        };
        var unsubscribe = $rootScope.$on('schoolAPPApp:contectUpdate', function(event, result) {
            $scope.contect = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
