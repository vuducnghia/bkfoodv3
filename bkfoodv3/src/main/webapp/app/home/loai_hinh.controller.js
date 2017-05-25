(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('LoaiHinhController', LoaiHinhController);

    LoaiHinhController.$inject = ['$scope', 'Mon_ans', 'MonAnDate'];

    function LoaiHinhController ($scope, Mon_ans, MonAnDate) {
        var vm = this;
        vm.monAns = [];
        vm.search = search;

        function search(){
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MonAnSearchTest.query({ten: vm.searchQuery}, function(result) {
                vm.monAns = result;
            });
        }
    }
})();
