app.controller('RegistrationController',['$scope','CustomerResource',
        function($scope,CustomerResource){

  $scope.user = new CustomerResource();

  $scope.register = function(){
    $scope.user.$save({},function(){
      $scope.user = new CustomerResource();
      $scope.message = "Registration successful !";
    });
  }

}]);
