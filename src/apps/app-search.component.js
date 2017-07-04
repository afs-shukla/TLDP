(function () {
"use strict";

angular.module('public')
.component('appSearch', {
  templateUrl: 'src/apps/app-search.view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppSearchController
});

AppSearchController.$inject = ['ApiPath'];
function AppSearchController(ApiPath) {
  var $ctrl = this;
  $ctrl.searchapps=function (){
  	console.log($ctrl.searchparams)
  }
}

})();