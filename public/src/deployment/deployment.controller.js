(function(){
'use strict';
angular.module('deployment')
.controller('DeploymentController',DeploymentController);

DeploymentController.$inject = ['DeployService','ExtrInfoService','$state','ngDialog','$mdDialog'];

 function DeploymentController(DeployService,ExtrInfoService,$state,ngDialog,$mdDialog) {
 	var $dctrl=this;
 	$dctrl.message='';
 	$dctrl.depDetails=(function(){
     DeployService.searchDeploymentsByStatus("SUCCESS").then(
        function(response){
        $dctrl.duefordeploy=response.data.depdetails;
        },function(error){
          console.log("Error while fetich deployments by status")
        }
        )
   });
  $dctrl.duefordeploy=[];
  $dctrl.sonarDetailsNew=[];
  $dctrl.jiraDetailsNew=[];
  $dctrl.svnDetailsNew=[];
  $dctrl.testDetailsNew=[];

  $dctrl.sonarDetails=[];
  $dctrl.jiraDetails=[];
  $dctrl.svnDetails=[];
  $dctrl.testDetails=[];

   var jira=[{"id":1,"depId":6,"jiraNo":"JIRA-0320","jiraDesc":"Creating New Accout","jiraStatus":"Completed","fixVersion":"1.0.89"},
   {"id":2,"depId":6,"jiraNo":"JIRA-03fileNames20","jiraDesc":"Creating New Accout","jiraStatus":"Completed","fixVersion":"1.0.89"}]
  
  var sonar=[{"id":1,"depId":6,"blockers":2,"criticals":3,"majors":7,"time":"10/05/2017"}]

   var svn=[{"id":1,"depId":6,"fileNames":"src/deployment/deployment.module.js","version":"2076","changedby":"Shylendra Kumar","time":"10/05/2017"},
   {"id":1,"depId":6,"fileNames":"src/deployment/deployment.controller.js","version":"2076","changedby":"Rajes Sing","time":"10/05/2017"},
   {"id":1,"depId":6,"fileNames":"src/deployment/deployment.service.js","version":"2076","changedby":"Avaneet Prakash","time":"10/05/2017"},
   {"id":1,"depId":6,"fileNames":"src/deployment/extra-info.service.js","version":"2076","changedby":"Pakki Sagar","time":"10/05/2017"},
   ]



  function loadRecord(){
     DeployService.searchDeploymentsByStatus("NEW").then(
        function(response){
        $dctrl.duefordeploy=response.data.depdetails;
        },function(error){
          console.log("Error while fetich deployments by status")
        }
        )
     
   };

   loadRecord();
   
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
    });

    var svnPromise=ExtrInfoService.findSvnDetailsByDepId(depid)
   svnPromise.then(
    function(response){
        $dctrl.svnDetails=response.data.svndetails;
        
    },function(reson){
        console.log("Error Reason:"+reason);
    });

    var sonarPromise=ExtrInfoService.findSonarDetailsByDepId(depid)
   sonarPromise.then(
    function(response){
        $dctrl.sonarDetails=response.data.sonardetails;
        
    },function(reson){
        console.log("Error Reason:"+reason);
    })

    

  }

  
  $dctrl.createReleaseNote=function(dep,$event){
       var parentEl = angular.element(document.body);
       $mdDialog.show({
         parent: parentEl,
         targetEvent: $event,
         templateUrl:'src/deployment/releasenote.home.html',
             
         locals: { depdtls: dep },
         controller: 'ReleaseNoteController',
         controllerAs:'$rctrl',
         clickOutsideToClose: true,
         escapeToClose: true
      });
  }

 	$dctrl.searchDeployments=function(){
			 		console.log("Search Param -deployment",$dctrl.searchparam);
             if($dctrl.searchparam.userId===undefined){
              $dctrl.searchparam.userId=0;
             }else if($dctrl.searchparam.appId===undefined){
              $dctrl.searchparam.appId=0;
             }
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
     $dctrl.dep.jiraDetails=[{"id":0,"depId":0,"jiraNo":"JIRA-0320","jiraDesc":"Creating New Accout","jiraStatus":"Completed","fixVersion":"1.0.89"},
     {"id":0,"depId":0,"jiraNo":"JIRA-0321","jiraDesc":"Settlement date format issue ","jiraStatus":"Completed","fixVersion":"1.0.89"}];
    
     $dctrl.dep.svnDetails=
     [{"id":0,"depId":0,"fileNames":"src/deployment/deployment.module.js","version":"2076","changedby":"Shylendra Kumar","time":"10/05/2017"},
     {"id":0,"depId":0,"fileNames":"src/deployment/deployment.controller.js","version":"2076","changedby":"Rajes Sing","time":"10/05/2017"},
     {"id":0,"depId":0,"fileNames":"src/deployment/deployment.service.js","version":"2076","changedby":"Avaneet Prakash","time":"10/05/2017"},
     {"id":0,"depId":0,"fileNames":"src/deployment/extra-info.service.js","version":"2076","changedby":"Pakki Sagar","time":"10/05/2017"},
     ];

     $dctrl.dep.sonarDetails=[{"id":0,"sonarId":"SONAR01111","depId":0,"blockers":2,"criticals":3,"majors":7,"time":"10/05/2017"}];

    $dctrl.jiraDetailsNew=$dctrl.dep.jiraDetails;
    $dctrl.svnDetailsNew=$dctrl.dep.svnDetails;
    $dctrl.sonarDetailsNew=$dctrl.dep.sonarDetails;

       
     DeployService.addDeployment($dctrl.dep)
      .then(function(response){
       console.log("Success Response:",response)
       //$ctrl.message=response.data.message;
       
       
      DeployService.searchDeploymentsByStatus("NEW").then(
        function(response){
        $dctrl.duefordeploy=response.data.depdetails;
        },function(error){
          console.log("Error while fetich deployments by status")
        }
        )
       $dctrl.dep=null;
       console.log("duefordploy",$dctrl.duefordploy)
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
  $dctrl.downloadReleaseNote=function(depId){
   var relNotePromise=DeployService.downloadReleaseNote(depId)
   relNotePromise.then(function (response) {
               console.log("Releae response",response)
                var blob = new Blob([response.data],{
                 type: "html/text;charset=utf-8;"
              });
              var downloadLink = document.createElement('a');
              downloadLink.setAttribute('download', 'sampleJSON.txt');
              downloadLink.setAttribute('href', window.URL.createObjectURL(blob));
              downloadLink.click();

            },function(error){
              console.log("Downloading Release note error",error)
            })
  }

   $dctrl.downloadReleaseNote1=function(depId){
   var relNotePromise=DeployService.downloadReleaseNote(depId)
   relNotePromise.then(function (response) {
    
                var file = new Blob([response], {type: 'text/html'});

                var isChrome = !!window.chrome && !!window.chrome.webstore;
                var isIE = /*@cc_on!@*/false || !!document.documentMode;
                var isEdge = !isIE && !!window.StyleMedia;


                if (isChrome){
                    var url = window.URL || window.webkitURL;

                    var downloadLink = angular.element('<a></a>');
                    downloadLink.attr('href',url.createObjectURL(file));
                    downloadLink.attr('target','_self');
                    downloadLink.attr('download', depId+'.txt');
                    downloadLink[0].click();
                }
                else if(isEdge || isIE){
                    window.navigator.msSaveOrOpenBlob(file,'invoice.pdf');

                }
                else {
                    var fileURL = URL.createObjectURL(file);
                    window.open(fileURL);
                }

            },function(error){
              console.log("Downloading Release note error",error)
            })
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