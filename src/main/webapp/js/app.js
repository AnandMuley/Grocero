var app = angular.module('GroceroUI',['ngRoute','guiControllers','guiServices']);
var controllers = angular.module('guiControllers',[]);
var services = angular.module('guiServices',['ngResource']);

app.config(['$routeProvider',function($routeProvider){
	
	$routeProvider.when('/',{
		redirectTo:'/mylists'
	}).when('/home',{
		redirectTo : '/mylists'
	}).when('/products',{
		templateUrl : 'views/Products.html',
		controller : 'ProductsController'
	}).when('/vegetables',{
		templateUrl : 'views/Vegetables.html',
		controller : 'VegetablesController'
	}).when('/mylists',{
		templateUrl : 'views/MyLists.html',
		controller : 'MyListsController'
	}).when('/groceries',{
		templateUrl: 'views/Groceries.html',
		controller : 'GroceriesController'
	});
	
}]);

app.value('RestPrefix','rest/');