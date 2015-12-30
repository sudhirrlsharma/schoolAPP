'use strict';

angular.module('schoolAPPApp')
    .controller('DivisionController', function ($scope, $state, Division, DivisionSearch) {

        $scope.divisions = [];
        $scope.loadAll = function() {
            Division.query(function(result) {
               $scope.divisions = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            DivisionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.divisions = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.division = {
                name: null,
                id: null
            };
        };
    });
