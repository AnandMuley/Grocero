var app = angular.module('GroceroUI',['ngMaterial','ngRoute','guiControllers','guiServices']);
var controllers = angular.module('guiControllers',[]);
var services = angular.module('guiServices',['ngResource']);

app.config(['$routeProvider',function($routeProvider){
	
	$routeProvider.when('/',{
		templateUrl : 'views/Login.html',
		controller : 'UserController'
	}).when('/products',{
		templateUrl : 'views/Products.html',
		controller : 'ProductsController'
	}).when('/vegetables',{
		templateUrl : 'views/Vegetables.html',
		controller : 'VegetablesController'
	}).when('/home',{
		templateUrl : 'views/Home.html',
		controller : 'HomeController'
	}).when('/groceries',{
		templateUrl: 'views/Groceries.html',
		controller : 'GroceriesController'
	}).when('/purchase',{
		templateUrl: 'views/Purchases.html',
		controller: 'PurchasesController'
	}).when('/registration',{
		templateUrl : 'views/Registration.html'
	});
	
}]);

app.value('RestPrefix','rest/');