(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('HotController', HotController);

    HotController.$inject = ['$scope', 'Mon_ans', '$stateParams', '$rootScope'];

    function HotController ($scope, Mon_ans, $stateParams, $rootScope) {
        var vm = this;
//        $scope.$on('nghia', function(event, data){
//        //                vm.username = user;
//                vm.username = 'ducm';
//                console.log(data);
//        });


    }
})();
