'use strict';

angular.module('schoolAPPApp').controller('UserAccountDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserAccount', 'Organization',
        function($scope, $stateParams, $uibModalInstance, entity, UserAccount, Organization) {

        $scope.userAccount = entity;
        $scope.organizations = Organization.query();
        $scope.load = function(id) {
            UserAccount.get({id : id}, function(result) {
                $scope.userAccount = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('schoolAPPApp:userAccountUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userAccount.id != null) {
                UserAccount.update($scope.userAccount, onSaveSuccess, onSaveError);
            } else {
                UserAccount.save($scope.userAccount, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
