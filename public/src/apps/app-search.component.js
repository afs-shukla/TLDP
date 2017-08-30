(function () {
"use strict";

angular.module('apps')
.component('appSearch', {
templateUrl: 'src/apps/apps.search.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppSearchController
});

AppSearchController.$inject = ['AppsService','$state'];
function AppSearchController(AppsService,$state) {
  var $ctrl = this;
  $ctrl.errormsg=""
  $ctrl.appsList=[];


  
  $ctrl.searchapps=function (){
    $ctrl.errormsg="";
  	console.log($ctrl.searchparams)
  	var appsPromise=AppsService.searchApps($ctrl.searchparams)
       appsPromise.then(
        function(response){
       	console.log("Success Response:"+response.data.appslist);
       	$ctrl.appsList=response.data.appslist;
        console.log("appslist",$ctrl.appsList);
        $ctrl.searchparams=null;

       },function(reason){
       	  console.log("Error Reason:"+reason);
       	  $ctrl.errormsg=reason.data.message;
             ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'Error While getting the applicaitons ',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

       })
   
  };

  $ctrl.addnew= function() {
    console.log("Adding new Apps");
    $state.go('apps.new');
  };

  
}

})();