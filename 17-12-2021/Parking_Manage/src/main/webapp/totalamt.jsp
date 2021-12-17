<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TOTAL AMOUNT</title>
<style>
* {
   box-sizing: border-box;
   margin: 0;
}
body
{
background-image: url("adminop.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.box {
  padding: 70px;
  width: 50%;
  position:absolute;
  top:25%;
  left:25%;
  background-color:rgb(255,255,255,0.7);
}
.button {
  background-color:#d3d3d3;
}
</style>
</head>
<body>
<% 
if(session.getAttribute("user_name")==null)
{
  response.sendRedirect("AdminLogin.jsp");
}%>
<div class="box">
<form action="getamt" method="get">
Enter Date:<input type="text" name="dateday" placeholder="DD-MM-YYYY"><br><br>
Click:<input type="submit" value="GET AMOUNT"><br><br>
<a href="AdminOperationsList.jsp">
<input type="button" value="BACK"></a><br>
</form>
</div>
</body>
</html>