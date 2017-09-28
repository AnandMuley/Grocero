services.factory('MasterListResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'customers/:id/masterlist',{},{
		query : {method:'GET',isArray:false},
		create : {method:'PUT'}
	});
}]);
