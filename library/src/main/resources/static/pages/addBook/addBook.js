'use strict';

angular.module('libraryApp.addBook',['ngRoute'])
    .config([configuration])
    .controller('AddBookCtrl',['$scope', controllerContent]);

function configuration(){
};

function controllerContent($scope){
    $scope.message = 'Look! I am an home home home page.';
}