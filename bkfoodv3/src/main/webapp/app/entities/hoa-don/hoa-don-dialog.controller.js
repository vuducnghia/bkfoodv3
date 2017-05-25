(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Hoa_donDialogController', Hoa_donDialogController);

    Hoa_donDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hoa_don', 'User'];

    function Hoa_donDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Hoa_don, User) {
        var vm = this;

        vm.hoa_don = entity;
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
            if (vm.hoa_don.id !== null) {
                Hoa_don.update(vm.hoa_don, onSaveSuccess, onSaveError);
            } else {
                Hoa_don.save(vm.hoa_don, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bkfoodv3App:hoa_donUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
