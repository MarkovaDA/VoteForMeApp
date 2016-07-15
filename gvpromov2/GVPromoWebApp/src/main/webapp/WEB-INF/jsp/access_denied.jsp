<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
    <script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
    
    <title>Панель администратора</title>
</head>
<style>
.demo-card-square.mdl-card {
  width: 320px;
  height: 320px;
  margin: 100px auto !important;
}
.demo-card-square > .mdl-card__title {
  color: darkblue;
  background: url('<c:url value="/res/images/no_action.png"/>') center center  no-repeat lavender;
  text-align: center !important;
}
.mdl-card__title-text{
    text-align:center !important;
}
</style>
<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    
  <jsp:include page="include/header.jsp"/>

  <main class="mdl-layout__content">
      <div class="page-content">
            

            <div class="content-grid mdl-grid">         
                <div  class="mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--8-col-phone">           
                    <div class="demo-card-square mdl-card mdl-shadow--2dp">
                        <div class="mdl-card__title mdl-card--expand">
                        </div>
                        <div class="mdl-card__supporting-text">
                              Вы не являетесь администратором данного сервиса
                        </div>
                        <div class="mdl-card__actions mdl-card--border" style="text-align: center;">
                        <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                              Доступ запрещен
                        </a>
                        </div>
                    </div>
                </div>
            </div>
      </div>
  </main>
</div>
</body>

</html>
