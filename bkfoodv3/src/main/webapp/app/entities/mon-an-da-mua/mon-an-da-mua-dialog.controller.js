(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_an_da_muaDialogController', Mon_an_da_muaDialogController);

    Mon_an_da_muaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mon_an_da_mua', 'User'];

    function Mon_an_da_muaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mon_an_da_mua, User) {
        var vm = this;

        vm.mon_an_da_mua = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mon_an_da_mua.id !== null) {
                Mon_an_da_mua.update(vm.mon_an_da_mua, onSaveSuccess, onSaveError);
            } else {
                Mon_an_da_mua.save(vm.mon_an_da_mua, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bkfoodv3App:mon_an_da_muaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
