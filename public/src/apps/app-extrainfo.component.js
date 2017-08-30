(function () {
"use strict";

angular.module('apps')
.component('appExtrainfo', {
  templateUrl: 'src/apps/app-extrainfo.view.html',
   bindings: {
     jiraDetails: '<',
     svnDetails: '<',
     sonarDetails: '<',
     testDetails: '<'
   },

  controller: AppExtraInfoController
});

AppExtraInfoController.$inject = ['ApiPath'];
function AppExtraInfoController(ApiPath) {
  var $ctrl = this;
  
}

})();