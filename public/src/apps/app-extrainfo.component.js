(function () {
"use strict";

angular.module('apps')
.component('appExtrainfo', {
  templateUrl: 'src/apps/app-extrainfo.view.html',
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