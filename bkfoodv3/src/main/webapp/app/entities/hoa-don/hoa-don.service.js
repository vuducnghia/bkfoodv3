(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('Hoa_don', Hoa_don);

    Hoa_don.$inject = ['$resource'];

    function Hoa_don ($resource) {
        var resourceUrl =  'api/hoa-dons/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
