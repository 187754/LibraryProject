'use strict';

angular.module('libraryApp.about',['ngRoute'])

.config(['$routeProvider', configuration])
.controller('AboutController',['$scope', controllerContent]);

function configuration($routeProvider){
	$routeProvider.when('/about', {
		templateUrl : 'pages/about/about.html',
		controller : 'AboutController'
	});
};

function controllerContent($scope){
	$scope.message = 'Look! I am an about page.';
}