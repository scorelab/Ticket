app.controller('MainController', function($scope, $location, $anchorScroll, AuthenticationService){
	$scope.show = function() {
		if(AuthenticationService.isLoggedIn()){
			$scope.showvalue= $.cookie("q_user");//$cookies.q_user;
			return true;
		}
		else {
			return false;
		}
	},
	$scope.logout = function() {
		AuthenticationService.logout();
		$location.path('/login');
	}
});

app.controller('LoginController', function($scope, AuthenticationService){
	$scope.credentials = {username:"", password:""};
	$scope.login = function() {
		AuthenticationService.login($scope.credentials);
	};
});

app.controller('BookingController', function($scope,$http,getdatemin,FormErrorService){
	$scope.bookcredentials = {id:"", journeydate:"", nic:"", contactphone:"", contactEmail:"", selectedFrom:"", selectedTo:""};

	$scope.bookcredentials.id="0";
	$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data,status,headers,config){
		$scope.mindate = data;//getdatemin.getdate($scope.bookcredentials);
	}).error(function(data,status,headers,config){
		alert("Server Error ",status);
	});

	$scope.getFrom = function(){
		$scope.bookcredentials.id="1";
		$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data,status,headers,config){
			$scope.fromvalue = data;
		}).error(function(data,status,headers,config){
			alert("Server Error ",status);
		});
	}

	$scope.getTo = function(){
		$scope.bookcredentials.id="2";	
		$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data,status,headers,config){
			$scope.tovalue = data;
		}).error(function(data,status,headers,config){
			alert("Server Error ",status);
		});
	}

	$scope.booking = function() {
		alert('Im in ');
		var status = FormErrorService.checkerror($scope.addbooking);
		if(status){
			$scope.nextstatus = 'success';
		}
	};
});

app.controller('CancelController', function(){
	
});

app.controller('InquiryController', function(){
	
});