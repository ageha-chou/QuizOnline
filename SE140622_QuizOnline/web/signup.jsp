<%-- 
    Document   : signup
    Created on : Jan 25, 2021, 7:32:03 AM
    Author     : Delwyn
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signup</title>
        <link rel="stylesheet" href="public/css/bootstrap.css">
        <link rel="stylesheet" href="public/css/mycss.css">
    </head>
    <body class="bg">
        <section class="login-block">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <form action="signUp" method="POST" id="signUpForm" class="md-float-material form-material">
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
                                            <h3 class="text-center heading">Create Account</h3>
                                        </div>
                                    </div>
                                    <div class="form-group form-primary"> <input type="text" class="form-control" name="fullname" value="${param.fullname}" placeholder="Full name" id="fullname"> </div>
                                    <div class="form-group form-primary"> <input type="text" class="form-control" name="email" value="${param.email}" placeholder="Email" id="email"> </div>
                                    <div class="form-group form-primary"> <input type="password" class="form-control" name="password" placeholder="Password" value="" id="password"> </div>
                                    <div class="form-group form-primary"> <input type="password" class="form-control" name="confirmPassword" placeholder="Repeat password" value="" id="confirmPassword"> </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <button type="submit" class="btn btn-md btn-block waves-effect text-center m-b-20 my-btn"><i class="fa fa-lock"></i> Signup Now </button> 
                                        </div>
                                    </div>
                                    <div class="or-container">
                                        <div class="line-separator"></div>
                                        <div class="or-label">or</div>
                                        <div class="line-separator"></div>
                                    </div>
                                    <p class="text-inverse text-center">Already have an account? <a class="my-link" href="loginPage">Login</a></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <script src="public/js/bootstrap.js"></script>
        <script src="public/js/jquery.min.js"></script>
        <script src="public/js/jquery.validate.js"></script>
        <script src="public/js/additional-methods.js"></script>
        <script src="public/js/myjs.js"></script>
        <script type="text/javascript">
            $.extend($.validator.methods, {
                required: function(b, c, d) {
                    if (!this.depend(d, c)) return "dependency-mismatch";
                    if ("select" === c.nodeName.toLowerCase()) {
                        var e = a(c).val();
                        return e && e.length > 0
                    }
                    return this.checkable(c) ? this.getLength(b, c) > 0 : b.trim().length > 0
                }
            });
            
            $(document).ready(function(){
                $("#signUpForm").validate({
                    rules:{
                        fullname:{
                            required: true,
                            rangelength: [2, 30]
                        },
                        email:{
                            required: true,
                            email: true
                           
                        },
                        password:{
                            required: true,
                            minlength: 6,
                            maxlength: 30
                        },
                        confirmPassword:{
                            equalTo: "#password"
                        }
                    },
                    messages:{
                        fullname:{
                            required: "Please enter your name",
                            rangelength: "Please enter at least 2 chars and at most 30 chars"
                        },
                        email:{
                            required: "Please enter your email",
                            email: "Please enter email format correctly. Format: name@domain.tld"
                        },
                        password:{
                            required: "Please enter your password",
                            minlength: "Your password has at least 6 chars",
                            maxlength: "Too long password..."
                        },
                        confirmPassword:{
                            equalTo: "Your confirm password is not same as your password"
                        }
                    }
                });
            });
        </script>
    </body>
</html>
