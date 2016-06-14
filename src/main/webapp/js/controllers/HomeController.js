controllers.controller('HomeController',
		['$scope','GroceryListResource',function($scope,GroceryList){
	
	$scope.groceryLists = GroceryList.query();
	
	$scope.deleteList = function(list){
		$id=list.id;
		list.$delete({listId:$id},function(){
			$scope.groceryLists = GroceryList.query();
		});
	}
	
	$scope.saveChanges = function(list){
		$id = list.id;
		list.$update({listId:$id},function(){
			$scope.message = 'Details saved successfully !';
		});
	}
	
}]);