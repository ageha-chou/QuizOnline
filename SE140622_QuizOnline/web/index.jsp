<%-- 
    Document   : index
    Created on : Jan 25, 2021, 6:56:20 PM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="subList" value="${requestScope.SUBJECT_LIST}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz online</title>
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
                  <li class="nav-item active">
                    <a class="nav-link" href="indexPage">Home</a>
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
        <div class="container mt-5 my-pagination">
            <div class="d-flex justify-content-center row">
                <div class="col-md-10 col-lg-10">
                    <div class="row">
                        <c:forEach var="sub" items="${subList}">
                            <div class="col-sm-4">
                                <div class="border">
                                    <div class="question bg-white p-3 border-bottom">
                                       <div class="d-flex flex-row justify-content-between align-items-center mcq">
                                            <h5>Subject: ${sub.name}</h5>
                                       </div>
                                    </div>
                                    <div class="question bg-white p-3 border-bottom">
                                        <p>Number of Questions: ${sub.noOfQuestion}</p>
                                        <p>Time: ${sub.quizTime} minutes</p>
                                        <form action="loadQuizQuestion" method="POST">
                                            <input type="hidden" name="subjectId" value="${sub.id}"/>
                                            <input type="hidden" name="numOfquest" value="${sub.noOfQuestion}"/>
                                            <input type="hidden" name="timeLimit" value="${sub.quizTime}"/>
                                            <center><button type="submit" class="btn my-btn">Do Quiz</button></center>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
