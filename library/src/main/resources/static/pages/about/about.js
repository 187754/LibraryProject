'use strict';

angular.module('libraryApp.about',['ngRoute'])
    .config([configuration])
    .controller('AboutCtrl',['$scope', controllerContent]);

function configuration(){
};

function controllerContent($scope){
    $scope.message = 'Look! I am an home home home page.';
}