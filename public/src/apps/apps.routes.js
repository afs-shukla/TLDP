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
      templateUrl: 'src/dashboard/apps.html'
    })
    // .state('apps.dashboardhome', {
    //   url: '/',
    //   templateUrl: 'src/dashboard/apps.dashboard.home.html'
    // })
   ;
}
})();
