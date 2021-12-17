<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
    <% Class.forName("com.mysql.jdbc.Driver"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EXIT</title>
<style>
* {
   box-sizing: border-box;
   margin: 0;
}
body
{
background-image: url("customer.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.box {
  padding: 70px;
  width: 50%;
  position:absolute;
  top:15%;
  left:25%;
  background-color:rgb(255,255,255,0.7);
}
</style>
</head>
<body>
<div class="box">
<center>
<form action="exit">
Enter Your Slot Code : <input type="text" name="slot_no"><br><br>
Click:<input type="submit" value="EXIT VEHICLE"><br><br>
<a href="welcome.html">
<input type="button" value="GO TO HOME PAGE"></a><br>
</form>
</center>
</div>
</body>
</html>