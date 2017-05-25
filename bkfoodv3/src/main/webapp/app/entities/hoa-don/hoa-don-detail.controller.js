(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('Hoa_donDetailController', Hoa_donDetailController);

    Hoa_donDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Hoa_don', 'User'];

    function Hoa_donDetailController($scope, $rootScope, $stateParams, previousState, entity, Hoa_don, User) {
        var vm = this;

        vm.hoa_don = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bkfoodv3App:hoa_donUpdate', function(event, result) {
            vm.hoa_don = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
