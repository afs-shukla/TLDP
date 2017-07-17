(function(){
"use strict";
angular.module('apps')
.service('AppsService', AppsService);



AppsService.$inject = ['$http', 'ApiPath'];
function AppsService($http, ApiPath) {
  var appsService = this;

  appsService.findApps = function (searchparams) {
    console.log("AppsService:"+searchparams.appid)
    return $http.get(ApiPath + '/apps',
    	{
    		params:{appid:searchparams.appid, appname:searchparams.appname, desc:searchparams.desc }
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

}




})();
