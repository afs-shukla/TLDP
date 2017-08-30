
(function() {
"use strict";
/**
 * app module for magaging the apps 
 */
var dashboard=angular.module('dashboard', ['deployment','apps','ui.router', 'common','smart-table','ngMaterial'])

dashboard.config(routeConfig);

/**'
 * Configures the routes and views
 */
routeConfig.$inject = ['$stateProvider'];
function routeConfig ($stateProvider) {
  // Routes
  $stateProvider
    .state('dashboard', {
      abstract: true,
      templateUrl: 'src/dashboard/dashboard.html'
    })
    .state('dashboard.home', {
      url: '/dashboard.home',
      templateUrl: 'src/dashboard/dashboard.home.html',
      controller: 'DeploymentController',
      controllerAs: '$dctrl'
    })
   ;
}

})();

