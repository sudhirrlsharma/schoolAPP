'use strict';

angular.module('bachpanApp')
    .factory('Organization', function ($resource, DateUtils) {
        return $resource('api/organizations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
