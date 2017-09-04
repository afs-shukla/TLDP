(function() {
"use strict";
/**
 * app module for magaging the apps 
 */
var appmodule=angular.module('deployment', ['apps','ngDialog','ui.router', 'common','smart-table','ngMaterial'])

appmodule.config(routeConfig);

/**
 * Configures the routes and views
 */
routeConfig.$inject = ['$stateProvider'];
function routeConfig ($stateProvider) {
  // Routes
  $stateProvider
    .state('dployment', {
      abstract: true,
      templateUrl: 'src/deployment/deployment.html'
    })
     .state('dployment.home', {
       url: '/dployment.home',
       templateUrl: 'src/deployment/deployment.home.html',
       controller: 'DeploymentController',
       controllerAs: '$dctrl'
     
     })
     .state('dployment.new', {
       url: '/dployment.new',
       templateUrl: 'src/deployment/deployment.new.html',
       controller: 'DeploymentController',
       controllerAs: '$dctrl'
     })
      .state('dployment.releasenote', {
       url: '/dployment.new',
       templateUrl: 'src/deployment/deployment.new.html',
       controller: 'DeploymentController',
       controllerAs: '$dctrl'
     })
     
     ;
}

})();
