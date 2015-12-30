'use strict';

angular.module('schoolAPPApp')
    .factory('Contect', function ($resource, DateUtils) {
        return $resource('api/contects/:id', {}, {
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
