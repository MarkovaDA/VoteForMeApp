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
    <style>
        .mdl-list__item:hover{
            background: lavender;
        }
    </style>
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    
  <jsp:include page="include/header.jsp"/>

  <main class="mdl-layout__content">
      <div class="page-content demo-card-square mdl-shadow--2dp">
            
            <!--начало таблицы-->
            <div class="content-grid mdl-grid">
          
            <!--1 колонка-->
            <div  class="content-column mdl-cell mdl-cell--2-col mdl-cell--1-col-tablet mdl-cell--1-col-phone" style="border-right: 1px solid lavender;">
            
                <label class="admin_login">
                    
                </label>
            <ul class="demo-list-item mdl-list">
              <li class="mdl-list__item">
                <span class="mdl-list__item-primary-content">
                  Мои друзья
                </span>
              </li>
              <li class="mdl-list__item">
                <span class="mdl-list__item-primary-content">
                  Кандидаты
                </span>
              </li>
             </ul>
            </div>
            
            <!--2 колонка-->    
            <div class="mdl-cell mdl-cell--10-col mdl-cell--3-col-phone mdl-cell--1-col-phone">
                <div class="mdl-textfield mdl-js-textfield" style="width:100% !important;">
                    <input style="width:100%;" class="mdl-textfield__input" type="text" id="sample1">
                    <label class="mdl-textfield__label" for="sample1">идентификатор</label>
                </div>
                <button class="mdl-button mdl-js-button mdl-button--raised" id="btn_add">
                    Добавить
                </button>
                
                
                <c:if test="${not empty friends}">

                    <ul class="demo-list-control mdl-list" id="friends_list">
			<c:forEach var="friend" items="${friends}">
                            <li class="mdl-list__item">
                                <input type="hidden" value="${friend.uid}" class="uid"/>
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
			</c:forEach>
                    </ul>
                 </c:if>
                 <button class="mdl-button mdl-js-button mdl-button--raised">
                    Добавить
                 </button>
            </div>
            </div>
      </div>
  </main>
</div>
<!--jquery-->
<script src="<c:url value="/res/js/jquery.js"/>"></script>
<script>
    $(document).ready(function()
    {
        var candidates = new Array();
        
        $('input[type="checkbox"]').change(function() {
            if ($(this).prop("checked")) 
            {   
                //выполнить эту функцию для каждого li
                var li = $(this).parent().parent().parent();
                var index = li.index();                           
            }       
        });
        
        function addObject(index)
        {   
            var candidate = new Object();
            var uid = $('#friends_list').find(".uid").eq(index).val();
            var first_name = $('#friends_list').find(".first_name").eq(index).html();
            var last_name = $('#friends_list').find(".last_name").eq(index).html();
            console.log(uid + first_name + last_name);
            candidate["uid"] = uid;
            candidate["first_name"] = first_name;
            candidate["last_name"] = last_name;
            candidates.push(candidate);
        }
        
        $('button').click(function(){
            $('#friends_list li').each(function(index, value){
                 
                 var checkbox = $(this).find('.mdl-checkbox__input').eq(0);
                 if ($(checkbox).prop("checked")){
                     addObject(index);
                 }
            });
            console.log(JSON.stringify(candidates));
        });
    
    });
</script>
</body>

</html>