<%-- 
    Document   : create-question
    Created on : Jan 26, 2021, 9:41:21 PM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="subList" value="${requestScope.SUBJECT_LIST}"/>
<c:if test="${not empty sessionScope.MESSAGE}">
    <script type="text/javascript">
        alert("${sessionScope.MESSAGE}");
    </script>
    <%session.removeAttribute("MESSAGE");%>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Question</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/mycss.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
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
                  <li class="nav-item">
                    <a class="nav-link" href="loadAdminPage">Admin Panel</a>
                  </li>
                  <li class="nav-item active">
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
        <div class="main-content">
        <h1>Create Question</h1>
        <form action="createQuestion" method="POST" id="createForm" class="md-float-material form-material">
            <div class="row">
                <div class="col-sm-6">
                    <div class="container-fluid">
                        <div class="form-group">
                            <label for="subject">Choose subject:</label>
                            <select class="form-control" id="subject" name="subject">
                                <c:forEach var="sub" items="${subList}">
                                    <option value="${sub.id}"
                                        <c:if test="${param.subject eq sub.id}">selected</c:if>
                                    >${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group form-primary"><textarea class="form-control" name="question" placeholder="Question" id="question"></textarea></div>
                        
                        <button type="submit" name="btnAction" value="Create" class="btn my-btn">Create Question</button>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="container-fluid">
                        <p class="note"><i class="fas fa-star-half-alt"></i><span class="error"> Check radio button for the true answer!!!</span></p>
                        <div class="form-group form-primary">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                    <input type="radio" id="answer01" name="rdoAnswer" value="1">
                                    </div>
                                </div>
                                <textarea class="form-control" name="answer01" placeholder="Answer No.1" id="answer01"></textarea>
                            </div>
                        </div>
                        <div class="form-group form-primary">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                    <input type="radio" id="answer02" name="rdoAnswer" value="2">
                                    </div>
                                </div>
                                <textarea class="form-control" name="answer02" placeholder="Answer No.2" id="answer02"></textarea>
                            </div>
                        </div>
                        <div class="form-group form-primary">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                    <input type="radio" id="answer03" name="rdoAnswer" value="3">
                                    </div>
                                </div>
                                <textarea class="form-control" name="answer03" placeholder="Answer No.3" id="answer03"></textarea>
                            </div>
                        </div>
                        
                        <div class="form-group form-primary">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">
                                    <input type="radio" id="answer04" name="rdoAnswer" value="4">
                                    </div>
                                </div>
                                <textarea class="form-control" name="answer04" placeholder="Answer No.4" id="answer04"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        </div>
    </main>
        <script src="${pageContext.request.contextPath}/admin/public/js/bootstrap.js"></script>
        <script src="${pageContext.request.contextPath}/admin/public/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/admin/public/js/jquery.validate.js"></script>
        <script src="${pageContext.request.contextPath}/admin/public/js/additional-methods.js"></script>
        <script type="text/javascript">
            $.extend($.validator.methods, {
                required: function(b, c, d) {
                    if (!this.depend(d, c)) return "dependency-mismatch";
                    if ("select" === c.nodeName.toLowerCase()) {
                        var e = a(c).val();
                        return e && e.length > 0;
                    }
                    return this.checkable(c) ? this.getLength(b, c) > 0 : b.trim().length > 0;
                }
            });
            
            $('#createForm').submit(function() {
                if ($('input:radio', this).is(':checked')) {
                    // everything's fine...
                } else {
                    $('.error').text(" Please choose correct answer!!!");
                    return false;
                }
            });

            $(document).ready(function(){
                $("#createForm").validate({
                    rules:{
                        question:{
                            required: true
                        },
                        answer01:{
                            required: true
                        },
                        answer02:{
                            required: true
                        },
                        answer03:{
                            required: true
                        },
                        answer04:{
                            required: true
                        }
                    },
                    messages:{
                        question:{
                            required: "Please enter question"
                        },
                        answer01:{
                            required: "Please enter answer 1"
                        },
                        answer02:{
                            required: "Please enter answer 2"
                        },
                        answer03:{
                            required: "Please enter answer 3"
                        },
                        answer04:{
                            required: "Please enter answer 4"
                        }
                    },
                    errorElemet: "em",
                    errorPlacement: function ( error, element ) {
                        // Add the `invalid-feedback` class to the error element
                        error.addClass( "invalid-feedback" );

                        if ( element.prop( "type" ) === "checkbox" ) {
                                error.insertAfter( element.next( "label" ) );
                        } else {
                                error.insertAfter( element );
                        }
                    },
                    highlight: function ( element, errorClass, validClass ) {
                            $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
                    },
                    unhighlight: function (element, errorClass, validClass) {
                            $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
                    }
                });
            });
        </script>
    </body>
</html>
