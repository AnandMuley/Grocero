controllers.controller('AddProductModalController',['$scope','$modalInstance',function($scope,$modalInstance){
	
	$scope.cancel = function(){
		$modalInstance.dismiss();
	}
	
}]);