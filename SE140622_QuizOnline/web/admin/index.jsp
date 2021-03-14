<%-- 
    Document   : index
    Created on : Jan 26, 2021, 9:24:55 PM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="subList" value="${requestScope.SUBJECT_LIST}"/>
<c:set var="totalQues" value="${requestScope.TOTAL_QUES}"/>
<c:if test="${not empty param.message}">
    <script>
        alert('${param.message}');
    </script>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Panel</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/mycss.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/easy-pagination.css">
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
    <body>
        <nav class="navbar navbar-expand-sm fixed-top nav-bg">
            <!-- Brand/logo -->
            <a class="navbar-brand" href="#">
              <img src="${pageContext.request.contextPath}/admin/public/images/logo-icon.png" alt="logo" style="width:40px;">
            </a>

            <!-- Links -->
            <div class="collapse navbar-collapse" id="navb">
                <ul class="navbar-nav mr-auto">
                  <li class="nav-item active">
                    <a class="nav-link" href="loadAdminPage">Admin Panel</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="loadCreateQuestionPage">Create Question</a>
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
        <main role="main" class="container">
            <form action="loadAdminPage" method="POST">
            <div class="row search-control">
               <div class="col-sm">
                  <div class="form-group">
                     <label for="subject">Subject: </label>
                     <select class="form-control" id="subject" name="subject">
                        <option value="" <c:if test="${empty param.subject}"></c:if>>All Subject</option>
                        <c:forEach var="sub" items="${subList}">
                            <option value="${sub.id}" 
                                <c:if test="${param.subject eq sub.id}">selected</c:if>
                            >${sub.name}</option>
                        </c:forEach>
                     </select>
                  </div>
               </div>
               <div class="col-sm">
                  <div class="form-group">
                     <label for="subject">Status: </label>
                     <select class="form-control" id="status" name="status">
                        <option value="" <c:if test="${empty param.status}">selected</c:if>>All Status</option>
                        <option value="true" <c:if test="${param.status}">selected</c:if>>Active</option>
                        <option value="false" <c:if test="${param.status eq 'false'}">selected</c:if>>Inactive</option>
                     </select>
                  </div>
               </div>
               <div class="col-sm">
                  <div class="form-group">
                     <label for="question">Question:</label>
                     <input type="text" class="form-control" id="question" placeholder="Enter question" name="question" value="${param.question}">
                  </div>
               </div>
               <div class="col-sm">
                  <button type="submit" class="btn my-btn search-btn"><i class="fas fa-search"></i></button>
               </div>
            </div>
         </form>
            <c:if test="${totalQues > 0}"><small style="padding-left: 6.7rem;">Total ${totalQues} records</small></c:if>
         <div id="easyPaginate">
            <c:set var="questList" value="${requestScope.QUESTION_LIST}"/>
            <c:if test="${not empty questList}">
                <c:forEach var="ques" items="${questList}">
                <div class="container mt-5 my-pagination">
                   <div class="d-flex justify-content-center row">
                      <div class="col-md-10 col-lg-10">
                         <div class="border">
                               <div class="question bg-white p-3 border-bottom">
                                  <div class="d-flex flex-row justify-content-between align-items-center mcq">
                                     <h4>Subject: ${ques.question.subjectId.name}</h4><span>Created date: <fmt:formatDate pattern = "dd/MM/yyyy" value = "${ques.question.createdDate}"/></span>
                                  </div>
                               </div>
                               <div class="question bg-white p-3 border-bottom">
                                    <div class="d-flex flex-row align-items-center question-title">
                                       <h3 class="text-danger"><i class="fas fa-question"></i></h3>
                                       <pre><h5 class="mt-1 ml-2">${ques.question.content}</h5></pre>
                                    </div>
                                    <c:forEach var="ans" items="${ques.answers}">
                                        <c:if test="${ans.isRight}">
                                            <div class="ans ml-2">
                                                <pre><p><i class="fas fa-check fa-2x correct-icon"></i><span class="ans-text">${ans.content}</span></p></pre>
                                            </div>
                                        </c:if>
                                        <c:if test="${not ans.isRight}">
                                            <div class="ans ml-2">
                                                <pre><p><i class="fas fa-times fa-2x wrong-icon"></i><span class="ans-text">${ans.content}</span></p></pre>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                               </div>
                               <div class="d-flex flex-row justify-content-between align-items-center p-3 bg-white">
                                    <c:url var="urlRewriting" value="loadSpecificQues">
                                        <c:param name="questId" value="${ques.question.id}"/>
                                    </c:url>
                                    <a href="${urlRewriting}" class="my-color" data-toggle="tooltip" title="View"><i class="far fa-eye"></i></a>
                                    <c:url var="urlRewriting" value="deleteQuestion">
                                        <c:param name="questId" value="${ques.question.id}"/>
                                        <c:param name="subject" value="${param.subject}"/>
                                        <c:param name="status" value="${param.status}"/>
                                        <c:param name="question" value="${param.question}"/>
                                    </c:url>
                                    <c:if test="${ques.question.status}">
                                    <a href="${urlRewriting}" class="justify-content-end my-color" onclick="return deleteAccept();" data-toggle="tooltip" title="Delete"><i class="fas fa-trash"></i></a>
                                    </c:if>
                               </div>
                         </div>
                      </div>
                   </div>
                </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty questList}">
                <h1 style="padding-left: 6.7rem;">Not found</h1>
            </c:if>
        </div> 

      </main>
      <script src="${pageContext.request.contextPath}/admin/public/js/jquery.min.js"></script>
      <script src="${pageContext.request.contextPath}/admin/public/js/easy-pagination.js"></script>
      <script src="${pageContext.request.contextPath}/admin/public/js/myjs.js"></script>
      <script>
        $('#easyPaginate').easyPaginate({
            paginateElement: 'div.my-pagination',
            startPage: 1,
            elementsPerPage: 20,
            effect: 'climb'
        });
        
        
     </script>
    </body>
</html>
