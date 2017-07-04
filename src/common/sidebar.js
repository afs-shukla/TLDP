(function () {
"use strict";

angular.module('common')
.component('sidebar', {
  templateUrl: 'src/common/sidebar-view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: SidebarController
});

SidebarController.$inject = ['ApiPath'];
function SidebarController(ApiPath) {
  var $ctrl = this;
  // $ctrl.searchapps=function (){
  // 	console.log($ctrl.searchparams)
  // }
}

})();