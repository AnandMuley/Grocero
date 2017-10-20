app.controller('RedirectionController',['$scope','$location',
      function($scope,$location){

  $scope.renderPage = function(pageName){
    $location.path(pageName);
  }

}]);
