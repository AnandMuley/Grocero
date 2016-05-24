controllers.controller('TabsController',['$scope','$location',function($scope,$location){
	$scope.tabs = [
	               {
	            	   path:"home",
	            	   name:"home"
	               },{
	            	   path:"products",
	            	   name:"products"
	               },{
	            	   path:"list",
	            	   name:"list"
	               },{
	            	   path:"viewlists",
	            	   name:"view lists"
	               }];
	
	$scope.loadView = function(tab){
		$location.path(tab);
	}
	
}]);