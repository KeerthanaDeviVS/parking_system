<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EDIT PARKING LOT</title>
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
  top:15%;
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
<form action="editslots" method="post">
Select Your Option<br><br>
<input type="radio" name="Add_/_Delete" value="ADD EXTRA SLOTS">
<label for="Add_/_Delete"> ADD EXTRA SLOTS</label><br><br>
<input type="radio" name="Add_/_Delete" value="DELETE SLOTS">
<label for="Add_/_Delete"> DELETE SLOTS</label><br><br>
Enter Floor Number:<input type="number" name="floor_number"><br><br>
Enter Total_Slots to be added / deleted:<input type="number" name="available_slots"><br><br>
Click:<input type="submit" value="EDIT"><br><br>
<a href="AdminOperationsList.jsp">
<input type="button" value="BACK"></a><br>
</form>

</body>
</html>