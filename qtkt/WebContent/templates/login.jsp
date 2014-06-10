<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class='col-sm-offset-2'>
	<%
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length > 1) {
			response.sendRedirect("localhost:8080/qtkt/#/");
		}
	%>
	<form class='form-horizontal' role='form' ng-submit='login()'>
		<div class="form-group">
			<label class="col-xs-10 label label-default">Login Here</label>
		</div>
		<div class='form-group'>
			<label for='username' class='col-sm-2 label label-info'><h5>Username</h5></label>
			<div class='col-sm-8 left-inner-addon'>
				<i class='glyphicon glyphicon-user'></i> <input type='text'
					class='form-control' placeholder=' Username' name='username'
					ng-model="credentials.username" required />
			</div>
		</div>
		<div class='form-group'>
			<label for='password' class='col-sm-2 label label-info'><h5>Password</h5></label>
			<div class='col-sm-8 left-inner-addon'>
				<i class='glyphicon glyphicon-lock'></i> <input type='password'
					class='form-control' placeholder=' Password' name='password'
					ng-model="credentials.password" required />
			</div>
		</div>
		<div class='form-group'>
			<button class='col-sm-10 btn btn-large btn-primary' type='submit'>
				<h5>Log In</h5>
			</button>
		</div>
	</form>
</div>