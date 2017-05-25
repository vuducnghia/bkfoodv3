(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope', 'MonAnSearchTest', 'Mon_an', '$stateParams', 'Hoa_don_search', 'Hoa_don', 'entity', 'UserCurrent'];

    function SearchController ($scope, MonAnSearchTest, Mon_an, $stateParams, Hoa_don_search, Hoa_don, entity, UserCurrent) {
        var vm = this;
        vm.monAns = [];
        vm.hoa_don = entity;
        vm.TruyenTenMonAn = TruyenTenMonAn;
        search();
        function search(){
            UserCurrent.query(function(result){
                vm.vu = result;
            });
            if($stateParams.datasearch){
                MonAnSearchTest.query({ten: $stateParams.datasearch}, function(result) {
                    vm.monAns = result;
                    if(vm.monAns.length == 0)
                        vm.no_data_search = 'Không có dữ liệu phù hợp';
                });
            }
        }
        function TruyenTenMonAn(ten, gia_tien, sale){
            vm.hoa_don.ten_mon_an = ten;
            vm.hoa_don.gia_tien = gia_tien;
            vm.hoa_don.sale = sale;
            vm.hoa_don.so_luong = 1;
            search1(ten);
        }
        function save () {

            if(vm.vu != null){
                vm.hoa_don.user = vm.vu;
                Hoa_don.save(vm.hoa_don);
            }

        }
        function search1(ten){
            Hoa_don_search.query({ten : ten}, function(result){
                if(result.length == 0){
                    save();
                    $scope.$emit('dem', 1);
                }
            })
        }
    }
})();
