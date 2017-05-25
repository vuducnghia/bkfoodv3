(function() {
    'use strict';

    angular
        .module('bkfoodv3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mon-an', {
            parent: 'entity',
            url: '/mon-an',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.mon_an.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mon-an/mon-ans.html',
                    controller: 'Mon_anController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mon_an');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mon-an-detail', {
            parent: 'mon-an',
            url: '/mon-an/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bkfoodv3App.mon_an.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mon-an/mon-an-detail.html',
                    controller: 'Mon_anDetailController',
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
        .state('mon-an-detail.edit', {
            parent: 'mon-an-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an/mon-an-dialog.html',
                    controller: 'Mon_anDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mon_an', function(Mon_an) {
                            return Mon_an.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mon-an.new', {
            parent: 'mon-an',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an/mon-an-dialog.html',
                    controller: 'Mon_anDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ten: null,
                                gia_tien: null,
                                the_loai: null,
                                date: null,
                                hinh_anh: null,
                                hinh_anhContentType: null,
                                sale: null,
                                link_comment_facebook: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mon-an', null, { reload: 'mon-an' });
                }, function() {
                    $state.go('mon-an');
                });
            }]
        })
        .state('mon-an.edit', {
            parent: 'mon-an',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an/mon-an-dialog.html',
                    controller: 'Mon_anDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mon_an', function(Mon_an) {
                            return Mon_an.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mon-an', null, { reload: 'mon-an' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mon-an.delete', {
            parent: 'mon-an',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mon-an/mon-an-delete-dialog.html',
                    controller: 'Mon_anDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mon_an', function(Mon_an) {
                            return Mon_an.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mon-an', null, { reload: 'mon-an' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
