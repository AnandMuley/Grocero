services.factory('ProductResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'products/:productId',{},{
		query : {method:'GET',isArray:true},
		update : {method:'PUT'}
	});
}]);