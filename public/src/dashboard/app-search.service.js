(function(){
"use strict";
angular.module('public')
.service('AppSearchService', AppSearchService);



AppSearchService.$inject = ['$http', 'ApiPath'];
function AppSearchService($http, ApiPath) {
  var appsService = this;

  appsService.findApps = function (appid,appname,desc) {
    return $http.get(ApiPath + '/findApps',
    	{
    		params:{appid:appid, appname:appname, desc:desc }
    	})
  };

  appsService.findAppById = function (appid) {
    return $http.get(ApiPath + '/findAppById',
    	{
    		params:{appid:appid}
    	})
  };

   appsService.removeAppById = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

  appsService.removeAppById = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

   appsService.addApp = function (appdata) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

  appsService.Updatepp = function (appdata) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };






})();
