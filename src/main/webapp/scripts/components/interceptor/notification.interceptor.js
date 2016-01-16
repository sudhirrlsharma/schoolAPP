 'use strict';

angular.module('bachpanApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-bachpanApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-bachpanApp-params')});
                }
                return response;
            },
        };
    });