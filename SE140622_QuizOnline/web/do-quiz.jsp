<%-- 
    Document   : do-quiz
    Created on : Jan 30, 2021, 8:47:06 AM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="quesList" value="${sessionScope.QUEST_LIST}"/>
<c:set var="quiz" value="${sessionScope.QUIZ}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Do Quiz</title>
        <link rel="stylesheet" href="public/css/bootstrap.css">
        <link rel="stylesheet" href="public/css/mycss.css">
        <link rel="stylesheet" href="public/css/simplePagination.css">
        <link rel="stylesheet" href="public/css/timer.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
        <style>
            pre {
                display: block;
                font-family: inherit !important;
                white-space: pre;
                margin: 1em 0;
                overflow-x:hidden !important;
                white-space: pre-wrap !important; 
                word-break: break-word !important;
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
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6">
                <div id="page-nav"></div>
            </div>
            <div class="col-sm-2">
                <form action="submitQuiz">
                    <button type="submit" class="btn my-btn" style="border: 1px solid #000000; margin-top: 6.8rem;">Submit</button>
                </form>
            </div>
            <div class="col-sm-2">
                <input type="hidden" id="time-limit" value="${sessionScope.TIME_LIMIT}">
                <input type="hidden" id="time-pass" value="${sessionScope.TIME_PASS}">
                <div id="app"></div>
            </div>
        </div>
    </div>
    <c:forEach var="ques" items="${quesList}" varStatus="counter">
        <div class="container mt-5 paginate">
            <div class="d-flex justify-content-center row">
                <div class="col-md-10 col-lg-10">
                    <div class="border">
                        <div class="question bg-white p-3 border-bottom">
                            <div class="d-flex flex-row justify-content-between align-items-center mcq">
                                <h4>MCQ Quiz</h4><span>(${counter.count} of ${quiz.totalQues})</span>
                            </div>
                        </div>
                        <div class="question bg-white p-3 border-bottom">
                            <div class="d-flex flex-row align-items-center question-title">
                                <h3 class="text-danger"><i class="fas fa-question"></i></h3>
                                <pre><h5 class="mt-1 ml-2">${ques.question.content}</h5></pre>
                            </div>
                            <c:forEach var="ans" items="${ques.answers}">
                                <div class="ans ml-2">
                                    <label class="radio">
                                        <pre><input type="radio" name="${ques.question.id}" value="${ans.id}"
                                            <c:if test="${sessionScope.ANS_LIST[ques.question.id] eq ans.id}">checked</c:if>
                                        ><span>${ans.content}</span></pre>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    
    <script src="public/js/jquery.min.js"></script>
    <script src="public/js/timer.js"></script>
    <script src="public/js/jquery.simplePagination.js"></script>
    <script src="public/js/myjs.js"></script>
    <script>
        $('input:radio').click(function() {
            var quesId = $(this).attr('name');
            var ansId = $(this).val();
            $.ajax({
                type: "GET",
                url: "doQuiz",
                data: {"quesId" : quesId, "ansId" : ansId}
            });
        });
    </script>
    </body>
</html>
