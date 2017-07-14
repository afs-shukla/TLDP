(function () {
"use strict";

angular.module('public')
.component('appExtrainfo', {
  templateUrl: 'src/dashboard/app-extrainfo.view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppExtraInfoController
});

AppExtraInfoController.$inject = ['ApiPath'];
function AppExtraInfoController(ApiPath) {
  var $ctrl = this;
  // $ctrl.searchapps=function (){
  // 	console.log($ctrl.searchparams)
  // }
}

})();