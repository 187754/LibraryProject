'use strict';

angular.module('libraryApp.cookiesManager', ['ngCookies'])

.directive('notLoggedIn', function(){
	return {
		restrict: 'E',
		templateUrl: 'components/cookiesManager/notLoggedIn.html'
	};
})
.controller('CookiesController', ['$scope', '$rootScope', '$http', '$cookies', 'CookieService', '$route', '$window', cookiesManagement])
.service('CookieService', ['$rootScope', '$http', '$cookies', '$window', '$filter', '$location', '$route', cookieServiceManagement]);

function cookieServiceManagement($rootScope, $http, $cookies, $window, $filter, $location, $route){
	
	var users = [];
	var loginErrorFlag;
	var functionStarter = this;

	

	this.getPermission = function(_uid){
		
		$http.get($rootScope.baseUrl + '/user/groups/permission/' + _uid).success(function(data){
			$cookies.put('cookiePermission', data);
			functionStarter.setFlags();
		});
	};

	this.signIn = function(_login, _password){

		$http.get($rootScope.baseUrl + '/user/loginUser/' + _login, {params: {pw: _password}}).success(function(data){
			users = data;

			if (data.uid != null){

				functionStarter.getPermission(users.uid);

				var user = [];
				user.push(users);
				$cookies.user = JSON.stringify(user);

				var userCookie = $cookies.user,
				user = userCookie ? JSON.parse(userCookie) : [];	

				$cookies.put('cookieCn', user[0].cn);
				$cookies.put('cookieUid', user[0].uid);
				$cookies.put('cookieMail', user[0].mail);
				$cookies.put('cookieToken', user[0].token);

				$window.location.href = '#/tasks';

				loginErrorFlag = false;
			}
			else{
				loginErrorFlag = true;
			};
		}).error(function(){
			loginErrorFlag = true;
		});
	};

	this.setFlags = function(){
		var tmp = $cookies.get('cookiePermission');
		var array = [];

		switch(tmp){
			case 'admin':
				var adminFlag = true;
				var pmFlag = true;
				var userFlag = true;
				array.push(adminFlag);
				array.push(pmFlag);
				array.push(userFlag);
				break;

			case 'pm':
				var adminFlag = false;
				var pmFlag = true;
				var userFlag = true;
				array.push(adminFlag);
				array.push(pmFlag);
				array.push(userFlag);
				break;

			case 'user':
				var adminFlag = false;
				var pmFlag = false;
				var userFlag = true;
				array.push(adminFlag);
				array.push(pmFlag);
				array.push(userFlag);
				break;
		};

		return array;
	}

	this.reloadCookie = function(_uid){
		$http({
			url: $rootScope.baseUrl+'/user/users/' + _uid,
			method: "GET",
			params: {
				editor: functionStarter.getCookieUid(),
				token: functionStarter.getCookieToken()
			}
		}).then(function(response){
			$cookies.remove('cookieCn');
			$cookies.put('cookieCn', response.data.cn);
		}, function(response){
			if(response.status === 401){
				functionStarter.logOut();
				alert(response.data);
			} else {
				alert("Błąd serwera, spróbuj ponownie");
			}
		});
	};

	this.ifAdmin = function(){
		var tmp = functionStarter.setFlags();
		return tmp[0];
	};

	this.ifPm = function(){
		var tmp = functionStarter.setFlags();
		return tmp[1];
	};

	this.ifUser = function(){
		var tmp = functionStarter.setFlags();
		return tmp[2];
	};

	this.ifIncorrectData = function(){
		return loginErrorFlag;
	};

	this.logOut = function(){
		$cookies.remove('cookieCn');
		$cookies.remove('cookieUid');
		$cookies.remove('cookiePermission');
		$cookies.remove('cookieMail');
		$cookies.remove('cookieToken');

		$window.location.href = '#/login';
	};

	this.getCookieCn = function(){
		return $cookies.get('cookieCn');
	}

	this.getCookieUid = function(){		
		return $cookies.get('cookieUid');
	};

	this.getCookiePermission = function(){
		return $cookies.get('cookiePermission');
	};

	this.getCookieMail = function(){
		return $cookies.get('cookieMail');
	};

	this.getCookieToken = function(){
		return $cookies.get('cookieToken');
	};

	this.refreshToken = function(){
		$http({
			url: $rootScope.baseUrl+"/user/checkToken",
			dataType: "application/json",
			method: "POST",
			params: {
				uid: functionStarter.getCookieUid(),
				token: functionStarter.getCookieToken()
			},
			headers: {
				"Content-Type": "application/json"
			}
		}).then(function(response){

		}, function(response){
			if(response.status === 401){
				functionStarter.logOut();
				alert(response.data);
			}
		})
		};
	};

function cookiesManagement($scope, $rootScope, $http, $cookies, CookieService, $route, $window){
	 $scope.CookieService = CookieService;
	 //$scope.CookieService.refreshToken();
};


