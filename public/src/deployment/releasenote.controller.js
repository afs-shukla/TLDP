(function(){
'use strict';
angular.module('deployment')
.controller('ReleaseNoteController',ReleaseNoteController);

ReleaseNoteController.$inject = ['ReleaseNoteService','$state','$mdDialog','depdtls'];

 function ReleaseNoteController(ReleaseNoteService,$state,$mdDialog,depdtls) {
 	var $rctrl=this;
 	$rctrl.notes={
 		depId:depdtls
 	}

console.log("$rctrl",$rctrl)
 	$rctrl.createReleaseNote=function(notes,$event){
 		console.log("Release note:",$rctrl.notes)
 		console.log("depdtls" ,depdtls)
 		$rctrl.notes.id=0;
        $rctrl.notes.depId=depdtls;
 		ReleaseNoteService.createReleaseNote($rctrl.notes)
 		 .then(function(response){
           $rctrl.notes=null;
     	    $mdDialog.show(
			      $mdDialog.alert()
			        /*.parent(angular.element(document.querySelector('#popupContainer')))*/
			        .clickOutsideToClose(true)
			        .title('Success')
			        .textContent('You can specify some description text in here.')
			        .ariaLabel('Alert Dialog Demo')
			        .ok('Got it!')
			        .targetEvent($event)
			    );
			  



 		 },function(error){
 	
		    $mdDialog.show(
		      $mdDialog.alert()
		        /*.parent(angular.element(document.querySelector('#popupContainer')))*/
		        .clickOutsideToClose(true)
		        .title('Failure')
		        .textContent('You can specify some description text in here.')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent($event)
		    );
		  


 		 })

 	}
 	

 }


})();