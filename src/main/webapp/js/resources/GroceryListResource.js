services.factory('GroceryListResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'grocerylists/:listId',{},{
		query : {method:'GET',params:{listId:'list'},isArray:true},
		update : {method:'PUT'}
	});
}]);