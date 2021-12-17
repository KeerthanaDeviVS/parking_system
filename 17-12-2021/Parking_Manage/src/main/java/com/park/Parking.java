package com.park;

import java.io.IOException;
import java.util.Random;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/park")
public class Parking extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url="jdbc:mysql://localhost:3306/parkingsystem";
	String User_name="root";
	String Password="Magicalkd@11";
	String query4="update floors set status_of_slot = ? ,vehicle_num = ? where slot_code =? ";
	String query5="insert into customerhistory values(?,?,?,?,?,?,?,?,?,?)";
	String query2="update parkinglot set available_slots=?, booked_slots = ? where floor_number=?";
	String slotcode;int floor_no,available_slots,booked_slots;
	Random random = new Random();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String vehicle_type=req.getParameter("vehicle");
			String vehicle_num=req.getParameter("vehicle_no");
			String contact_num=req.getParameter("telphone");
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
		    LocalDate entry_date = LocalDate.now();
		    LocalTime entry_time = LocalTime.now();
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, User_name, Password);
			String query1="select floor_number,booked_slots,available_slots from parkinglot where available_slots>'0' and vehicle_type=?";
			PreparedStatement st1=con.prepareStatement(query1);
			st1.setString(1, vehicle_type);
			ResultSet rs1=st1.executeQuery();
			PrintWriter out = resp.getWriter();
			if(!(rs1.next()))
			{
				out.println("<meta http-equiv='refresh' content='3;URL=parking.jsp'>");
				out.println("<p style='color:red;'>SLOT NOT AVAILABLE</p>");
			}
			else
			{
			floor_no=rs1.getInt(1);
			booked_slots=rs1.getInt(2); 
			available_slots=rs1.getInt(3);}
			PreparedStatement st2=con.prepareStatement(query2);
			st2.setInt(1,available_slots-1);
			st2.setInt(2, booked_slots+1);
		    st2.setInt(3, floor_no);
		    st2.executeUpdate(); 
			String query3="select slot_code from floors where floor_number=? and status_of_slot= 'EMPTY' ORDER BY length(slot_code), slot_code limit 1";
			PreparedStatement st3=con.prepareStatement(query3);
			st3.setInt(1, floor_no);
			ResultSet rs=st3.executeQuery();
			rs.next();
			slotcode=rs.getString(1);
			PreparedStatement st4=con.prepareStatement(query4);
			st4.setString(1,"FULL");
			st4.setString(2, vehicle_num);
			st4.setString(3, slotcode);
			int cnt=st4.executeUpdate();
			PreparedStatement st5=con.prepareStatement(query5);
			int random_number=random.nextInt(10000);
			st5.setInt(1, random_number);
			st5.setInt(2, floor_no);
			st5.setString(3, slotcode);
			st5.setString(4, vehicle_num);
			st5.setString(5,contact_num);
			st5.setString(6,formatter1.format(entry_date));
			st5.setString(7, formatter2.format(entry_time));
			st5.setString(8, null);
			st5.setString(9,null);
			st5.setInt(10,0);
			st5.executeUpdate();
			if(cnt==1)
			{
		        out.println("<!DOCTYPE html>");
		        out.println("<html>");
		        out.println("<head>");
		        out.println("<title>PARKING ID</title>");            
		        out.println("</head>");
		        out.println("<body onLoad=\"showResult();\">");
		        out.println("<script>");
		        out.println("function showResult(){");
		        out.println("alert(\" YOUR VEHICLE PARKED ON SLOTCODE :"+slotcode+"\")");
		        out.println("}");
		        out.println("</script>");
		        out.println("</body>");
		        out.println("</html>");
				out.println("<meta http-equiv='refresh' content='0;URL=customeroperations.jsp'>");
				out.println("<h1 style='color:green;'>VEHICLE   PARKED   SUCCESSFULLY!!!</h1>");
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
