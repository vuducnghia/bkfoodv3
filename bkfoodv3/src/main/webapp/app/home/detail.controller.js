(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('DetailController', DetailController);

    DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Mon_an'];

    function DetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Mon_an) {
        var vm = this;

        vm.monAn = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bkfoodApp:mon_anUpdate', function(event, result) {
            vm.mon_an = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
