(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('Mon_an_da_mua', Mon_an_da_mua);

    Mon_an_da_mua.$inject = ['$resource'];

    function Mon_an_da_mua ($resource) {
        var resourceUrl =  'api/mon-an-da-muas/:id';

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
