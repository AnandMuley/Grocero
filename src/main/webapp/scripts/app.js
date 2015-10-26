var app = angular.module('Grocero', 
		['ngRoute',
		 'GroceroControllers',
		 'ui.bootstrap',
		 'ngCookies']);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/Home.html',
		controller : 'HomeController'
	}).when('/home', {
		templateUrl : 'views/Home.html',
		controller : 'HomeController'
	}).when('/products', {
		templateUrl : 'views/Product.html',
		controller : 'ProductController'
	});
} ]);

var controllers = angular.module('GroceroControllers', []);