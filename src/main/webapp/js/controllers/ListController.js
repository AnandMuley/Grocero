controllers.controller('ListController',['$scope','ProductResource',function($scope,Product){
	
	$scope.products = Product.query();
	$scope.list = [];
	
	
	$scope.enlist = function(product){
		$scope.list.push(product);
	}
	
	$scope.deleteRow = function(item){
		var index = $scope.list.indexOf(item);
		if(index!=-1){
			$scope.list.splice(index,1);
		}
	}
	
	$scope.addProduct = function(){
		$scope.list.push({
			product : null,
			quantity : 0
		});	
	}
	
	$scope.addProduct();
	
	$scope.getMeasuredIn = function(product){
		var measuredIn = '';
		if(product!=null){
			var prod = JSON.parse(product);
			measuredIn = prod.measuredIn;	
		}
		return measuredIn;
	}
	
}]);