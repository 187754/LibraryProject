'use strict';

angular.module('libraryApp.addLibrary',['ngRoute'])

.config(['$routeProvider', configuration])
.controller('addLibraryController',['$scope', '$http', '$route', controllerContent]);

function configuration($routeProvider){
	$routeProvider.when('/addLibrary', {
		templateUrl : 'pages/addLibrary/addLibrary.html',
		controller : 'addLibraryController'
	});
};

function controllerContent($scope, $http, $route){
	
	$http({
		url: "http://localhost:8080/resource/",
		method: "GET",
	}).then(function(response){
		console.log(response.data);
	});
}