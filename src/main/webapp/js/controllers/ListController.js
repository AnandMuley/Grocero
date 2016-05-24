controllers.controller('ListController',['$scope','ProductResource','GroceryListResource',function($scope,Product,GroceryList){
	
	$scope.products = Product.query();
	$scope.list = new GroceryList({items:[]});
	$scope.itemIndex = 0;
	$scope.mode="create";
	
	
	$scope.enlist = function(product){
		$scope.list.items.push(product);
	}
	
	$scope.deleteRow = function(item){
		var index = $scope.list.items.indexOf(item);
		if(index!=-1){
			$scope.list.items.splice(index,1);
		}
	}
	
	$scope.addProduct = function(){
		$scope.list.items.push({
			index:$scope.itemIndex,
			quantity:0,
			product:null
		});
		$scope.itemIndex++;
	}
	
	$scope.getProductFromJSON = function(prodJSON){
		var prod = null;
		if(prodJSON!=null){
			prod = JSON.parse(prodJSON);
		}
		return prod;
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
	
	$scope.backToCreateList = function(){
		$scope.mode = 'create';
		$scope.list = new GroceryList({items:[]});
		$scope.addProduct();
	}
	
	$scope.viewGroceryList = function(getResponseHeaders){
		var responseHeaders = getResponseHeaders();
		var $id = responseHeaders.location.substring(responseHeaders.location.lastIndexOf('/')+1);
		var groceryListFound = GroceryList.get({listId:$id},function(){
			$scope.list = groceryListFound;
			$scope.mode="view";
		});
	}
	
	$scope.saveList = function(){
		for(var index=0;index<$scope.list.items.length;index++){
			var product = $scope.getProductFromJSON($scope.list.items[index].product);
			product.quantity = $scope.list.items[index].quantity;
			$scope.list.items[index] = product;
		}
		$scope.list.$save(function(value,getResponseHeaders){
			$scope.viewGroceryList(getResponseHeaders);
		});
	}
	
}]);