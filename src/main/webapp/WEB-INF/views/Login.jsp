<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>Spring Boot Security</title>
</head>
<body>
  <h2>Form Login1</h2>
  <form name='login-form' action="/api/v1/loginAdmin" modelAttribute="account" method='POST'>
    <table>
      <tr>
        <td>Username:</td>
        <td><input type='text' name='username' value=''></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type='password' name='password' /></td>
      </tr>
      <tr>
        <td><input name="submit" type="submit" value="submit" /></td>
      </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  </form>
</body>
