'use strict';

angular.module('libraryApp.home',['ngRoute'])

    .config([configuration])
    .controller('HomeCtrl',['$scope', controllerContent]);

function configuration(){
};

function controllerContent($scope){
    $scope.message = 'Look! I am an home home home page.';//TODO usunac
}