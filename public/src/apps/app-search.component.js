(function () {
"use strict";

angular.module('apps')
.component('appSearch', {
templateUrl: 'src/apps/app-search.view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppSearchController
});

AppSearchController.$inject = ['AppsService'];
function AppSearchController(AppsService) {
  var $ctrl = this;
  $ctrl.errormsg=""
  $ctrl.appslist=[];
  $ctrl.searchapps=function (){
  	console.log($ctrl.searchparams)
  	var appsPromise=AppsService.findApps($ctrl.searchparams)
       appsPromise.then(function(response){
       	console.log("Success Response:"+response.data);
       	return response.data

       },function(reason){
       	  console.log("Error Reason:"+reason);
       	  $ctrl.errormsg=reason;
       })
   
  }
}

})();