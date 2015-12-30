'use strict';

angular.module('schoolAPPApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organization', {
                parent: 'entity',
                url: '/organizations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Organizations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organizations.html',
                        controller: 'OrganizationController'
                    }
                },
                resolve: {
                }
            })
            .state('organization.detail', {
                parent: 'entity',
                url: '/organization/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Organization'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organization-detail.html',
                        controller: 'OrganizationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Organization', function($stateParams, Organization) {
                        return Organization.get({id : $stateParams.id});
                    }]
                }
            })
            .state('organization.new', {
                parent: 'organization',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/organization/organization-dialog.html',
                        controller: 'OrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    orgType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('organization', null, { reload: true });
                    }, function() {
                        $state.go('organization');
                    })
                }]
            })
            .state('organization.edit', {
                parent: 'organization',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/organization/organization-dialog.html',
                        controller: 'OrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('organization.delete', {
                parent: 'organization',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/organization/organization-delete-dialog.html',
                        controller: 'OrganizationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Organization', function(Organization) {
                                return Organization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('organization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
