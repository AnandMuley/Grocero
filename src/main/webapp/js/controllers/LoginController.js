app.controller('LoginController',
  ['$scope','CustomerResource','$location','AuthenticationService','$rootScope',
          function($scope,CustomerResource,$location,authenticationService,$rootScope){

  $scope.page = "Login";

  $scope.login = function() {
    CustomerResource.authenticate({},
    {
      username:$scope.user.username,
      password:$scope.user.password
    },
      function(responseData,headers){
          $scope.user.token = responseData.message;
          authenticationService.save($scope.user);
          $location.path('home');
          $rootScope.authenticated = true;
      });
  }

}]);
