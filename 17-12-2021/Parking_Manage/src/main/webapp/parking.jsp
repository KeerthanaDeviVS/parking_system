<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PARK</title>
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
<center>
<div class="box">
<form action="park" method="post">
Enter your Name:<input type="text" name="customer_name"><br><br>
Enter your Vehicle Number:<input type="text" name="vehicle_no"><br><br>
Enter your Contact Number:<input type="tel" name="telphone"  pattern="[0-9]{10}"  title="Ten digits code" required/><br><br>
Tick Vehicle Type<br><br>
<input type="radio" name="vehicle" value="Bike">
<label for="vehicle"> Bike</label><br><br>
<input type="radio" name="vehicle" value="Car">
<label for="vehicle"> Car</label><br><br>
Click:<input type="submit" value="PROCEED TO PARK"><br><br>
<a href="welcome.html">
<input type="button" value="GO TO HOME PAGE"></a><br>
</form>
</div>
</center>
</body>
</html>