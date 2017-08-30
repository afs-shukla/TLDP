(function(){
'use strict';
/*angular.module('apps')
.controller('AppsDialogViewController',AppsDialogViewController);

AppsDialogViewController.$inject = ['$scope','AppsService','$state','ngDialog','$mdDialog','appsdtls'];

 function AppsDialogViewController($scope,AppsService,$state,ngDialog,$mdDialog,appsdtls) {
    var $dctrl=this;

    $dctrl.app=appsdtls

    console.log("!!!  $dctrl.app",$dctrl.app)
     console.log("!!!  appsdtls",appsdtls)
 	
 
 

 }*/

 angular.module('apps')
.controller('DialogController',DialogController);
DialogController.$inject = ['$scope','$mdDialog','appsdtls'];
   function DialogController($scope, $mdDialog, appsdtls) {
        $scope.items = appsdtls;

        console.log("scope item:",$scope.items)
        $scope.closeDialog = function() {
          $mdDialog.hide();
        }
      }

 


})();