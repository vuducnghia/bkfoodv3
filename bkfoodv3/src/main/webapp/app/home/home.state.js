(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },

            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('new', {
            parent: 'home',
            url: 'new',
            data: {
                authorities: []
            },

            templateUrl: 'app/home/new.html',
            controller: 'NewController',
            controllerAs: 'vm',
            resolve: {
                Mon_ans: ['MonAnDate',function(MonAnDate){
                    return MonAnDate.query().$promise;
                }],
                entity: function () {
                    return {
                        ten_mon_an: null,
                        id: null
                    };
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('all', {
            parent: 'home',
            url: 'all',
            data: {
                authorities: []
            },

            templateUrl: 'app/home/all.html',
            controller: 'AllController',
            controllerAs: 'vm',

            resolve: {
                Mon_ans: ['Mon_an',function(Mon_an){
                    return Mon_an.query().$promise;
                }],
                entity: function () {
                    return {
                        ten_mon_an: null,
                        sale: null,
                        gia_tien: null,
                        so_luong: null,
                        id: null

                    };
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('hot', {
            parent: 'home',
            url: 'hot',
            data: {
                authorities: []
            },

            templateUrl: 'app/home/hot.html',
            controller: 'HotController',
            controllerAs: 'vm',

            resolve: {
                Mon_ans: ['Mon_an',function(Mon_an){
                    return Mon_an.query().$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('search', {
            parent: 'home',
            url: 'search',
            data: {
                authorities: []
            },

            templateUrl: 'app/home/search.html',
            controller: 'SearchController',
            controllerAs: 'vm',
            params: {
                datasearch : null
            },
            resolve: {
                entity: function () {
                    return {
                        ten_mon_an: null,
                        id: null
                    };
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('detail_mon-an', {
            parent: 'mon-an',
            url: '/mon-an/{id}',
            data: {
                authorities: [],
                pageTitle: 'bkfoodApp.mon_an.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/detail.html',
                    controller: 'DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mon_an');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mon_an', function($stateParams, Mon_an) {
                    return Mon_an.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mon-an',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('loai_hinh', {
            parent: 'home',
            url: 'loai-hinh',
            data: {
                authorities: []
            },

            templateUrl: 'app/home/new.html',
            controller: 'LoaiHinhController',
            controllerAs: 'vm',

            resolve: {
                Mon_ans: ['Mon_an',function(Mon_an){
                    return Mon_an.query().$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('gio_hang', {
            parent: 'all',
            url: '/Gio-hang',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/home/gio_hang.html',
                    controller: 'GiohangController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'man_hinh',  //trong style giohang.html
                    resolve: {
                        HoaDons: ['Hoa_don',function(Hoa_don){
                            return Hoa_don.query().$promise;
                        }],
                    }
                }).result.then(function() {
                    $state.go('hoa-don', null, { reload: 'hoa-don' });
                }, function() {
                    $state.go('^');
                });
            }]

        })
        ;
    }
})();
