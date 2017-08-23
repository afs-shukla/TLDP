(function(){
'use strict';
angular.module('apps')
.controller('AddingNewAppController',AddingNewAppController);

AddingNewAppController.$inject = ['AppsService','$state'];

 function AddingNewAppController(AppsService,$state) {
 	var $ctrl=this;

 	$ctrl.addNewApp=function() {
     console.log($ctrl.app)
     $ctrl.app.id=0;
     AppsService.addApp($ctrl.app);
 	}
 }


})();