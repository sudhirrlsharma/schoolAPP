'use strict';

angular.module('schoolAPPApp')
	.controller('ContectDeleteController', function($scope, $uibModalInstance, entity, Contect) {

        $scope.contect = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Contect.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
