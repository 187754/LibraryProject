'use strict';

angular.module('libraryApp.home',['ngRoute'])

    .config(['$httpProvider', configuration])
    .controller('HomeCtrl',['$scope', '$http', controllerContent]);

function configuration($httpProvider){
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
};

function controllerContent($scope, $http){
    $http.get('/resource').then(function(response) {
        $scope.greeting = response.data;
        console.log($scope.greeting);
    }, function myError(response){
        console.log("status: " + response.status + "  " + response.statusText);
    });
};