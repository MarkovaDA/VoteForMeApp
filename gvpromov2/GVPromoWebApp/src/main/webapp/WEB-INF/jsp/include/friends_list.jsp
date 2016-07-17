<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


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

 <button class="mdl-button mdl-js-button mdl-button--raised">
    Добавить
 </button>
