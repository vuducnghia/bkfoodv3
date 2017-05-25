(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Mon_an_da_muaDetailController', Mon_an_da_muaDetailController);

    Mon_an_da_muaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mon_an_da_mua', 'User'];

    function Mon_an_da_muaDetailController($scope, $rootScope, $stateParams, previousState, entity, Mon_an_da_mua, User) {
        var vm = this;

        vm.mon_an_da_mua = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bkfoodv3App:mon_an_da_muaUpdate', function(event, result) {
            vm.mon_an_da_mua = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
