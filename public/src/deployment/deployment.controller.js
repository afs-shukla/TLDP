(function(){
'use strict';
angular.module('deployment')
.controller('DeploymentController',DeploymentController);

DeploymentController.$inject = ['DeployService','ExtrInfoService','$state','ngDialog','$mdDialog'];

 function DeploymentController(DeployService,ExtrInfoService,$state,ngDialog,$mdDialog) {
 	var $dctrl=this;
 	$dctrl.message='';
 	$dctrl.depDetails=[];
  $dctrl.sonarDetailsNew=[];
  $dctrl.jiraDetailsNew=[];
  $dctrl.svnDetailsNew=[];
  $dctrl.testDetailsNew=[];

  $dctrl.sonarDetails=[];
  $dctrl.jiraDetails=[];
  $dctrl.svnDetails=[];
  $dctrl.testDetails=[];

   var jira=[{"id":1,"depId":6,"jiraNo":"JIRA-0320","jiraDesc":"Creating New Accout","jiraStatus":"Completed","fixVersion":"1.0.89"},
   {"id":1,"depId":6,"jiraNo":"JIRA-0320","jiraDesc":"Creating New Accout","jiraStatus":"Completed","fixVersion":"1.0.89"}]
  

  $dctrl.findExtraInfo=function(){
    //console.log("Extra info:",$dctrl.dep);
    $dctrl.jiraDetailsNew=jira;
     console.log("Extra info After:",$dctrl.jiraDetailsNew);
  }

  $dctrl.findExtraInfoByDepId=function(depid){
   var jiraPromise=ExtrInfoService.findJiraDetailsByDepId(depid)
   jiraPromise.then(
    function(response){
        $dctrl.jiraDetails=response.data.jiradetails;
    },function(reson){
        console.log("Error Reason:"+reason);
    })

    

  }

 	$dctrl.searchDeployments=function(){
			 		console.log("Search Param -deployment",$dctrl.searchparam);
			   		 var depPromise= DeployService.searchDeployments($dctrl.searchparam)

			 		depPromise.then(
			        function(response){
			       	console.log("Success Response:"+response.data.deplist);
			       	$dctrl.depDetails=response.data.deplist;
			    
			        $dctrl.searchparams=null;

			       },function(reason){
			       	  console.log("Error Reason:"+reason);
			       	  $dctrl.errormsg=reason.data.message;
			             ngDialog.openConfirm({
			                    template:
			                        '<span class="glyphicon glyphicon-ok"></span>' +
			                        'Error While getting the applicaitons ',
			                    plain: true,
			                    className: 'ngdialog-theme-default'
			                });

			       })




 	}
    
    $dctrl.addDeployment=function() {
     console.log("dep",$dctrl.dep);
     $dctrl.dep.id=0;
     $dctrl.dep.jiraDetails=jira;
     $dctrl.dep.svnDetails=[];
     $dctrl.dep.sonarDetails=[];


       
     DeployService.addDeployment($dctrl.dep)
      .then(function(response){
       console.log("Success Response:",response)
       //$ctrl.message=response.data.message;
       $dctrl.dep=null;

          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'New Deployment is added successfully',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      },function (reason){
       console.log("Error Response:",reason)
       //$ctrl.message=response.data.message;
          ngDialog.openConfirm({
                    template:
                        '<span class="glyphicon glyphicon-ok"></span>' +
                        'Failed to add new Deployment',
                    plain: true,
                    className: 'ngdialog-theme-default'
                });

      });
 	}


  $dctrl.openApplicationSearch=function($event){
  
    
      var parentEl = angular.element(document.body);
       $mdDialog.show({
         parent: parentEl,
         targetEvent: $event,
         templateUrl:' src/apps/app-search-component.modal.view.html',
             
         /*locals: { appsdtls: $ctrl.findbyid },
         controller: 'DialogController',*/
         clickOutsideToClose: true,
         escapeToClose: true
      });

   };


 }
   function findElement(arr, propName, propValue) {
  for (var i=0; i < arr.length; i++)
    if (arr[i][propName] == propValue)
      return arr[i];

  
}


})();