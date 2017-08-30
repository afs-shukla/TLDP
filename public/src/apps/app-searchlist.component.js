(function () {
"use strict";

angular.module('apps')
.component('searchList', {
  templateUrl: 'src/apps/app-searchlist.view.html',
   bindings: {
    appDetails: '<'
   },

  controller: AppsSearchListController
});

AppsSearchListController.$inject = ['ApiPath','ngDialog','$mdDialog'];

function AppsSearchListController(ApiPath,ngDialog,$mdDialog) {
  var $ctrl = this;
 
   $ctrl.viewAppDetails=function(targetId,$event){
    console.log("targetId",targetId);
    $ctrl.findbyid=findElement($ctrl.appDetails,"id",targetId);
    console.log("findbyid", $ctrl.findbyid);
    
      var parentEl = angular.element(document.body);
       $mdDialog.show({
         parent: parentEl,
         targetEvent: $event,
         templateUrl:'src/apps/apps-details.modal.view.html',
             
         locals: { appsdtls: $ctrl.findbyid },
         controller: 'DialogController',
         clickOutsideToClose: true,
         escapeToClose: true
      });

   };

   function findElement(arr, propName, propValue) {
  for (var i=0; i < arr.length; i++)
    if (arr[i][propName] == propValue)
      return arr[i];

  
}
   console.log("Applist",$ctrl.appDetails);

}

})();
