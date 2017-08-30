(function () {
"use strict";

angular.module('apps')
.component('appView', {
  templateUrl: 'src/apps/apps.new-form.html',
   bindings: {
     appData: '<',
     viewMode:'<'
   },

  controller: AppViewController
});

AppViewController.$inject = ['ApiPath'];

function AppViewController(ApiPath) {
  var $ctrl = this;
  $ctrl.app=$ctrl.appData;
  console.log("View Controller",$ctrl.viewMode)
 
}

})();