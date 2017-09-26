controllers.controller('GroceriesController',
		['$scope','ProductResource','GroceryListResource','$location','$filter',
		 function($scope,Product,GroceryList,$location,$filter){

  var self = this;
  self.listName = $filter('date')(new Date(),"dd-MMM-yyyy");
	$scope.products = Product.query();
	$scope.list = new GroceryList({name:self.listName,items:[]});

	$scope.enlist = function(product){
		if($scope.list.items.indexOf(product) == -1){
			$scope.list.items.push(product);
		}
	}

	$scope.saveList = function(){
		$scope.list.$save(function(data,headers){
			$scope.masterList = new GroceryList({name:self.listName,items:[]});
			$scope.message = "GroceryList Saved successfully !!!";
		},function(httpResponse){
			let data = httpResponse.data;
			$scope.message = data.message;
		});
	}

}]);
