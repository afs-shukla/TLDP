(function(){
"use strict";
angular.module('deployment')
.service('ReleaseNoteService', ReleaseNoteService);



ReleaseNoteService.$inject = ['$http', 'ApiPath'];
function ReleaseNoteService($http, ApiPath) {
  var relNoteService = this;

  relNoteService.createReleaseNote  = function (releaseNote) {
    console.log("ReleaseNoteService:",releaseNote)
    return $http.post(ApiPath + '/createReleaseNote',releaseNote)
  };
 
}




})();
