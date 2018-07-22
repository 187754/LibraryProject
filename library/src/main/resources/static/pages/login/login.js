'use strict';

angular.module('libraryApp.login',['ngRoute'])

    .config(['$httpProvider', configuration])
    .controller('LoginCtrl',['$rootScope', '$scope', '$http', '$location', controllerContent]);

function configuration($httpProvider){
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
};

function controllerContent($rootScope, $scope, $http, $location){

    var authenticate = function(credentials, callback) {
        $rootScope.authenticated = true;
        var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/login', {headers : headers}).then(function(response) {
            if (response.data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
            $rootScope.authenticated = false;
            callback && callback();
        });

    }

    $scope.credentials = {};
    $scope.login = function() {
        authenticate($scope.credentials, function() {
            if ($rootScope.authenticated) {
                $location.path("/");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };
}