'use strict';

angular.module('bachpanApp')
    .controller('OrganizationController', function ($scope, Organization, ParseLinks) {
        $scope.organizations = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Organization.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.organizations.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.organizations = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
                $('#deleteOrganizationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Organization.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteOrganizationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.organization = {name: null, type: null, id: null};
        };
    });
