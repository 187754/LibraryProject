'use strict';
angular.module('libraryApp',
		[ 
		  'ngRoute', 
		  'libraryApp.about', 
		  'libraryApp.home'
		 ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
        templateUrl : 'pages/home/home.html',
        controller  : 'HomeController'
    })
	.otherwise({
		redirectTo : '/home'
	});
	// TODO add httpProvider
} ])
