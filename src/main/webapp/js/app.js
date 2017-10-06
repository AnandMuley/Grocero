var app = angular.module('GroceroUI',['ngMaterial','ngRoute','guiControllers','guiServices']);
var controllers = angular.module('guiControllers',[]);
var services = angular.module('guiServices',['ngResource']);

app.config(['$routeProvider','$mdIconProvider',function($routeProvider,$mdIconProvider){

	$routeProvider.when('/',{
		redirectTo:'/login'
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

}]);

app.value('RestPrefix','rest/');
