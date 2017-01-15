'use strict';

angular.module('libraryApp.home',['ngRoute'])

.config(['$routeProvider', configuration])
.controller('HomeController',['$scope', controllerContent]);

function configuration($routeProvider){
	$routeProvider.when('/home', {
		templateUrl : 'pages/home/home.html',
		controller : 'HomeController'
	});
};

function controllerContent($scope){
	$scope.message = 'Look! I am an home home home page.';
}