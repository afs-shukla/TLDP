(function(){
'use strict';
angular.module('users')
.controller('UsersController',UsersController);

UsersController.$inject = ['UsersService','$state','ngDialog',];

 function UsersController(UsersService,$state,ngDialog) {
 	var $uctrl=this;
 	$uctrl.message='';
    
    $uctrl.addUser=function() {
     console.log($uctrl.user)
     $uctrl.user.id=0;
    
       
     UsersService.addUser($uctrl.user)
      .then(function(response){
       console.log("Success Response:",response)
       //$ctrl.message=response.data.message;
       $uctrl.user=null;

          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'New User is added successfully',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      },function (reason){
       console.log("Error Response:",response)
       //$ctrl.message=response.data.message;
          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'Failed to add new User',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      });
 	}
 }


})();