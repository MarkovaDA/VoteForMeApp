<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Регистрация пользователя</title>
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/res/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="<c:url value="/res/css/plugins/metisMenu/metisMenu.min.css"/>" rel="stylesheet">
    <!-- Timeline CSS -->
    <link href="<c:url value="/res/css/plugins/timeline.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="/res/css/sb-admin-2.css"/>" rel="stylesheet">
    <!-- Morris Charts CSS -->
    <link href="<c:url value="/res/css/plugins/morris.css"/>" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="<c:url value="/res/font-awesome-4.1.0/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">
        <!-- Navigation -->
        <jsp:include page="../include/navigation.jsp"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Новый Кандидат</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12 col-md-12">
                    ФОРМА!
                    <button type="button" class="btn btn-primary">Добавить кандидата</button>
                </div>
            </div>

        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="<c:url value="/res/js/jquery.js"/>"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/res/js/bootstrap.min.js"/>"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="<c:url value="/res/js/plugins/metisMenu/metisMenu.min.js"/>"></script>
    <!-- Custom Theme JavaScript -->
    <script src="<c:url value="/res/js/sb-admin-2.js"/>"></script>
    <!-- Morris Charts JavaScript -->
    <script src="<c:url value="/res/js/plugins/morris/raphael.min.js"/>"></script>
    <script src="<c:url value="/res/js/plugins/morris/morris.min.js"/>"></script>
    <script src="<c:url value="/res/js/plugins/morris/morris-data.js"/>"></script>
    <script src="<c:url value="/res/js/big.titles.js"/>"></script>

</body>

</html>