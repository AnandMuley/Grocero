services.factory('CustomerResource',['$resource','RestPrefix',
      function($resource,RestPrefix){
        return $resource(RestPrefix+'customers/:id',{},{
          query : { method:'GET', isArray:true },
          authenticate : { method : 'POST', params: { id : 'authenticate' }}
        });
}]);
