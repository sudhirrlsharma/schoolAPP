'use strict';

angular.module('bachpanApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


