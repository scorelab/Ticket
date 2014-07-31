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

app.controller('BookingController', function($scope,$http,getdatemin,FormErrorService,PrintService){
	$scope.bookcredentials = {id:"", journeydate:"", nic:"", contactphone:"", contactEmail:"", selectedFrom:"", selectedTo:"", trainTime:"", selectedClass:"", seats:""};
	$scope.next=1;
	$scope.rcre={};
	$scope.im={};

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

	$scope.getTrainandTime = function(){
		$scope.bookcredentials.id="3";
		$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data,status,headers,config){
			$scope.trainvalue = data;
		}).error(function(data,status,headers,config){
			alert("Server Error ",status);
		});
	}

	$scope.getClass = function() {
		$scope.bookcredentials.id="4";
		$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data,status,headers,config){
			$scope.classvalue = data;
		}).error(function(data,status,headers,config){
			alert("Server Error ",status);
		});
	}

	$scope.checkBooking = function() {
		var status = FormErrorService.checkerror($scope.addbooking);
		if(status){
			$scope.bookcredentials.id="5";
			$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data) {
				if(data == 'true') {
					$scope.next = 2;				
				} else if(data == 'false'){
					$scope.next = 1;
					FormErrorService.displayerror("Required Seats are not Available");
				}
			}).error(function(data){
				alert("Server Error "+status);
			});
		}
	}

	$scope.payment = function() {
		alert("payment");
	}
	$scope.reserve = function() {
		var r = confirm("Do you really want to reserve..");
		if (r == true) {
    		$scope.bookcredentials.id="6";
			$http.post('http://localhost:8080/qtkt/auth/FromGet',$scope.bookcredentials).success(function(data) {
				$scope.rcre = data;
				if ($scope.rcre.status == 0) {
					FormErrorService.displayerror('You cannot Reserve a Ticket within 2 Days From the Journey Date');
				} else if($scope.rcre.status == 1) {
					FormErrorService.displayerror('Sorry Your Reserve Request is Currently Unavilable');
				} else if ($scope.rcre.status == 2){
					FormErrorService.clearerror();
					FormErrorService.displayerror('Ticket Reserved. Remember This is not a valid Ticket, Pay Money to Make Your Ticket Valid');
					$scope.next = 3;
				};
			}).error(function(data){
				alert("Server Error "+status);
			});
		} else {
		}
	}

	$scope.back = function() {
		var r = confirm("Do you really want to go back..");
		if(r) {
			FormErrorService.clearerror();
			$scope.next = 1;//!$scope.next;
		}
	}

	$scope.printdiv = function() {
		PrintService.print('printthis');
	}
	window.onbeforeunload = function() {
  		return "Data will be lost if you leave the page, are you sure?";
	};
});

app.controller('InquiryController', function($scope,$http,FormErrorService,PrintService){
	//window.scope = $scope;
	$scope.next = 1;
	$scope.tcr = {};
	$scope.getDetails = function(){
		if (!$scope.details.$valid) {
			FormErrorService.displayerror("Invalid Ticket Number");
		}
		else {
			FormErrorService.clearerror();
			$http.get('http://localhost:8080/qtkt/auth/TicketServlet?ticketno='+$scope.ticketno).success(function(data){
				$scope.tcr = data;
				if($scope.tcr.status == 0) {
					FormErrorService.displayerror('Sorry Requested Ticket Not Found');
				}
				else if ($scope.tcr.status == 1) {
					FormErrorService.clearerror();
					$scope.next = 2;
					if ($scope.tcr.valid == "f") {
						FormErrorService.displayerror("Note: Ticket Expired or Canceled");
					} else {
						if ($scope.tcr.btype == "r") {
							FormErrorService.displayerror("Note: Reserved Ticket")
						}
					}
				}
			}).error(function(status){
				alert('Server Error ');
			});
		}
	}

	$scope.printdiv = function() {
		PrintService.print('printthis');
	}

	$scope.back = function() {
		$scope.next = FormErrorService.backme($scope.next);
	}
});

app.controller('CancelController', function($scope,$http,FormErrorService,PrintService){
	$scope.max = "";
	$scope.next = 1;
	$scope.ntcr = {};
	$scope.ccr = {};
	window.scope = $scope;
	$scope.doCancel = function(){
		if (!$scope.cancel.$valid) {
			FormErrorService.displayerror("Invalid Ticket Number");
		}
		else {
			FormErrorService.clearerror();
			$http.get('http://localhost:8080/qtkt/auth/TicketServlet?ticketno='+$scope.ticketno).success(function(data){
				$scope.ccr = data;
				if($scope.ccr.status == 0) {
					FormErrorService.displayerror('Sorry Requested Ticket Not Found');
				}
				else if ($scope.ccr.status == 1) {
					FormErrorService.clearerror();
					//$scope.next = 2;
					if ($scope.ccr.valid == "f") {
						FormErrorService.displayerror("Note: Ticket Expired or Canceled");
					} else {
						$scope.allowToCancel();
					}
				}
			}).error(function(status){
				alert('Server Error ');
			});
		}
	}

	$scope.allowToCancel = function(){
		$scope.next = 2;
		$scope.max = $scope.ccr.seats;
	}

	$scope.sendCancel = function(){
		if (!$scope.scancel.$valid) {
			FormErrorService.displayerror("Check No of Canceling Ticket/s");
		}
		else {
			$scope.sccr.status = 0;
			$scope.sccr.ticketno = $scope.ccr.ticketno;
			$http.post('http://localhost:8080/qtkt/auth/TicketServlet',$scope.sccr).success(function(data){
				$scope.ntcr = data;
				if ($scope.ntcr.status == 0) {
					FormErrorService.displayerror("Cancelation not Success");
				}
				else if($scope.ntcr.status == 1){
					FormErrorService.clearerror();
					alert("Ticket Successfully Canceled. Printout the new Ticket");
					$scope.next = 3;
				} 
				else {
					FormErrorService.displayerror("Something went wrong, Ticket Cancelation Failed");
				}
			}).error(function(status){
				alert('Server Error ');
			});
		}
	}

	$scope.printdiv = function() {
		PrintService.print('printthis');
	}

	$scope.back = function() {
		$scope.next = FormErrorService.backme($scope.next);
	}
});