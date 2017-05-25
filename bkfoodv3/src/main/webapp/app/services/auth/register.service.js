(function () {
    'use strict';

    angular
        .module('bkfoodv3App')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
