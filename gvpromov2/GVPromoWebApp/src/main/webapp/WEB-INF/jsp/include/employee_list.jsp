<!--список подчиненных,подлежащих удалению (у кандидата или у администратора-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- поле поиска-->

<div class="mdl-textfield">
    <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
      <i class="material-icons">search</i>
    </label>
    <input style="width:100% !important; margin-left: 30px;" class="mdl-textfield__input" type="text" id="search" placeholder="search">
    <label class="mdl-textfield__label" for="search"></label>
</div>

<c:if test="${not empty employees}">    
<ul class="demo-list-control mdl-list" id="friends_list">
    <c:forEach var="employee" items="${employees}">                            
        <li class="mdl-list__item">
            <span class="mdl-list__item-primary-content">
                <i class="material-icons  mdl-list__item-avatar">person</i>
                <span class="first_name">${employee.name}</span> 
            </span>
            <span class="mdl-list__item-secondary-action">
               <button class="mdl-button mdl-js-button mdl-button--raised" id="btn_add" employee_id="${employee.id}">
                Удалить
               </button>
            </span>
        </li>
    </c:forEach>

</ul>
</c:if>
