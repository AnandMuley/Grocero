controllers.controller('ListController',['$scope','ProductResource',function($scope,Product){
	
	$scope.products = Product.query();
	$scope.list = [{
		product : '',
		quantity : 0
	}];
	
	$scope.enlist = function(product){
		$scope.list.push(product);
	}
	
	$scope.addProduct = function(){
		
	}
	
}]);