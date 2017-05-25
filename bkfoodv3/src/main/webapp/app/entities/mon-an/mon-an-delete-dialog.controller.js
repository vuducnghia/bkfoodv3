(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_anDeleteController',Mon_anDeleteController);

    Mon_anDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mon_an'];

    function Mon_anDeleteController($uibModalInstance, entity, Mon_an) {
        var vm = this;

        vm.mon_an = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mon_an.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
