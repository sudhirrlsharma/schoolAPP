'use strict';

angular.module('schoolAPPApp')
    .factory('OrganizationSearch', function ($resource) {
        return $resource('api/_search/organizations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
