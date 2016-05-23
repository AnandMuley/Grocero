controllers.controller('ProductsController',['$scope','Product',function($scope,Product){
	$scope.pageTitle = 'Products Page';
	$scope.products = Product.query();
	
}]);