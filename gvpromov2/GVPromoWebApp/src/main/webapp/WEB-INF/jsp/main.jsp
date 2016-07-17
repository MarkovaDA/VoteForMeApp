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
                <button class="mdl-button mdl-js-button mdl-button--raised" id="btn_add">
                    Добавить
                </button>
                
                <p class="header_text">Выберите среди друзей:</p>
                
                <div class="mdl-textfield mdl-js-textfield" style="width:100% !important;">
                    <input style="width:100%;" class="mdl-textfield__input" type="text" id="search" placeholder="search">
                    <label class="mdl-textfield__label" for="search"></label>
                </div>
                
                <c:if test="${not empty friends}">

                    <ul class="demo-list-control mdl-list" id="friends_list">
			<c:forEach var="friend" items="${friends}">
                            <c:if test="${friend.status != false}">
                            
                            <li class="mdl-list__item">
                                <input type="hidden" value="${friend.uid}" class="uid"/>
                                <input type="hidden" value="${friend.city}" class="city"/>
                                <span class="mdl-list__item-primary-content">
                                    <i class="material-icons  mdl-list__item-avatar">person</i>
                                    <span class="first_name">${friend.first_name}</span> 
                                    <span style="margin-left: 10px;" class="last_name"> ${friend.last_name}</span>
                                </span>
                                <span class="mdl-list__item-secondary-action">
                                    <label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="list-checkbox-${friend.last_name}">
                                    <input type="checkbox" id="list-checkbox-${friend.last_name}" class="mdl-checkbox__input"/>
                                    </label>
                                </span>
                            </li>
                            
                            </c:if>
			</c:forEach>
                            
                    </ul>
                 </c:if>
                
            </div>
            </div>
      </div>
  </main>
</div>
<!--jquery-->
<script src="<c:url value="/res/js/jquery.js"/>"></script>
<script src="<c:url value="/res/js/add_from_friends.js"/>"></script>
<script>
    $(document).ready(function()
    {            
        //обособить в отдельную функцию скрипта с передачей параметра сортировки
        $('#search').keyup(function(){
            var pattern = $(this).val();
            
            $('#friends_list li').each(function(index, value){
                var name = $(this).find('.first_name').eq(0).html() + $(this).find('.last_name').eq(0).html();
                if (name.indexOf(pattern) !== 0)
                    $(this).hide();
                else $(this).show();
            });
        });  
        addFromFriends("store_candidates", false);
    });
</script>
</body>

</html>