(function(){
'use strict';
angular.module('apps')
.controller('AddingNewAppController',AddingNewAppController);

AddingNewAppController.$inject = ['AppsService','$state','ngDialog'];

 function AddingNewAppController(AppsService,$state,ngDialog) {
 	var $ctrl=this;
 	$ctrl.message='';

 	$ctrl.addNewApp=function() {
     console.log($ctrl.app)
     $ctrl.app.id=0;
     AppsService.addApp($ctrl.app)
      .then(function(response){
       console.log("Success Response:",response)
       $ctrl.message=response.data.message;
       $ctrl.app=null;
       ngDialog.open({
    		template: '<p>Added Successfully</p>'
    		
			});
      },function (reason){
       console.log("Error Response:",response)
       $ctrl.message=response.data.message;
      });
 	}
 }


})();