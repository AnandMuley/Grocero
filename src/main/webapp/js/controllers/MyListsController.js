controllers.controller('MyListsController',
		['$scope','GroceryListResource',function($scope,GroceryList){
	
	$scope.groceryLists = GroceryList.query();
	
	$scope.deleteList = function(list){
		$id=list.id;
		list.$delete({listId:$id},function(){
			$scope.groceryLists = GroceryList.query();
		});
	}
	
}]);