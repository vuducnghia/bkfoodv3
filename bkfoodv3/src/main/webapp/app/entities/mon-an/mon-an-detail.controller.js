(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_anDetailController', Mon_anDetailController);

    Mon_anDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Mon_an'];

    function Mon_anDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Mon_an) {
        var vm = this;

        vm.mon_an = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bkfoodv3App:mon_anUpdate', function(event, result) {
            vm.mon_an = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
