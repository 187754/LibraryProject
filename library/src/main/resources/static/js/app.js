var app = angular.module('libraryApp', [
    'ngRoute',
    'ngResource',
    'libraryApp.home',
    'libraryApp.addBook',
    'libraryApp.about',
    'libraryApp.addLibrary',
    'libraryApp.login',
    'libraryApp.register'
]);

app.run(function ($rootScope){
    $rootScope.rootDate = new Date();
});

app.config(function($routeProvider, $locationProvider, $httpProvider, $resourceProvider){
    $routeProvider
        .when('/addBook', {
            templateUrl : 'pages/addBook/addBook.html',
            controller : 'AddBookCtrl'
        })
        .when('/', {
            templateUrl : 'pages/home/home.html',
            controller : 'HomeCtrl'
        })
        .when('/about', {
            templateUrl : 'pages/about/about.html',
            controller : 'AboutCtrl'
        })
        .when('/addLibrary', {
            templateUrl : 'pages/addLibrary/addLibrary.html',
            controller : 'AddLibraryCtrl'
        })
        .when('/login', {
            templateUrl : 'pages/login/login.html',
            controller : 'LoginCtrl'
        })
        .when('/register', {
            templateUrl : 'pages/register/register.html',
            controller : 'RegisterCtrl'
        })
        .otherwise({
            template : "Strona niedostępna."
        });


    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $resourceProvider.defaults.stripTrailingSlashes = false;

});

app.controller('mainCtrl', function($scope, $location, $http){
    $scope.getClass = function (path) {
        if ($location.path().substr(0, path.length) == path) {
            return "active"
        } else {
            return ""
        }
    }

});

app.controller('dateCtrl', function ($rootScope, $scope){
    $scope.myDate = function (){
        return $rootScope.rootDate;
    };
});