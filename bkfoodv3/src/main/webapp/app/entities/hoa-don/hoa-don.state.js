(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hoa-don', {
            parent: 'entity',
            url: '/hoa-don',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.hoa_don.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hoa-don/hoa-dons.html',
                    controller: 'Hoa_donController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hoa_don');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('hoa-don-detail', {
            parent: 'hoa-don',
            url: '/hoa-don/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.hoa_don.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hoa-don/hoa-don-detail.html',
                    controller: 'Hoa_donDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('hoa_don');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Hoa_don', function($stateParams, Hoa_don) {
                    return Hoa_don.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'hoa-don',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('hoa-don-detail.edit', {
            parent: 'hoa-don-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hoa-don/hoa-don-dialog.html',
                    controller: 'Hoa_donDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Hoa_don', function(Hoa_don) {
                            return Hoa_don.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hoa-don.new', {
            parent: 'hoa-don',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hoa-don/hoa-don-dialog.html',
                    controller: 'Hoa_donDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ten_mon_an: null,
                                sale: null,
                                gia_tien: null,
                                so_luong: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hoa-don', null, { reload: 'hoa-don' });
                }, function() {
                    $state.go('hoa-don');
                });
            }]
        })
        .state('hoa-don.edit', {
            parent: 'hoa-don',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hoa-don/hoa-don-dialog.html',
                    controller: 'Hoa_donDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Hoa_don', function(Hoa_don) {
                            return Hoa_don.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hoa-don', null, { reload: 'hoa-don' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hoa-don.delete', {
            parent: 'hoa-don',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hoa-don/hoa-don-delete-dialog.html',
                    controller: 'Hoa_donDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Hoa_don', function(Hoa_don) {
                            return Hoa_don.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hoa-don', null, { reload: 'hoa-don' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
