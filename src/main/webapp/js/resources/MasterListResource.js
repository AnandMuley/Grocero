services.factory('MasterListResource',['$resource','RestPrefix',function($resource,RestPrefix){
	return $resource(RestPrefix+'customers/:id/masterlist',{},{
		query : {method:'GET',isArray:true},
		create : {method:'POST'}
	});
}]);
