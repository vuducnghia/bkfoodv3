(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('UserCurrent', UserCurrent);

    UserCurrent.$inject = ['$resource'];

    function UserCurrent ($resource) {

        var resourceUrl =  'api/account';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false}
        });
    }
})();
