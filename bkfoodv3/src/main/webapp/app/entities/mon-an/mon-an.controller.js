(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_anController', Mon_anController);

    Mon_anController.$inject = ['DataUtils', 'Mon_an'];

    function Mon_anController(DataUtils, Mon_an) {

        var vm = this;

        vm.mon_ans = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Mon_an.query(function(result) {
                vm.mon_ans = result;
                vm.searchQuery = null;
            });
        }
    }
})();
