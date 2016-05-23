controllers.controller('ProductsController',['$scope','ProductResource',function($scope,Product){
	$scope.pageTitle = 'Products Page';
	$scope.products = Product.query();
	$scope.product = {};
	$scope.mode = "add";

	$scope.fetchProducts = function(){
		$scope.products = Product.query();
	}
	
	$scope.add = function(){
		var newProduct = new Product({name:$scope.product.name,measuredIn:$scope.product.measuredIn});
		newProduct.$save({},function(){
			$scope.fetchProducts();
			$scope.product = {};
		});
	}
	
	$scope.update = function(){
		$id = $scope.product.id;
		$scope.product.$update({productId:$id},$scope.product);
	}
	
	$scope.edit = function(product){
		$scope.product = product;
		$scope.mode = "edit";
	}
	
}]);