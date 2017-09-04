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
    return $http.get(ApiPath + '/findAppById')
    	
  };

   depService.searchDeploymentsByStatus = function (status) {
    console.log("DeployService findByStatus",status)
    return $http.get(ApiPath + '/findByStatus' + '/'+status)
    	
  };

  depService.downloadReleaseNote = function (depid) {
   delete $http.defaults.headers.common['X-Requested-With']; 
   return $http.get(ApiPath + '/getReleaseNote' + '/'+depid, {responseType:'text/html'
        })
  };

  

  depService.searchDeploymentsByFixversion = function (appdata) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

}




})();
