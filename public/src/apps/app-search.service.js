(function(){
"use strict";
angular.module('public')
.service('AppSearchService', AppSearchService);



AppSearchService.$inject = ['$http', 'ApiPath'];
function AppSearchService($http, ApiPath) {
  var searchservice = this;

  searchservice.findApps = function (appid,appname,desc) {
    return $http.get(ApiPath + '/findApps',{params:{appid:appid, appname:appname, desc:desc }}).then(function (response) {
      return response.data;
    });
  };



})();
