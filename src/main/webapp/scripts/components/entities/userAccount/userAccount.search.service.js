'use strict';

angular.module('schoolAPPApp')
    .factory('UserAccountSearch', function ($resource) {
        return $resource('api/_search/userAccounts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
