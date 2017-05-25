(function() {
    'use strict';
    angular
        .module('bkfoodv3App')
        .factory('TinhTien', TinhTien);

    TinhTien.$inject = ['$resource'];

    function TinhTien ($resource) {
        var resourceUrl =  'api/hoa-dons/TinhTienHoaDonbyUser';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false}
        });
    }
})();
