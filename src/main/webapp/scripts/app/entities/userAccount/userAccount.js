'use strict';

angular.module('schoolAPPApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userAccount', {
                parent: 'entity',
                url: '/userAccounts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserAccounts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userAccount/userAccounts.html',
                        controller: 'UserAccountController'
                    }
                },
                resolve: {
                }
            })
            .state('userAccount.detail', {
                parent: 'entity',
                url: '/userAccount/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserAccount'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userAccount/userAccount-detail.html',
                        controller: 'UserAccountDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UserAccount', function($stateParams, UserAccount) {
                        return UserAccount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userAccount.new', {
                parent: 'userAccount',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userAccount/userAccount-dialog.html',
                        controller: 'UserAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    userId: null,
                                    password: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userAccount', null, { reload: true });
                    }, function() {
                        $state.go('userAccount');
                    })
                }]
            })
            .state('userAccount.edit', {
                parent: 'userAccount',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userAccount/userAccount-dialog.html',
                        controller: 'UserAccountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserAccount', function(UserAccount) {
                                return UserAccount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userAccount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userAccount.delete', {
                parent: 'userAccount',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userAccount/userAccount-delete-dialog.html',
                        controller: 'UserAccountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserAccount', function(UserAccount) {
                                return UserAccount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userAccount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
