(function() {
"use strict";

/**
 * Restaurant module that includes the public module as a dependency
 */
var mainapp=angular.module('main', ['ui.router','common','admin','apps','dashboard','ngResource']);
mainapp.config(['$stateProvider','$urlRouterProvider', function ($stateProvider,$urlRouterProvider){

  $stateProvider
  .state('home', {
      abstract: true,
      templateUrl: 'src/public/home.html'
    })
 
    .state('home.main', {
      url: '/',
      templateUrl: 'src/public/main.home.html'
    });

   $urlRouterProvider.otherwise('/');

}]);
// routeConfig.$inject = ['$stateProvider'];
// routeConfig.$inject = ['$urlRouterProvider'];

/*function routeConfig ($stateProvider,$urlRouterProvider) {
   

  $stateProvider
  .state('home', {
      abstract: true,
      templateUrl: 'src/public/home.html'
    })
 
    .state('main.home', {
      url: '/',
      templateUrl: 'src/public/main.home.html'
    });

   $urlRouterProvider.otherwise('/');

   
    
}*/

})();