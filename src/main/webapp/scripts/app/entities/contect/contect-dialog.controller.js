'use strict';

angular.module('schoolAPPApp').controller('ContectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contect',
        function($scope, $stateParams, $uibModalInstance, entity, Contect) {

        $scope.contect = entity;
        $scope.load = function(id) {
            Contect.get({id : id}, function(result) {
                $scope.contect = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('schoolAPPApp:contectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contect.id != null) {
                Contect.update($scope.contect, onSaveSuccess, onSaveError);
            } else {
                Contect.save($scope.contect, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
