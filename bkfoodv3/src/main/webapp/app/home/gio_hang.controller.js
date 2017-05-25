(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('GiohangController', GiohangController);

    GiohangController.$inject = ['$scope', '$stateParams', 'Mon_an', '$uibModalInstance', 'Hoa_don', 'HoaDons', 'Mon_an_da_mua','Hoa_don_search_ByUser', 'UserCurrent', 'TinhTien'];

    function GiohangController ($scope, $stateParams, Mon_an, $uibModalInstance, Hoa_don, HoaDons, Mon_an_da_mua, Hoa_don_search_ByUser, UserCurrent, TinhTien) {
        var vm = this;
        vm.clear = clear;
        vm.reset = reset;
        vm.cong = 0;
        vm.HoaDons = [];
        vm.add = add;
        vm.decrease = decrease;
        username();
        vm.ThanhToan = ThanhToan;
//        vm.MonAnDaMua = MonAnDaMua;
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        function username(){
            UserCurrent.query(function(result){
                vm.name = result.login;
                loadAll();
            });
        }
        function loadAll() {
            Hoa_don_search_ByUser.query({user : vm.name}, function(result) {
                vm.HoaDons = result;
                CongTien();
            });
        }
        function add(so_luong, hoa_don){
            hoa_don.so_luong ++;
            save(hoa_don);
            CongTien();
        }
        function decrease(so_luong, hoa_don){
            if(so_luong > 0){
                hoa_don.so_luong --;
                save(hoa_don);
            }
            if(hoa_don.so_luong == 0){
                for(var i = 0; i < vm.HoaDons.length; ++i)
                    if(vm.HoaDons[i] == hoa_don){
                        vm.HoaDons.splice(i,1);
                        break;
                    }
                confirmDelete(hoa_don.id);
            }
            CongTien();
        }
        function CongTien (){
//             vm.cong = 0;
             TinhTien.get({user : 'admin'}, function(result) {
                vm.cong = result.message;
             });
        }
        function reset(){
            var n = vm.HoaDons.length;
            for(var i = 0; i < n ; ++i)
                confirmDelete(vm.HoaDons[i].id);
            vm.HoaDons.splice(0,n);
            CongTien();
        }
        function save (hoa_don) {
            Hoa_don.update(hoa_don);
        }
        function confirmDelete (id) {
            Hoa_don.delete({id: id});
        }
        function ThanhToan(){
            var n = vm.HoaDons.length;
            for(var i = 0; i < n ; ++i)
                Mon_an_da_mua.update(vm.HoaDons[i]);
            reset();
        }

    }
})();
