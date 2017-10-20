app.controller('LoginController',
  ['$scope','CustomerResource','$location','AuthenticationService','$rootScope',
          function($scope,CustomerResource,$location,authenticationService,$rootScope){

  $scope.page = "Login";

  function isEmpty(value){
    return value == undefined || value == "";
  }

  function requestIsValid(){
    let isValid = true;
    if(isEmpty($scope.user)){
      $scope.message = 'Please enter username and password';
      isValid = false;
    } else if(isEmpty($scope.user.username)){
      $scope.message = 'Username is required';
      isValid = false;
    } else if(isEmpty($scope.user.password)){
      $scope.message = 'Password is required';
      isValid = false;
    }
    return isValid;
  }

  $scope.login = function() {
    if(requestIsValid()){
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
        },function(response){
          if(response.status == 404){
              $scope.message = 'Authentication failed !';
          }
          $scope.user = {};
        });
    }

  }

}]);
