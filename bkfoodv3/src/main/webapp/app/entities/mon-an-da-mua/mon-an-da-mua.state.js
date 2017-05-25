(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mon-an-da-mua', {
            parent: 'entity',
            url: '/mon-an-da-mua',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.mon_an_da_mua.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-muas.html',
                    controller: 'Mon_an_da_muaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mon_an_da_mua');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mon-an-da-mua-detail', {
            parent: 'mon-an-da-mua',
            url: '/mon-an-da-mua/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.mon_an_da_mua.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-mua-detail.html',
                    controller: 'Mon_an_da_muaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mon_an_da_mua');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mon_an_da_mua', function($stateParams, Mon_an_da_mua) {
                    return Mon_an_da_mua.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mon-an-da-mua',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mon-an-da-mua-detail.edit', {
            parent: 'mon-an-da-mua-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-mua-dialog.html',
                    controller: 'Mon_an_da_muaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mon_an_da_mua', function(Mon_an_da_mua) {
                            return Mon_an_da_mua.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mon-an-da-mua.new', {
            parent: 'mon-an-da-mua',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-mua-dialog.html',
                    controller: 'Mon_an_da_muaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ten_mon_an: null,
                                so_luong: null,
                                sale: null,
                                gia_tien: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mon-an-da-mua', null, { reload: 'mon-an-da-mua' });
                }, function() {
                    $state.go('mon-an-da-mua');
                });
            }]
        })
        .state('mon-an-da-mua.edit', {
            parent: 'mon-an-da-mua',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-mua-dialog.html',
                    controller: 'Mon_an_da_muaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mon_an_da_mua', function(Mon_an_da_mua) {
                            return Mon_an_da_mua.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mon-an-da-mua', null, { reload: 'mon-an-da-mua' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mon-an-da-mua.delete', {
            parent: 'mon-an-da-mua',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an-da-mua/mon-an-da-mua-delete-dialog.html',
                    controller: 'Mon_an_da_muaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mon_an_da_mua', function(Mon_an_da_mua) {
                            return Mon_an_da_mua.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mon-an-da-mua', null, { reload: 'mon-an-da-mua' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
