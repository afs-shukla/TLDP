(function () {
"use strict";

angular.module('public')
.component('deployDisplay', {
  templateUrl: 'src/apps/app-view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppsController
});

AppsController.$inject = ['ApiPath'];
function AppsController(ApiPath) {
  var $ctrl = this;
  $ctrl.addapplication=function (){
  	console.log($ctrl.app)
  }
}

})();
