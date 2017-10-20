var app = angular.module('GroceroUI',
					['ngMaterial','ngRoute','guiControllers','guiServices','base64']);
var controllers = angular.module('guiControllers',[]);
var services = angular.module('guiServices',['ngResource','ngCookies']);

app.config(['$routeProvider','$mdIconProvider','$httpProvider',
function($routeProvider,$mdIconProvider,$httpProvider){

	$routeProvider.when('/',{
		redirectTo:'/login'
	}).when('/registration',{
		templateUrl : 'views/Registration.html',
		controller : 'RegistrationController'
	}).when('/login',{
	  templateUrl:'views/Login.html',
	  controller : 'LoginController'
	}).when('/home',{
		redirectTo : '/mylists'
	}).when('/products',{
		templateUrl : 'views/Products.html',
		controller : 'ProductsController'
	}).when('/masterlist',{
		templateUrl : 'views/MasterList.html',
		controller : 'MasterListController'
	}).when('/mylists',{
		templateUrl : 'views/MyLists.html',
		controller : 'MyListsController'
	}).when('/groceries',{
		templateUrl: 'views/Groceries.html',
		controller : 'GroceriesController'
	}).when('/purchases',{
		templateUrl: 'views/Purchases.html',
		controller: 'PurchasesController'
	});

	$mdIconProvider.iconSet('social','img/icons/sets/social-icons.svg',24)
								 .defaultIconSet('img/icons/sets/core-icons.svg',24);

  $httpProvider.interceptors.push(['$q','$cookies','$base64',
					function($q,$cookies,$base64){

		function getTimeout(){
			let noOfMinutes = 15;
	    var curr = new Date();
	    return new Date(curr.getTime()+(noOfMinutes*60000));
	  }

		function handleAuthenticationResponse(response){
			if(response.config.url.endsWith('authenticate') == true){
				var user = response.config.data;
				user.password = undefined;
				user.token = response.data.message;
		    // $cookies.putObject('auth',user,{expires:getTimeout()});
				console.log("Interceptor Authentication...");
			}
		}

		function isNotUserRegistrationCall(config){
			return !(config.url.endsWith('customers') && config.method == "POST");
		}

		function populateAuthenticationHeaders(config) {
			if(config.url.endsWith('authenticate') == false && isNotUserRegistrationCall(config)){
				var authData = $cookies.getObject('auth');
				var encoded = $base64.encode(authData.username+':'+authData.token);
				config.headers.Authorization = 'Basic '+encoded;
			}
		}

		return{
	    'request' : function(config){
				if(config.url.startsWith('rest') == true){
					populateAuthenticationHeaders(config);
				}
	      return config;
	    },
			'requestError':function(rejection){
				console.log("REQUEST ERROR  : "+JSON.stringify(rejection));
				if (rejection.status === 403) {
					 window.location.href = '';
				 }
		 		return $q.reject(rejection);
			},
	    'response' : function(response){
				if(response.config.url.startsWith('rest') == true){
					handleAuthenticationResponse(response);
				}
	      return response;
	    },
			'responseError' : function(rejection){
				if (rejection.status === 403) {
					 window.location.href = '';
				}else if(rejection.message == "Cannot read property 'username' of undefined"){
					window.location.href = '';
				}
					return $q.reject(rejection);
			}
	  }
	}]);

}]);



app.value('RestPrefix','rest/');
