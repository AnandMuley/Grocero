controllers.controller('ProductController',['$scope','$modal',function($scope,$modal){
	$scope.pageTitle = 'Products';
	
	$scope.addProductModal = function(){
		var addProductModal = $modal.open({
			animation : true,
			scope : $scope,
			templateUrl : 'views/AddProductModal.html',
			controller : 'AddProductModalController',
			size : 'sm'
		});
	}
	
}]);