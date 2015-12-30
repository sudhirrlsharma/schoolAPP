'use strict';

angular.module('schoolAPPApp')
    .controller('ContectController', function ($scope, $state, Contect, ContectSearch, ParseLinks) {

        $scope.contects = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Contect.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.contects.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.contects = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ContectSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.contects = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.contect = {
                name: null,
                phone: null,
                email: null,
                im: null,
                address1: null,
                adress2: null,
                city: null,
                state: null,
                country: null,
                pinCode: null,
                id: null
            };
        };
    });
