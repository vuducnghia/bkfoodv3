(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('MonAnSearchTest', MonAnSearchTest);

    MonAnSearchTest.$inject = ['$resource'];


    function MonAnSearchTest ($resource) {
        var resourceUrl =  'api/mon-ans/searchten';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
