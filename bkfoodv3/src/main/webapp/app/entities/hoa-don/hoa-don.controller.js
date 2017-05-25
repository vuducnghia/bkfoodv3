(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Hoa_donController', Hoa_donController);

    Hoa_donController.$inject = ['Hoa_don'];

    function Hoa_donController(Hoa_don) {

        var vm = this;

        vm.hoa_dons = [];

        loadAll();

        function loadAll() {
            Hoa_don.query(function(result) {
                vm.hoa_dons = result;
                vm.searchQuery = null;
            });
        }
    }
})();
