(function() {
"use strict";
/**
 * app module for magaging the apps 
 */
var appmodule=angular.module('users', ['deployment','apps','ngDialog','ui.router', 'common','smart-table'])

appmodule.config(routeConfig);

/**
 * Configures the routes and views
 */
routeConfig.$inject = ['$stateProvider'];
function routeConfig ($stateProvider) {
  // Routes
  $stateProvider
    .state('users', {
      abstract: true,
      templateUrl: 'src/users/users.html'
    })
     .state('users.home', {
       url: '/users.home',
       templateUrl: 'src/users/users.home.html'
     })
     .state('users.new', {
       url: '/users.new',
       templateUrl: 'src/users/users.new.html',
       controller: 'UsersController',
        controllerAs: '$uctrl'
     })
     
     ;
}

})();
