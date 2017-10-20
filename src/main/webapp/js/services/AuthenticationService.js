app.factory('AuthenticationService',['$cookies',function($cookies){

  function getTimeout(){
    let noOfMinutes = 15;
    var curr = new Date();
    return new Date(curr.getTime()+noOfMinutes*60000);
  }

  function save(user){
    user.password = undefined;
    $cookies.putObject('auth',user,{expires:getTimeout()});
  }

  function getAuth(){
    return $cookies.getObject('auth');
  }

  return {
    save : save,
    getAuth : getAuth
  };

}]);
