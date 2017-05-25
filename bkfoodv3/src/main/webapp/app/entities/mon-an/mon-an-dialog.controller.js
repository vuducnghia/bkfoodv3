(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_anDialogController', Mon_anDialogController);

    Mon_anDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Mon_an'];

    function Mon_anDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Mon_an) {
        var vm = this;

        vm.mon_an = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mon_an.id !== null) {
                Mon_an.update(vm.mon_an, onSaveSuccess, onSaveError);
            } else {
                Mon_an.save(vm.mon_an, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bkfoodv3App:mon_anUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        vm.setHinh_anh = function ($file, mon_an) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        mon_an.hinh_anh = base64Data;
                        mon_an.hinh_anhContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
