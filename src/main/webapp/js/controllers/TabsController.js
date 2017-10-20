controllers.controller('TabsController',['$scope','$location','$cookies','$rootScope',
				function($scope,$location,$cookies,$rootScope){


	function checkCookie(){
		var authData = $cookies.getObject('auth');
		if(authData != undefined){
			$rootScope.authenticated = true;
		}
	}

	checkCookie();

	$scope.logout = function(){
		$cookies.remove('auth');
		$rootScope.authenticated = false;
		$location.path('login');
	}

	$scope.tabs = [
	               {
	            	   path:"home",
	            	   name:"home"
	               },
								//  {
	            	//    path:"products",
	            	//    name:"products"
	              //  },
								 {
	            	   path:"groceries",
	            	   name:"groceries"
	               },{
	            	   path:"purchases",
	            	   name:"purchases"
	               },{
	            	   path:"masterlist",
	            	   name:"masterlist"
	               }];

	$scope.loadView = function(tab){
		$location.path(tab.name);
	}

	$scope.currentNavItem = 'page1';

}]);
