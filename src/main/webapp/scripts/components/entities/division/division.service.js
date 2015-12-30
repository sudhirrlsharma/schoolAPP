'use strict';

angular.module('schoolAPPApp')
    .factory('Division', function ($resource, DateUtils) {
        return $resource('api/divisions/:id', {}, {
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
