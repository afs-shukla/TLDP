(function () {
"use strict";

angular.module('apps')
.component('appDetails', {
  templateUrl: 'src/apps/apps-details.modal.view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppDetailsController
});

AppDetailsController.$inject = ['ApiPath'];
function AppDetailsController(ApiPath) {
  var $ctrl = this;
  // $ctrl.searchapps=function (){
  // 	console.log($ctrl.searchparams)
  // }
}

})();