(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Hoa_donDeleteController',Hoa_donDeleteController);

    Hoa_donDeleteController.$inject = ['$uibModalInstance', 'entity', 'Hoa_don'];

    function Hoa_donDeleteController($uibModalInstance, entity, Hoa_don) {
        var vm = this;

        vm.hoa_don = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Hoa_don.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
