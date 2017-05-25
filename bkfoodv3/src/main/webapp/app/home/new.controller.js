(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('NewController', NewController);

    NewController.$inject = ['$scope', 'Mon_ans', 'MonAnDate', 'Hoa_don', 'entity', 'Hoa_don_search', 'UserCurrent'];

    function NewController ($scope, Mon_ans, MonAnDate, Hoa_don, entity, Hoa_don_search, UserCurrent) {
        var vm = this;
        vm.monAns = [];
        vm.hoa_don = entity;
        loadAll();
        vm.TruyenTenMonAn = TruyenTenMonAn;
        function loadAll() {
            MonAnDate.query(function(result) {
                vm.monAns = result;
            });
            UserCurrent.query(function(result){
                vm.vu = result;
            });
        }
        function TruyenTenMonAn(ten, gia_tien, sale){
            vm.hoa_don.ten_mon_an = ten;
            vm.hoa_don.gia_tien = gia_tien;
            vm.hoa_don.sale = sale;
            vm.hoa_don.so_luong = 1;
            vm.hoa_don.user = vm.vu;
            if(vm.vu != null){

                search(ten);
            }
        }
        function save () {
            Hoa_don.save(vm.hoa_don);
        }
        function search(ten){
            Hoa_don_search.query({ten : ten}, function(result){
                if(result.length == 0){
                    save();
                    $scope.$emit('dem', 1);
                }
                else{
                    var t = true;
                    for(var i = 0; i < result.length; ++i)
                        if(result[i].login == vm.vu.login && result[i].ten_mon_an == vm.hoa_don.ten_mon_an)
                            t = false;
                    if(t == true){
                    $scope.$emit('dem', 1);
                    save();}
                }
            })
        }
    }
})();
