<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<br/>
<div style="float:right; margin-right:200px;">
     <div class="panel-heading">
         <h3 class="panel-title">Please sign in</h3>
     </div>
     <div class="panel-body">
         <form:form modelAttribute="login" action="${pageContext.request.contextPath}/loginuser" method="post">
             <form:errors path="*" cssClass="alert alert-danger" element="div"/>
             <fieldset>
                 <div class='form-group'>
                     <form:input class="form-control" placeholder="User Name" path='loginId' type="text" name="j_username"/>
                 </div>
                 <div class="form-group">
                     <form:input class='form-control' placeholder="Password"	path='password' type="password" name="j_password"/>
                 </div>
                 <div class="form-group">
                     <div class="checkbox">
                         <label> <input type="checkbox" />Remember Me
                         </label>
                     </div>
                 </div>
                 <input class="btn btn-lg btn-success btn-block" type="submit"	value="login">
             </fieldset>
         </form:form>
     </div>
 </div> 
<div style="margin:0">
     <img src="static/images/login.png"   alt="">
</div>