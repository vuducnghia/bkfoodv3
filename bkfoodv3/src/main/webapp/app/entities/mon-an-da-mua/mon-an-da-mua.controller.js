(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_an_da_muaController', Mon_an_da_muaController);

    Mon_an_da_muaController.$inject = ['Mon_an_da_mua'];

    function Mon_an_da_muaController(Mon_an_da_mua) {

        var vm = this;

        vm.mon_an_da_muas = [];

        loadAll();

        function loadAll() {
            Mon_an_da_mua.query(function(result) {
                vm.mon_an_da_muas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
