'use strict';

angular.module('libraryApp.register',['ngRoute'])

    .config(['$httpProvider', configuration])
    .controller('RegisterCtrl',['$rootScope', '$scope', '$http', '$location', '$resource', controllerContent]);

function configuration($httpProvider){
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
};

function controllerContent($rootScope, $scope, $http, $location, $resource){

    $scope.userData = {};

    $scope.checkNick = function(){
        $http.get('/user/' + $scope.userData.username).then(function(response) {
            if (response.statusCode(200)) {
                console.log(true);
            } else {
                alert("Taki login już istnieje! Proszę użyć innego.");
            }
        });
    };

    $scope.register = function() {
        if ($scope.userData.password === $scope.userData.password2){
            var User = $resource('/user/new');
            User.save($scope.userData, function(response){
                console.log(response.body);
            })

        } else {
            alert("Podane hasła muszą być jednakowe");
        }
    };
}