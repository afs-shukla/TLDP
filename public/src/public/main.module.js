(function() {
"use strict";

/**
 * Restaurant module that includes the public module as a dependency
 */
angular.module('main', ['admin','apps','dashboard','ngResource'])
.config(config);

config.$inject = ['$urlRouterProvider'];
function config($urlRouterProvider) {

  // If user goes to a path that doesn't exist, redirect to public root
  $urlRouterProvider.otherwise('/');
}

})();