(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('Hoa_don_search_ByUser', Hoa_don_search_ByUser);

    Hoa_don_search_ByUser.$inject = ['$resource'];

    function Hoa_don_search_ByUser ($resource) {
        var resourceUrl =  'api/hoa-dons/searchHoaDonbyUser';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
