(function(){
"use strict";
angular.module('users')
.service('UsersService', UsersService);



UsersService.$inject = ['$http', 'ApiPath'];
function UsersService($http, ApiPath) {
  var userService = this;

  userService.searchApps  = function (searchparams) {
    console.log("DeployService:"+searchparams.appid)
    return $http.get(ApiPath + '/searchApps'+'/'+searchparams.appid+'/'+searchparams.appname+'/'+searchparams.description)
  };

  userService.findAppById = function (appid) {
    return $http.get(ApiPath + '/findAppById',
    	{
    		params:{appid:appid}
    	})
  };

   userService.removeAppById = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

  userService.removeAppById = function (appid) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

   userService.addUser = function (userdata) {
    console.log("In UserService addUser ",userdata)
    return $http.post(ApiPath + '/addUser', userdata)
    	
  };

  userService.Updatepp = function (appdata) {
    return $http.get(ApiPath + '/removeAppById',
    	{
    		params:{appid:appid}
    	})
  };

}




})();
