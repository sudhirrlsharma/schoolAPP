'use strict';

angular.module('schoolappApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
