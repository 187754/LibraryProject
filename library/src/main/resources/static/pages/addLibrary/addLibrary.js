'use strict';

angular.module('libraryApp.addLibrary',['ngRoute'])
    .config([configuration])
    .controller('AddLibraryCtrl',['$scope', controllerContent]);

function configuration(){
};

function controllerContent($scope){
    $scope.message = 'Look! I am an home home home page.';
}