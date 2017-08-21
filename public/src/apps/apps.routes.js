(function() {
'use strict';

angular.module('apps')
.config(routeConfig);

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
