(function() {
"use strict";
/**
 * app module for magaging the apps 
 */
var appmodule=angular.module('apps', ['ngDialog','ui.router', 'common','smart-table'])

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
       templateUrl: 'src/apps/apps.home.html'
     })
     .state('apps.new', {
       url: '/apps-new',
       templateUrl: 'src/apps/apps.new-form.html',
       controller: 'AddingNewAppController',
        controllerAs: '$ctrl'
     })
     ;
}

})();

