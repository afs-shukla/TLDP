(function(){
"use strict";
angular.module('deployment')
.service('DeployService', DeployService);



DeployService.$inject = ['$http', 'ApiPath'];
function DeployService($http, ApiPath) {
  var depService = this;

  depService.searchDeployments  = function (searchparams) {
    console.log("DeployService:",searchparams)
    return $http.get(ApiPath + '/searchDeployments'+'/'+searchparams.depNumber+'/'+
    	searchparams.appId+'/'+searchparams.userId+'/'+searchparams.depDate+'/'+searchparams.depStatus+
    	'/'+searchparams.fixVersion)
  };


   depService.addDeployment = function (dep) {
    console.log("In DeployService ",dep)
    return $http.post(ApiPath + '/addDeployment', dep)
    	
  };

   depService.searchDeploymentsByAppId = function (appid) {
    return $http.get(ApiPath + '/findAppById',
    	{
    		params:{appid:appid}
    	})
  };

  depService.searchDeploymentsByAppId = function (appid) {
    return $http.get(ApiPath + '/findAppById',
    	{
    		params:{appid:appid}
    	})
  };

   depService.searchDeploymentsByUserId = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

  depService.searchDeploymentsByDate = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

   depService.searchDeploymentsByStatus = function (depdata) {
    console.log("In AppService addApp ",depdata)
    return $http.post(ApiPath + '/addDeployment', depdata)
    	
  };

  depService.searchDeploymentsByFixversion = function (appdata) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

}




})();
