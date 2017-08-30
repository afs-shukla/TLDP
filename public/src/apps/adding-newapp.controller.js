(function(){
'use strict';
angular.module('apps')
.controller('AddingNewAppController',AddingNewAppController);

AddingNewAppController.$inject = ['AppsService','$state','ngDialog'];

 function AddingNewAppController(AppsService,$state,ngDialog) {
 	var $ctrl=this;
 	$ctrl.message='';
 
  console.log("$ctrl.app",$ctrl.app)
 	$ctrl.addNewApp=function() {
     console.log($ctrl.app)
     $ctrl.app.id=0;
       
     AppsService.addApp($ctrl.app)
      .then(function(response){
       console.log("Success Response:",response)
       //$ctrl.message=response.data.message;
       $ctrl.app=null;

          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'New Application is added successfully',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      },function (reason){
       console.log("Error Response:",response)
       //$ctrl.message=response.data.message;
          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'Failed to add new application',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      });
 	}

 	$ctrl.searchApp=function(){

 	}

 }


})();