(function() {
'use strict';

angular.module('main')
.config(routeConfig);

/**
 * Configures the routes and views
 */
routeConfig.$inject = ['$stateProvider'];
function routeConfig ($stateProvider) {
  
  $stateProvider
    .state('main.home', {
      url: '/',
      templateUrl: 'src/public/main.home.html'
    })
    
}
})();
