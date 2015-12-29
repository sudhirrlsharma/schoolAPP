 'use strict';

angular.module('schoolappApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-schoolappApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-schoolappApp-params')});
                }
                return response;
            },
        };
    });