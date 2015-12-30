'use strict';

angular.module('schoolAPPApp')
	.controller('UserAccountDeleteController', function($scope, $uibModalInstance, entity, UserAccount) {

        $scope.userAccount = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
