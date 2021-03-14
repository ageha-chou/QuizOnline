<%-- 
    Document   : show-result
    Created on : Jan 31, 2021, 12:13:58 AM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="result" value="${requestScope.RESULT}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Result</title>
        <link rel="stylesheet" href="public/css/bootstrap.css">
        <link rel="stylesheet" href="public/css/mycss.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    </head>
    <body class="padding-body">
        <nav class="navbar navbar-expand-sm fixed-top nav-bg">
            <!-- Brand/logo -->
            <a class="navbar-brand" style="text-decoration: none;">
              <i class="fas fa-university fa-2x my-icon"></i>
            </a>

            <!-- Links -->
            <div class="collapse navbar-collapse" id="navb">
                <ul class="navbar-nav mr-auto">
                  <li class="nav-item">
                    <a class="nav-link" href="loadUserPage">Home</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="loadQuizHistory">Quiz History</a>
                  </li>
                </ul>
                <ul class="navbar-nav my-2 my-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Welcome, ${sessionScope.USER.name}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container mt-5">
            <div class="d-flex justify-content-center row">
                <div class="col-md-10 col-lg-10">
                    <div class="border">
                        <div class="question bg-white p-3 border-bottom">
                            <div class="d-flex flex-row justify-content-between align-items-center mcq">
                                <h4>Subject: ${result.subjectId.name}</h4><span>Submitted date: <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${result.submittedDate}" /></span>
                            </div>
                        </div>
                        <div class="question bg-white p-3 border-bottom">
                            <div class="d-flex flex-row align-items-center question-title">
                                <h3 class="text-danger">Score:</h3>
                                <h5 class="mt-1 ml-2">${result.correctQues/result.totalQues*10.0}/10</h5>
                            </div>
                            <div class="ans ml-2">
                                <p><i class="fas fa-check fa-2x correct-icon"></i><span class="ans-text"> ${result.correctQues} question(s)</span></p>
                            </div>
                            <div class="ans ml-2">
                                <p><i class="fas fa-times fa-2x wrong-icon"></i><span class="ans-text"> ${result.totalQues - result.correctQues} questions(s)</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
