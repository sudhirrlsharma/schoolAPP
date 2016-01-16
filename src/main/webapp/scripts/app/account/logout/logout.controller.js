'use strict';

angular.module('bachpanApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
