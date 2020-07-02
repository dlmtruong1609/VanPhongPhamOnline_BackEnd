<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>Spring Boot Security</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row d-flex justify-content-center">
			<div class="col-md-6">
				<h2 class="mb-4">Admin</h2>
				<div class="text-center">
					<div class="alert alert-warning" ${message != null ? '' : 'hidden' }>
						${message }
					</div>
				</div>
				<form name='login-form' action="/api/v1/loginAdmin"
					modelAttribute="account" method='POST'>
					<div class="form-group">
						<label for="username">Email address:</label> <input required="required" type="text"
							class="form-control" placeholder="Nhập tên tài khoản"
							name="username" id="username">
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label> <input required="required" type="password"
							class="form-control" placeholder="Nhập password" name='password'
							id="pwd">
					</div>
					<button type="submit" class="btn btn-primary">Login</button>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>
</body>