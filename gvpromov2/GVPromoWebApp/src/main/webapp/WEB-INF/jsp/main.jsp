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
    <link href="<c:url value="/res/css/custom_style.css"/>" rel="stylesheet">
    <title>Панель администратора</title>
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    
  <jsp:include page="include/header.jsp"/>

  <main class="mdl-layout__content">
      <div class="page-content demo-card-square mdl-shadow--2dp">
            
            <!--начало таблицы-->
            <div class="content-grid mdl-grid">
          
            <!--1 колонка-->
            <div  class="mdl-cell mdl-cell--2-col mdl-cell--1-col-tablet mdl-cell--1-col-phone" style="border-right: 1px solid lavender;">           
                <ul class="demo-list-item mdl-list">
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">add</i>
                      <a href="">Добавить кандидата</a>
                    </span>
                  </li>
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">remove</i>                      
                      <a href="candidates">Удалить кандидата</a>
                    </span>
                  </li>
                 </ul>
            </div>
            
            <!--2 колонка-->    
            <div class="mdl-cell mdl-cell--10-col mdl-cell--3-col-phone mdl-cell--1-col-phone">
                <div class="mdl-textfield mdl-js-textfield" style="width:100% !important;">
                    <input style="width:100%;" class="mdl-textfield__input" type="text" id="uid_text" placeholder="vk uid">
                    <label class="mdl-textfield__label" for="uid_text"></label>
                </div>
                <jsp:include page="include/friends_list.jsp"/>                
            </div>
            </div>
      </div>
  </main>
</div>
<!--jquery-->
<script src="<c:url value="/res/js/jquery.js"/>"></script>
<script src="<c:url value="/res/js/search.js"/>"></script>
<script src="<c:url value="/res/js/add_from_friends.js"/>"></script>
<script>
    $(document).ready(function()
    {            
        search();
        addFromFriends("store_candidates", false);
    });
</script>
</body>

</html>