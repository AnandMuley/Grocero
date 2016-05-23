var app = angular.module('GroceroUI',['ngRoute','guiControllers','guiServices']);
var controllers = angular.module('guiControllers',[]);
var services = angular.module('guiServices',['ngResource']);

app.config(['$routeProvider',function($routeProvider){
	
	$routeProvider.when('/',{
		templateUrl:'views/Home.html',
		controller : 'HomeController'
	}).when('/home',{
		templateUrl : 'views/Home.html',
		controller : 'HomeController'
	}).when('/products',{
		templateUrl : 'views/Products.html',
		controller : 'ProductsController'
	}).when('/list',{
		templateUrl : 'views/List.html',
		controller : 'ListController'
	});
	
}]);

app.value('RestPrefix','rest/');