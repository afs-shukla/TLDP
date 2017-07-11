(function () {
"use strict";

angular.module('public')
.component('searchList', {
  templateUrl: 'src/apps/app-searchlist.view.html',
  // bindings: {
  //   appdetails: '<'
  // }

  controller: AppsSearchListController
});

AppsSearchListController.$inject = ['ApiPath'];
function AppsSearchListController(ApiPath) {
  var $ctrl = this;
 var
        appidList = ['Pierre', 'Pol', 'Jacques', 'Robert', 'Elisa'],
        appnameList = ['Dupont', 'Germain', 'Delcourt', 'bjip', 'Menez'];


    function createRandomItem() {
        var
            appid = appidList[Math.floor(Math.random() * 4)],
            appname = appnameList[Math.floor(Math.random() * 4)];
          

        return{
            appid: appid,
            appname: appname
           
        };
    }
  
   
  $ctrl.appslist=[];

  for (var j = 0; j < 10; j++) {
        $ctrl.appslist.push(createRandomItem());
    }

  $ctrl.itemsByPage=5;  
  
  // $ctrl.getAppsList={
  // 	"applist":[{"appid":Apr Gloss","appname":"Apr Gloss Clearing System"},{"appid":"Mair1","appname":"Mairr Gui app for Gloss"}]
  // }
}

})();
