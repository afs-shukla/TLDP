(function() {
"use strict";
/**
 * app module for magaging the apps 
 */
var appmodule=angular.module('apps', ['ui.router', 'common','smart-table'])

appmodule.config(routeConfig);

/**
 * Configures the routes and views
 */
routeConfig.$inject = ['$stateProvider'];
function routeConfig ($stateProvider) {
  // Routes
  $stateProvider
    .state('apps', {
      abstract: true,
      templateUrl: 'src/apps/apps.html'
    })
     .state('apps.home', {
       url: '/appshome',
       templateUrl: '<app-search></app-search>'
     })
   ;
}

})();

