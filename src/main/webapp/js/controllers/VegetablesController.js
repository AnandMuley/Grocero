controllers.controller('VegetablesController',
		['$scope','ProductResource','GroceryListResource','$location','$filter',
		 function($scope,Product,GroceryList,$location,$filter){
	var self = this;
	self.listName = $filter('date')(new Date(),"dd-MMM-yyyy");
	$scope.groceryList = new GroceryList({name:self.listName,items:[]});
	$scope.message = "";

	$scope.saveList = function(){
		$scope.groceryList.$save(function(data,headers){
			$scope.groceryList = new GroceryList({name:"12-Sept-2017",items:[]});
			$scope.message = "Saved successfully !!!";
		});
	}

	$scope.addItemToList = function (item){
		$scope.groceryList.items.push(item.name);
	}

	$scope.isNotEnlisted = function(item){
		return $scope.groceryList.items.indexOf(item) == -1;
	}

}]);
