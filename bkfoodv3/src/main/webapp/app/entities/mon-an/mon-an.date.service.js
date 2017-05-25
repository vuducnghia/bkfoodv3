(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('MonAnDate', MonAnDate);

    MonAnDate.$inject = ['$resource', 'DateUtils'];


    function MonAnDate ($resource, DateUtils) {
        var resourceUrl =  'api/mon-ans/date';

        return $resource(resourceUrl, {}, {
//            'query': { method: 'GET', isArray: true},
        });
    }
})();
