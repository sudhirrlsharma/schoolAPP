'use strict';

angular.module('schoolAPPApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contect', {
                parent: 'entity',
                url: '/contects',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Contects'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contect/contects.html',
                        controller: 'ContectController'
                    }
                },
                resolve: {
                }
            })
            .state('contect.detail', {
                parent: 'entity',
                url: '/contect/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Contect'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contect/contect-detail.html',
                        controller: 'ContectDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Contect', function($stateParams, Contect) {
                        return Contect.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contect.new', {
                parent: 'contect',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contect/contect-dialog.html',
                        controller: 'ContectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    phone: null,
                                    email: null,
                                    im: null,
                                    address1: null,
                                    adress2: null,
                                    city: null,
                                    state: null,
                                    country: null,
                                    pinCode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contect', null, { reload: true });
                    }, function() {
                        $state.go('contect');
                    })
                }]
            })
            .state('contect.edit', {
                parent: 'contect',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contect/contect-dialog.html',
                        controller: 'ContectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contect', function(Contect) {
                                return Contect.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contect', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contect.delete', {
                parent: 'contect',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contect/contect-delete-dialog.html',
                        controller: 'ContectDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contect', function(Contect) {
                                return Contect.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contect', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
