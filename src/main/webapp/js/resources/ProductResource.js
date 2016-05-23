services.factory('ProductResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'products/:productId',{},{
		query : {method:'GET',params:{productId:'list'},isArray:true},
		update : {method:'PUT'}
	});
}]);