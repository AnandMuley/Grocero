controllers.controller('TabsController',['$scope','$location',function($scope,$location){
	$scope.tabs = [
	               {
	            	   path:"home",
	            	   name:"home"
	               },{
	            	   path:"products",
	            	   name:"products"
	               },{
	            	   path:"vegetables",
	            	   name:"vegetables"
	               },{
	            	   path:"groceries",
	            	   name:"groceries"
	               }];
	
	$scope.loadView = function(tab){
		$location.path(tab.name);
	}
	
	$scope.currentNavItem = 'page1';
	
}]);