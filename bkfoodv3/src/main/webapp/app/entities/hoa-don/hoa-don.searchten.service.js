(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('Hoa_don_search', Hoa_don_search);

    Hoa_don_search.$inject = ['$resource'];

    function Hoa_don_search ($resource) {
        var resourceUrl =  'api/hoa-dons/searchtenmonan';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
