services.factory('GroceryListResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'grocerylists/:listId',{},{
		query : {method:'GET',isArray:true},
		update : {method:'PUT'}
	});
}]);
