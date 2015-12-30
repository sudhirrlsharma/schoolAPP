'use strict';

angular.module('schoolAPPApp')
    .factory('ContectSearch', function ($resource) {
        return $resource('api/_search/contects/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
