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
    <title>Личный кабинет кандидата</title>
</head>

<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    
  <jsp:include page="include/header_candidate.jsp"/>

  <main class="mdl-layout__content">
      <div class="page-content demo-card-square mdl-shadow--2dp">
            
            <!--начало таблицы-->
            <div class="content-grid mdl-grid">
          
            <!--1 колонка-->
            <div  class="mdl-cell mdl-cell--2-col mdl-cell--1-col-tablet mdl-cell--1-col-phone" style="border-right: 1px solid lavender;">           
                <ul class="demo-list-item mdl-list">
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">view_list</i>
                      <a href="">Мои сообщения</a>
                    </span>
                  </li>
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">add</i>                      
                      <a href="add_sendors?candidate_id=${candidateUser.id}">Добавить пользователя</a>
                    </span>
                  </li>
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">remove</i>                      
                      <a href="sendors?candidate_id=${candidateUser.id}">Удалить пользователя</a>
                    </span>
                  </li>
                 </ul>
            </div>
            
            <!--2 колонка-->    
            <div class="mdl-cell mdl-cell--10-col mdl-cell--3-col-phone mdl-cell--1-col-phone"> 
                <p class="header_text">Мои сообщения</p>
                <c:if test="${not empty messages}">
                    <c:forEach var="message" items="${messages}">
                        <div class="mdl-textfield mdl-js-textfield" style="width: 100%;">
                          <textarea class="mdl-textfield__input" type="text" rows= "3" id="message_${message.messageId}" 
                                    >${message.messageText}</textarea>
                         <label class="mdl-textfield__label" for="message_${message.messageId}"></label>
                        </div>
                        <button message="${message.messageId}" class="mdl-button mdl-js-button mdl-button--raised">
                        Сохранить
                        </button>
                        <br><br>
                    </c:forEach>                           
                 </c:if>              
            </div>
            </div>
      </div>
  </main>
</div>
<!--jquery-->
<script src="<c:url value="/res/js/jquery.js"/>"></script>
<script>
    $(document).ready(function(){
        $('button').click(function(){
            var message = new Object();
            message["messageId"] = parseInt($(this).attr('message'));
            message["messageText"] = $('#message_' + message["messageId"]).val();
            console.log(JSON.stringify(message));
            console.log($('#candidate_id').val());
            
            $.ajax({
              type: 'POST',
              url: 'update_message',
              data: 'message='+ JSON.stringify(message) + '&candidate_id='+$('#candidate_id').val()         
            });

        });
    });
</script>
</body>

</html>
