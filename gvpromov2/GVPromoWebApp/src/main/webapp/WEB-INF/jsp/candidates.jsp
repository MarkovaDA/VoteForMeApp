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
    <link href="<c:url value="/res/css/custom_style.css"/>" rel="stylesheet">
    <script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
    
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
                      <a id="add_candidate">Добавить кандидата</a>
                    </span>
                  </li>
                  <li class="mdl-list__item">
                    <span class="mdl-list__item-primary-content">
                      <i class="material-icons mdl-list__item-avatar">remove</i>                      
                      <a href="">Удалить кандидата</a>
                    </span>
                  </li>
                 </ul>
            </div>
            
            <!--2 колонка-->    
            <div class="mdl-cell mdl-cell--10-col mdl-cell--3-col-phone mdl-cell--1-col-phone">
                                
             <p class="header_text">Ваши кандидаты </p>                
             <jsp:include page="include/employee_list.jsp"/>  
            </div>
            </div>
      </div>
  </main>
  <div>
  <script src="<c:url value="/res/js/jquery.js"/>"></script>
  <script src="<c:url value="/res/js/delete_employee.js"/>"></script>
  <script src="<c:url value="/res/js/search.js"/>"></script>
  <script>
      $(document).ready(function(){
          search();
          $('#add_candidate').click(function(){
              var location = window.location.href;
              location = location.substring(0, location.indexOf('/candidates'));
              window.location = location;              
          });
          delete_emplyee("delete_candidate");         
      });
  </script>




</div>
</body>
</html>
