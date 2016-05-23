controllers.controller('TabsController',['$scope','$location',function($scope,$location){
	$scope.tabs = ["home","products","list","logout"];
	
	$scope.loadView = function(tab){
		$location.path(tab);
	}
	
}]);