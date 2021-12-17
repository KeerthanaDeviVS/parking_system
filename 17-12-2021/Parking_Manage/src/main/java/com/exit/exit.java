package com.exit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/exit")
public class exit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String slot_no=request.getParameter("slot_no");
		String url="jdbc:mysql://localhost:3306/parkingsystem";
		String user_name="root";
		String password="Magicalkd@11";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate exit_date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String query1="select Parking_No,entry_time,floor_number from customerhistory where charge='0' and slot_code = ?";
		String query2 ="update customerhistory set exit_time= ? ,charge=? ,exit_date = ? where charge='0' and slot_code = ?";
		LocalTime exit_time = LocalTime.now();int charge=0,available_slots,booked_slots;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user_name, password);
			PreparedStatement st1=con.prepareStatement(query1);
			st1.setString(1, slot_no);
			ResultSet rs=st1.executeQuery();
			PrintWriter out = response.getWriter();
			if(!(rs.next()))
			{
				out.println("<meta http-equiv='refresh' content='3;URL=exit.jsp'>");
				out.println("<p style='color:red;'>INVALID SLOT CODE.USE VALID ID</p>");
			}
			else {
				int parking_no=rs.getInt(1);
			String entry_time=rs.getString(2);
			int floor_number=rs.getInt(3);
			Date enter_time=format.parse(entry_time);
			Date exittime=format.parse(formatter.format(exit_time));
			long time_diff=exittime.getTime()-enter_time.getTime();
			long time_diff_Mins = (time_diff / (60 * 1000))% 60;
			int mins=(int)time_diff_Mins;
			if(slot_no.contains("B"))
			charge=50+mins*10;
			else
				charge=100+mins*10;
			PreparedStatement st2=con.prepareStatement(query2);
			st2.setString(1,formatter.format(exit_time));
			st2.setInt(2, charge);
			st2.setString(3, formatter1.format(exit_date));
			st2.setString(4, slot_no);
			st2.executeUpdate();
			String query3="select available_slots,booked_slots from parkinglot where floor_number="+floor_number;
			Statement st3=con.createStatement();
			ResultSet rs1=st3.executeQuery(query3);
			rs1.next();
			available_slots=rs1.getInt(1);
			booked_slots=rs1.getInt(2);
			String query4="update parkinglot set available_slots=?, booked_slots = ? where floor_number=?";
			PreparedStatement st4=con.prepareStatement(query4);
			st4.setInt(1, available_slots+1);
			st4.setInt(2, booked_slots-1);
			st4.setInt(3, floor_number);
			st4.executeUpdate();
			String query5="update floors set status_of_slot='EMPTY',vehicle_num='NIL' where slot_code=?";
			PreparedStatement st5=con.prepareStatement(query5);
			st5.setString(1, slot_no);
	        st5.executeUpdate();
			request.setAttribute("parking_no", parking_no);
			RequestDispatcher rd = 
		             request.getRequestDispatcher("customerbill.jsp");
			rd.forward(request, response);}
		} catch (SQLException | ClassNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
