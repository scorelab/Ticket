// setter
var app = angular.module("app",['ngRoute','ngCookies']);

app.config(function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl: 'templates/login.jsp',
		controller: 'LoginController'
	});

	$routeProvider.when('/booking',{
		templateUrl: 'templates/booking.html',
		controller: 'BookingController'
	});

	$routeProvider.when('/cancel',{
		templateUrl: 'templates/cancel.html',
		controller: 'CancelController'
	});

	$routeProvider.when('/inquiry',{
		templateUrl: 'templates/inquiry.html',
		controller: 'InquiryController'
	});

	$routeProvider.otherwise({ redirectTo: '/'});
});  

app.run(function($rootScope, $location, AuthenticationService, FlashService){
	var routesThatRequireAuth = ['/cancel'];
	$rootScope.$on('$routeChangeStart', function(event, next, current){
		if($.inArray($location.path(),routesThatRequireAuth)!==-1 && !AuthenticationService.isLoggedIn()){
			$location.path('/login');
			FlashService.show("Please login to continue.");
		}else if($location.path()!=='/login'){
			FlashService.clear();
		}

		if($location.path() === '/login' && AuthenticationService.isLoggedIn()){
			$location.path('/');
		}
	});
});

app.factory('FlashService', function($rootScope){
	return {
		show: function(message){
			$rootScope.flash = message;
		},
		clear: function(){
			$rootScope.flash = "";
		}
	};
});

app.factory('CacheAndUncache', function($cookies, SessionService){
	return {
		cacheSession: function(data){
			$.cookie("q_user", data, { expires: 7});
			//$cookies.q_user = data;
			//SessionService.set('authenticated', true);
		},
		uncacheSession: function(){
			//SessionService.unset('authenticated');
		},
	};
});

app.factory('AuthenticationService', function($http, $location, SessionService, CacheAndUncache, FlashService){
	return {
		login: function(credentials) {
			$http.post('http://localhost:8080/qtkt/auth/login',credentials).success(function(data,status,headers,config){
				CacheAndUncache.cacheSession(data);
				FlashService.clear();
				if (data == '') {
					FlashService.show('Username or Password is wrong');
				}else{
				$location.path('/');
				}
			}).error(function(data){
				alert('fail');
			});
		},
		logout: function() {
			return $.removeCookie('q_user');
		},
		isLoggedIn: function() {
			//return $cookies.q_user;
			return $.cookie('q_user');
			//return SessionService.get('authenticated');
		}
	};
});

app.factory('SessionService', function(){
	return {
		get: function(key){
			return sessionStorage.getItem(key);
		},
		set: function(key, val){
			return sessionStorage.setItem(key, val);
		},
		unset: function(key){
			return sessionStorage.removeItem(key);
		}		
	};
});

app.factory('getdatemin', function($http){
	return {
		getdate: function(credentials){
			return $http.post('http://localhost:8080/qtkt/auth/FromGet',credentials).success(function(data,status,headers,config){
				alert(data);
				return data;
			}).error(function(data,status,headers,config){
				alert("Server Error ",status);
				return null;
			});
		}
	};
});

app.factory('FormErrorService', function(FlashService){
	return {
		checkerror: function(form) {
			if(form.$valid){
				FlashService.clear();
				return true;
			}
			else if(!form.nic.$valid){
				FlashService.show('Check NIC Value');
				return false;
			}
		},
		displayerror: function(message) {
			FlashService.show(message);
		},
		clearerror: function() {
			FlashService.clear();
		},
		backme: function(next) {
			var r = confirm("Do you really want to go back..");
			if(r) {
				FlashService.clear();
				return next-1;
			}
			else {
				return next;
			}
		}
	};
});

app.factory('PrintService', function(){
	return {
		print: function(div) {
			var printContents = document.getElementById(div).innerHTML;
			var originalContent = document.body.innerHTML;
			document.body.innerHTML = printContents;
			window.print();
			document.body.innerHTML = originalContent;
		}
	};
});