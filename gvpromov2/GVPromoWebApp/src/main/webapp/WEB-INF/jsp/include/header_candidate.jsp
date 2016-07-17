<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- Navigation -->
 <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Поддержите своих кандидатов</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer">
          <input type="hidden" id="candidate_id" value="${candidateUser.id}">
      </div>  
      
      <nav class="mdl-navigation">
        <a class="mdl-navigation__link">${candidateUser.name}</a>
      </nav>
    </div>
 </header>


