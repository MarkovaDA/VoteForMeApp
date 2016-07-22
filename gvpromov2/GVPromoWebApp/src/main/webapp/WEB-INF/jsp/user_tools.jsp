<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!--библиотека вк-->
        <script type="text/javascript" src="http://vkontakte.ru/js/api/openapi.js"></script>
        <script src="<c:url value="/res/js/jquery.js"/>"></script>
    </head>
    <body>
        <h3>Будущий функционал юзера</h3>
        <p class="user"></p>
        <button class="mdl-button mdl-js-button" id="vk_login_btn">
            Войти
        </button>
        
        <button class="mdl-button mdl-js-button" id="vk_unlogin_btn">
            Выйти
        </button>
        <br><br>
        
        <button class="mdl-button mdl-js-button" id="send_message_btn">
            Отправить сообщение
        </button>
        
        <script>
        $(document).ready(function(){
            //https://geektimes.ru/post/112923/
            VK.init({
                apiId: 5546142
            });
            
            $('#vk_login_btn').click(function(event)
            {
                event.preventDefault();
                //https://new.vk.com/dev/permissions
                VK.Auth.login(function(data){
                    var session_params = data.session;
                    //очень насыщенный объект data
                    var user = session_params["user"];
                    console.log(data);
                    $('.user').text(user.first_name + " " + user.last_name);
                    console.log(session_params);
                }, VK.access.messages); //права на отправку сообщений
            });
            
            $('#vk_unlogin_btn').click(function(event)
            {
                event.preventDefault();
                VK.Auth.logout(function(){
                });
            });
            $('#send_message_btn').click(function(){
                sendMessage();
            });
            
            VK.Observer.subscribe('auth.login', function(response){
                    console.log("авторизация завершена");
            });
            VK.Observer.subscribe('auth.sessionChange', function(response){
                    console.log("изменение параметров");
            });
            
            VK.Auth.getLoginStatus(function(response){
                if(response.session)
                {
                    console.log(response.session);    // пользователь авторизирован
                }
                else
                {
                        // пользователь не авторизирован
                }
            });
            
                
        });
        
        function sendMessage(){
            //пример вызова функции
            //https://vkapi.zf-projects.ru/methods-list
            VK.Api.call('messages.send', 
            {               
                user_id: '262591631',
                message: 'тестовое сообщение самой себе',
                access_token: 'cc087d230e8b55ccb55cef48a4a3cfbf'
            }, 
            function(data){
               // обработка списка друзей
               console.log(data);
            });
                
        }
          
        </script>
    </body>
</html>
