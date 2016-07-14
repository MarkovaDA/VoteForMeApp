<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>SB Admin 2 - Bootstrap Admin Theme</title>
  <!-- Bootstrap Core CSS -->
  <link href="<c:url value="/res/css/bootstrap.min.css"/>" rel="stylesheet">
  <!-- Custom CSS -->
  <link href="<c:url value="/res/css/sb-admin-2.css"/>" rel="stylesheet">
</head>

<body>

<div class="container" style="margin-top: 100px;">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <div class="login-panel panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">Вход</h3>
        </div>
        <div class="panel-body">
            <a class="btn btn-primary" href="${authHref}">Войти</a>        
        </div>
      </div>
    </div>
  </div>
</div>
            
<!-- jQuery -->
<script src="<c:url value="/res/js/jquery.js"/>"></script>
<script src="<c:url value="/res/js/sb-admin-2.js"/>"></script>

</body>
</html>