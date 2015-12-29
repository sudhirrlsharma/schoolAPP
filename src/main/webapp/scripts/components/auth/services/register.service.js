'use strict';

angular.module('schoolappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


