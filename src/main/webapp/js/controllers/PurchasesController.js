controllers.controller('PurchasesController',
		['$scope','GroceryListResource',function($scope,GroceryList){
	
	$scope.selected = "default";
	$scope.list = GroceryList.query();
	$scope.totalCost=0;

	$scope.showList = function(){
		$scope.message = null;
		var selectedItem = JSON.parse($scope.selected);
		var glr = new GroceryList(selectedItem);
		$scope.selected=glr;
		$scope.calculateTotal();
	}
	
	$scope.calculateTotal = function(){
		$scope.totalCost = 0;
		var items = $scope.selected.items; 
		var len = items.length;
		for(var i=0;i<len;i++){
			$scope.totalCost = $scope.totalCost+items[i].cost;
		}
	}
	
	$scope.updateCosts = function(){
		$id = $scope.selected.id;
		$scope.selected.$update({listId:$id},function(){
			$scope.message = "Details saved successfully !";
			$scope.selected = 'default';
		});
	}
	
}]);