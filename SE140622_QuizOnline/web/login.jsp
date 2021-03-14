<%-- 
    Document   : login
    Created on : Jan 25, 2021, 9:56:17 AM
    Author     : Delwyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="public/css/bootstrap.css">
        <link rel="stylesheet" href="public/css/mycss.css">
        <c:if test="${not empty requestScope.MESSAGE}">
            <script type="text/javascript">
                alert('${requestScope.MESSAGE}');
            </script>
        </c:if>
    </head>
    <body class="bg">
        <section class="login-block login">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <form action="login" method="POST" class="md-float-material form-material">
                            <div class="auth-box card">
                                <div class="card-block">
                                    <c:if test="${not empty requestScope.ERROR}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert" id="error-arlet">
                                        <strong>Oops!</strong> ${requestScope.ERROR}
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                          <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    </c:if>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h3 class="text-center heading">Login</h3>
                                        </div>
                                    </div>
                                    <div class="form-group form-primary"> <input type="text" class="form-control" name="email" value="${param.email}" placeholder="Email" id="email" required> </div>
                                    <div class="form-group form-primary"> <input type="password" class="form-control" name="password" placeholder="Password" value="" id="password" required> </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <button type="submit" class="btn btn-block my-btn" >Login</button>
                                        </div>
                                    </div>
                                    <div class="row justify-content-center my-link">
                                        <span><i>Do not any account?</i><a href="signUpPage" class="my-link"> Sign Up</a></span>
                                    </div>
                                    <div class="or-container">
                                        <div class="line-separator"></div>
                                        <div class="or-label">or</div>
                                        <div class="line-separator"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12"> <a class="btn-google" style="color: #000000; text-decoration: none" 
                                                href="https://accounts.google.com/o/oauth2/auth?scope=openid%20email%20profile&redirect_uri=http://localhost:8080/QuizOnline/googleLogin&response_type=code&client_id=535579488853-7a2qfj3d2rsa2t320t3s7caf8gtnd2pm.apps.googleusercontent.com&approval_prompt=force">
                                                <img src="public/images/icon-google.png"> Login With Google</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <script src="public/js/bootstrap.js"></script>
        <script src="public/js/jquery.min.js"></script>
        <script src="public/js/myjs.js"></script>
    </body>
</html>
