(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_an_da_muaDeleteController',Mon_an_da_muaDeleteController);

    Mon_an_da_muaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mon_an_da_mua'];

    function Mon_an_da_muaDeleteController($uibModalInstance, entity, Mon_an_da_mua) {
        var vm = this;

        vm.mon_an_da_mua = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mon_an_da_mua.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
