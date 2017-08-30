(function(){
'use strict';
angular.module('apps')
.controller('AppsSearchModalView',AppsSearchModalView);

AppsSearchModalView.$inject = ['ApiPath','ngDialog','$mdDialog'];

function AppsSearchModalView(ApiPath,ngDialog,$mdDialog) {
  var $ctrl = this;
 
   $ctrl.openApplicationSearch=function($event){
  
    
      var parentEl = angular.element(document.body);
       $mdDialog.show({
         parent: parentEl,
         targetEvent: $event,
         templateUrl:' <app-search></app-search>',
             
         /*locals: { appsdtls: $ctrl.findbyid },
         controller: 'DialogController',*/
         clickOutsideToClose: true,
         escapeToClose: true
      });

   };

  

  
}
   



})();
