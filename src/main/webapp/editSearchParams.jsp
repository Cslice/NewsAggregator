<%-- 
    Document   : newPost
    Created on : Jul 25, 2015, 5:45:44 PM
    Author     : cameronthomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--<c:if test="${sessionScope == null}" > 
   <c:redirect url="/index.jsp"/>
</c:if>--%>

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

    <title>Edit Search Parameters</title>

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
        function test()
        {
//           var validThreadName = true;
//           var threadName = $("#threadNameTextbox").val();
//            
//            // Check if text box is empty or is just spaces
//            if (threadName===null || threadName.trim()==="" 
//                    || threadName === undefined) {    
//                validThreadName = false;
//            }
//            
//            return validThreadName;
            alert("hi");
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
            <label class="navbar-brand">Edit Search Parameters</label>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          
          <ul class="nav navbar-nav navbar-right">
            
            <li><a href="Logout">Sign Out</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

<i class="fa fa-spinner fa-spin fa-5x"></i>
    <div class="container">

      <!-- Main component for a primary marketing message or call to action -->
      <div class="jumbotron">
          
          <div class="container">
            <h3>Words to Exclude</h3>
            <!-- Delete words to exclude in search -->
            <form method="POST" action="EditSearchParams">    
                <c:if test="${excludeWordList.isEmpty()}">
                    <h4>No words added</h4>
                </c:if>
             
                <ul class="list-group">
                   <c:forEach var="word" items="${excludeWordList}"> 
                       <div class="list-group-item">          
                               <li class="list-group-item">
                                   <span>${word}</span>
                                   <button type="submit" name="wordToAddOrDelete" value="${word}" class="btn btn-xs btn-danger">Delete Word</button>
                               </li>
                       </div>
                   </c:forEach>
                </ul>        
                <input type="hidden" name ="typeOfTransAction" value="DeleteExcludeWord"/>
            </form>
            
            <!-- Add words to exclude in search -->
            <form method="POST" action="EditSearchParams">
              <div class="form-group">
                <label for="usr">Name:</label>
                <input type="text" class="form-control" name="wordToAddOrDelete">
                
                <br/>
                
                <button type="submit" class="btn btn-s btn-success">Add Word</button>
                <input type="hidden" name ="typeOfTransAction" value="AddExcludeWord"/>
              </div>
            </form>
            
            <br/>
            <br/>
            <br/>        
            
            <h3>Words to Include</h3>
            <!-- Delete words to include in search -->
             <form method="POST" action="EditSearchParams">   
                 
                 <c:if test="${includeWordList.isEmpty()}">
                    <h4>No words added</h4>
                </c:if>
                    
                <ul class="list-group">
                   <c:forEach var="word" items="${includeWordList}"> 
                       <div class="list-group-item">          
                               <li class="list-group-item">
                                   ${word}
                                   <button type="submit" name="wordToAddOrDelete" value="${word}" class="btn btn-xs btn-danger">Delete Word</button>
                               </li>
                       </div>
                   </c:forEach>
                </ul>        
                <input type="hidden" name ="typeOfTransAction" value="DeleteIncludeWord"/>
            </form>
            
            
            <!-- Add words to include in search -->
            <form method="POST" action="EditSearchParams">
              <div class="form-group">
                <input type="text" class="form-control" name="wordToAddOrDelete">
                
                <br/>
                
                <button type="submit" class="btn btn-s btn-success">Add Word</button>
                <input type="hidden" name ="typeOfTransAction" value="AddIncludeWord"/>
              </div>
            </form>
          </div>
          
          
        <a href="SignIn">Back to HomePage</a>
          
    
        
            
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

