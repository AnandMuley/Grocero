app.controller('LoginController',['$scope',function($scope){

  $scope.page = "Login";

  $scope.login = function() {
    console.log('Authenticating user... '+JSON.stringify($scope.user));
  }

}]);
