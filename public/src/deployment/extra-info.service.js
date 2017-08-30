(function(){
"use strict";
angular.module('deployment')
.service('ExtrInfoService', ExtrInfoService);



ExtrInfoService.$inject = ['$http', 'ApiPath'];
function ExtrInfoService($http, ApiPath) {
  var extInfoService = this;

 


  
 

  extInfoService.findJiraDetailsByDepId = function (depid) {
    return $http.get(ApiPath + '/findJiraDetailsByDepId'+'/'+depid)
  };

   extInfoService.findSvnDetailsByDepId = function (depid) {
    console.log("In AppService addApp ",depdata)
    return $http.get(ApiPath + '/findSvnDetailsByDepId'+'/'+depid )
    	
  };

  extInfoService.findSonarDetailsByDepId = function (depid) {
    return $http.get(ApiPath + '/findSonarDetailsByDepId'+'/'+depid)
  };

}




})();

