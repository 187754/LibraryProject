'use strict';

angular.module('libraryApp.login', ['ngRoute', 'vcRecaptcha'])

.config(['$routeProvider', configuration])
.controller('loginViewCtrl', ['$scope', '$http', '$window', '$filter', '$rootScope', 'regExpFactory', 'vcRecaptchaService', controllerContent])

function configuration($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl: 'pages/login/login.html',
		controller: 'loginViewCtrl'
	})
};

function controllerContent($scope, $http, $window, $filter, $rootScope, regExpFactory, vcRecaptchaService) {
	$scope.windowMode = 'login';
	$scope.regExpFactory = regExpFactory;
	$scope.uid = "";
	$scope.mail = "";
	$scope.user="";
	$scope.tmpPassword = {};
	$scope.response = null;
	$scope.widgetId = null;
	$scope.key="6LfQggsTAAAAAE85SC7PGAXym3cmf9h3SedhPYeG";
	$scope.vcRecaptchaService=vcRecaptchaService;

	$scope.sendNewPasswd=false;
	$scope.confirmEmail=false;
	$scope.typeYourData=true;

	$(".nav li").on("click", function() {
		$(".nav li").removeClass("active");
		$(this).addClass("active");

	});

	$scope.filler = function(_name, _surname){
		var nm = _name.slice(0, 1);		

		if(_name != undefined && _surname != undefined){
			$scope.tmp = nm + _surname;
			$scope.temp = $scope.polishLetterReplacer($scope.tmp);
			$scope.uid = $scope.toLowercase($scope.temp);
			$scope.mailFiller(_name, _surname);
			return $scope.uid;
		};
	};

	$scope.mailFiller = function(_name, _surname){
		if(_name != undefined && _surname != undefined){
			$scope.tmp = _name + '.' + _surname + '@comarch.com';
			$scope.temp = $scope.toLowercase($scope.tmp);
			$scope.mail = $scope.polishLetterReplacer($scope.temp);
			return $scope.mail;
		};

	};

	$scope.polishLetterReplacer = function(_text){
		_text = _text.replace(/ę/ig,'e');
		_text = _text.replace(/[źż]/ig,'z');
		_text = _text.replace(/ó/ig,'o');
		_text = _text.replace(/ł/ig,'l');
		_text = _text.replace(/ć/ig,'c');
		_text = _text.replace(/ś/ig,'s');
		_text = _text.replace(/ń/ig,'n');
		_text = _text.replace(/ą/ig,'a');

		return _text;
	};

	$scope.toLowercase = function(_text){
		$scope.tmp = angular.lowercase(_text);

		return $scope.tmp;
	};

	$scope.sendRemindRequest = function(uid, mail){
		
		var string = mail+":"+uid;

		$http({
			url: $rootScope.baseUrl+"/reminder/reminderInput",
			dataType: "text/plain",
			method: "POST",
			params: {
				string: string,
				gResponse: $scope.response
			},
			headers: {
				"Content-Type": "text/plain"
			}
		}).then(function(response){
			if(response.data=="true"){
				alert("mail został wysłany na Twój adres pocztowy: "+mail);
				$scope.typeYourData=false;
				$scope.confirmEmail=true;
			}
			else{
				alert(response.data);
				$scope.vcRecaptchaService.reload($scope.widgetId);
			}
		}, function(response){
			if(response.status === 401){
				CookieService.logOut();
				alert(response.data);
			} else {
				alert("Błąd serwera, spróbuj ponownie");
			}
		});

	};

	$scope.sendConfirmPass = function(uid, tempCode){
		var string = tempCode+':'+uid;
		if($scope)
		$http({
			url: $rootScope.baseUrl+"/reminder",
			dataType: "text/plain",
			method: "POST",
			data: string,
			headers: {
				"Content-Type": "text/plain"
			}
		}).then(function(response){
			if(response.data=="true"){
				$scope.confirmEmail=false;
				$scope.sendNewPasswd=true;	
			}
			else{alert("zły kod!");}
		}, function(response){
			if(response.status === 401){
				CookieService.logOut();
				alert(response.data);
			} else {
				alert("Błąd serwera, spróbuj ponownie");
			};
		});

		
	};

	$scope.sendPasswd = function(uid, passwd){
		var string = uid + ":" + $scope.tmpPassword.pass1 + ":" + $scope.tmpPassword.pass2;
		if($scope.tmpPassword.pass1 === $scope.tmpPassword.pass2){
			$http({
			url: $rootScope.baseUrl+"/reminder/passwd",
			dataType: "text/plain",
			method: "POST",
			data: string,
			headers: {
				"Content-Type": "text/plain"
			}
		}).then(function(response){
			$scope.response=response;	
			alert("Hasło zostało zmienione! Zaloguj sie ponownie");
			location.reload();
		}, function(response){
			if(response.status === 401){
				CookieService.logOut();
				alert(response.data);
			} else if(response.data === false){
				alert("Hasła nie są identyczne");
			} else {
				alert("Błąd serwera, spróbuj ponownie");
			}
		});
		} else {
			alert("Hasła nie są identyczne!");
		}
	};

	$scope.submitCaptcha = function(){
		$http({
			url: $rootScope.baseUrl+"/reminder/recaptcha",
			dataType: "application/json",
			method: "POST",
			params: {
				gResponse: $scope.response
			},
			headers: {
				"Content-Type": "application/json"
			}
		}).then(function(response){
			if(response.data=="true"){
			//TODO
			}
			else{alert("błąd!");}
		}, function(response){
			if(response.status === 401) {
				CookieService.logOut();
				alert(response.data);
			} else {
				alert("Błąd serwera, spróbuj ponownie");
			};
		});
	};

	$scope.setResponse = function(response){
		$scope.response = response;
		//TODO zmienić typy 
		// obsługa błędu nie tylko złych wpisów w bazie
	}
};
