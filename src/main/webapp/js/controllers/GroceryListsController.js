controllers.controller('GroceryListsController',
		['$scope','GroceryListResource',function($scope,GroceryList){
	
	$scope.groceryLists = GroceryList.query();
	
}]);