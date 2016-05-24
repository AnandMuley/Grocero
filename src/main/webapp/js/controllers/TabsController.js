controllers.controller('TabsController',['$scope','$location',function($scope,$location){
	$scope.tabs = ["home","products","list"];
	
	$scope.loadView = function(tab){
		$location.path(tab);
	}
	
}]);