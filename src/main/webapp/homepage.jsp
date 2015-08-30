<%-- 
    Document   : newPost
    Created on : Jul 25, 2015, 5:45:44 PM
    Author     : cameronthomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user == null}" > 
   <c:redirect url="index.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Discussion Board Homepage</title>

    <!-- Bootstrap core CSS -->
    <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="http://getbootstrap.com/examples/navbar-static-top/navbar-static-top.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
   
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
   
    <script type="text/javascript">
        function validateThreadName()
        {
           var validThreadName = true;
           var threadName = $("#threadNameTextbox").val();
            
            // Check if text box is empty or is just spaces
            if (threadName===null || threadName.trim()==="" 
                    || threadName === undefined) {    
                validThreadName = false;
            }
            
            return validThreadName;
        }
    </script>
<!--    <style>
        .fa.fa-spinner.fa-spin {
    -webkit-animation-timing-function: steps(8, end);
    animation-timing-function: steps(8, end);
}
  </style>-->
  </head>

  <body>

    <!-- Static navbar -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div>         
            <label class="navbar-brand">Discussion Board</label>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          
          <ul class="nav navbar-nav navbar-right">
            
            <li><a href="signIn.html">Sign Out</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

<i class="fa fa-spinner fa-spin fa-5x"></i>
    <div class="container">

      <!-- Main component for a primary marketing message or call to action -->
      <div class="jumbotron">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Cnn.svg/2000px-Cnn.svg.png" alt="CNN photo" style="width:250px;height:100px;"/>
        ${noThreadsLabel}
        <c:forEach var="thread" items="${threadList}">       
                <a href="ViewPostsPage?threadName=${thread[0]}&username=${username}" class="list-group-item">
                   <h4>${thread[0]}</h4> 
                            ${thread[1]} <br />
                            ${thread[2]}
                </a>
        </c:forEach>
        <br />
        
            
        </div>
      </div>
    </div> <!-- /container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>

