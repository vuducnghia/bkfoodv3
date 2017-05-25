(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'Auth', '$state', 'Mon_an', 'Hoa_don', 'Hoa_don_search_ByUser', 'UserCurrent'];

    function HomeController ($scope, Principal, LoginService, Auth, $state, Mon_an, Hoa_don, Hoa_don_search_ByUser, UserCurrent) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.logout = logout;
        $state.go('all');
        vm.mon_ans = [];
        vm.search = search;
        username();
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }
        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
        function search(){
            if(vm.searchQuery){
                $state.go('search', {datasearch : vm.searchQuery});
            }
        }
        function loadAll(){
            vm.v = vm.name;
            Hoa_don_search_ByUser.query({user : vm.v}, function(result) {
                if(result.length > 0)
                    vm.count = result.length;
                else
                    vm.count = 0;
            })
        }
        vm.count = 0;
        $scope.$on('dem', function(i){
            vm.count += 1;
        })

        function username(){
            UserCurrent.query(function(result){
                vm.name = result.login;
                loadAll();
            });
        }

    }
})();
