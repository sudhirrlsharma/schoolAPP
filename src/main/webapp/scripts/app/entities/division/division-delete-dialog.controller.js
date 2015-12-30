'use strict';

angular.module('schoolAPPApp')
	.controller('DivisionDeleteController', function($scope, $uibModalInstance, entity, Division) {

        $scope.division = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Division.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
