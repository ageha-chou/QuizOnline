<%-- 
    Document   : quiz-history
    Created on : Jan 31, 2021, 9:20:11 AM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="subList" value="${requestScope.SUBJECT_LIST}"/>
<c:set var="quizList" value="${requestScope.QUIZ_LSIT}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz History</title>
        <link rel="stylesheet" href="public/css/bootstrap.css">
        <link rel="stylesheet" href="public/css/mycss.css">
        <link rel="stylesheet" href="public/css/simplePagination.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
        <style>
        #page-nav{
            padding: 2rem 2rem 0 2rem;
            text-align: right;
        }
        
        .form-control{
            margin: 1rem 2rem 0rem 0;
        }
        </style>
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
                  <li class="nav-item active">
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
                    <h1>Quiz History</h1>
                    <div class="row">
                        <div class="col-sm-4">
                            <select class="form-control" id="subject" name="subject" onmousedown="this.value='';" onchange="selectSubject(this.value);">
                                <option value="all">All subject</option>
                                <c:forEach var="sub" items="${subList}">
                                    <option value="${sub.id}"
                                        <c:if test="${param.subId ne 'all'}">
                                            <c:if test="${param.subId eq sub.id}">selected</c:if>
                                        </c:if>
                                    >${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <c:if test="${not empty quizList}">
                            <div class="col-sm-8">
                                <div id="page-nav"></div>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${not empty quizList}">
                        <table class="table table-hover">
                            <thead>
                                <tr class="paginate">
                                    <th>#</th>
                                    <th>Subject</th>
                                    <th>Correct answers</th>
                                    <th>Total answers</th>
                                    <th>Score</th>
                                    <th>Submitted Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="result" items="${quizList}" varStatus="counter">
                                <tr class="paginate">
                                    <th>${counter.count}</th>
                                    <td>${result.subjectId.name}</td>
                                    <td>${result.correctQues}</td>
                                    <td>${result.totalQues}</td>
                                    <td><fmt:formatNumber value = "${result.correctQues/result.totalQues*10.0}" type = "number" maxFractionDigits="2"/>/10</td>
                                    <td><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${result.submittedDate}" /></td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty quizList}">
                        <h1>Not found</h1>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="public/js/bootstrap.js"></script>
        <script src="public/js/jquery.min.js"></script>
        <script src="public/js/jquery.simplePagination.js"></script>
        <script>
           jQuery(function($){
                var pageParts = $(".paginate");
                var numPages = pageParts.length;
                var perPage = 10;
                pageParts.slice(perPage).hide();
                $("#page-nav").pagination({
                    items: numPages,
                    itemsOnPage: perPage,
                    cssStyle: "my-theme",
                    
                    onPageClick: function(pageNum){
                        var start = perPage * (pageNum - 1);
                        var end = start + perPage;
                        pageParts.hide().slice(start, end).show();
                    }
                });
            });
            
            function selectSubject(value){
                window.location = "loadQuizHistory?subId=" + value;
            }
        </script>
    </body>
</html>
