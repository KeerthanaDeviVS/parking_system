package com.editfloors;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editslots")
public class editingparkinglot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String add_or_delete=req.getParameter("Add_/_Delete");
		int floor_num=Integer.parseInt(req.getParameter("floor_number"));
		int available_slots=Integer.parseInt(req.getParameter("available_slots")); 
		int duplicate_cnt=available_slots; String bike_or_car = null;
		String url="jdbc:mysql://localhost:3306/parkingsystem";
		String user_name="root";
		String pass="Magicalkd@11";
		String query1="select vehicle_type,available_slots,booked_slots from parkinglot where floor_number="+floor_num;
		String query2="update parkinglot set available_slots = ? where floor_number = ? " ;
		String query3=null;int cnt=0;
		 PrintWriter out = resp.getWriter();

		if(add_or_delete.equals("ADD EXTRA SLOTS"))
		{
			query3="insert into floors values(?,?,?,?)";
		}
		else
		{
			query3="delete from floors where floor_number = ? order by slot_code desc limit 1";
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, user_name, pass);
			Statement st1=con.createStatement();
			ResultSet rs=st1.executeQuery(query1);
			if(!rs.next())
			{
				out.println("<meta http-equiv='refresh' content='3;URL=editingparkinglot.jsp'>");
				 out.println("<p style='color:red;'>FLOOR NOT AVAILABLE!</p>");
				   cnt=1;
			}
			else {
			int val=0,booked_count=0;
			val=rs.getInt(2);
			bike_or_car=rs.getString(1);
			booked_count=rs.getInt(3);
			int old_val=val;
			PreparedStatement st2=con.prepareStatement(query2);
			if(add_or_delete.equals("ADD EXTRA SLOTS")) {
			val+=available_slots;
			st2.setInt(1, val);
			st2.setInt(2, floor_num);
			st2.executeUpdate();
			}
			else if(add_or_delete.equals("DELETE SLOTS") && val>=available_slots)
			{
				val-=available_slots;
				st2.setInt(1, val);
				st2.setInt(2, floor_num);
		       st2.executeUpdate();
			}
			else
			{  
				 out.println("<meta http-equiv='refresh' content='3;URL=editingparkinglot.jsp'>");
				 out.println("<p style='color:red;'>SLOTS NOT AVAILABLE!</p>");
				   cnt=1;
			}
			PreparedStatement st3=con.prepareStatement(query3);
			if(add_or_delete.equals("ADD EXTRA SLOTS"))
			{
			for(int indx=old_val+booked_count;indx<val+booked_count;indx++)
			{
				st3.setInt(1, floor_num);
				if(bike_or_car.equals("Bike")) {
				st3.setString(2, String.format("%dB%d", floor_num,indx));}
				else {
					st3.setString(2, String.format("%dC%d",floor_num,indx));
				}
				st3.setString(3, "EMPTY");
				st3.setString(4, "NIL");
				st3.executeUpdate();
			}
			}
			else if(add_or_delete.equals("DELETE SLOTS") && old_val>=available_slots) {
				int count=1;
				while(count<=duplicate_cnt) {
				st3.setInt(1,floor_num);
				st3.executeUpdate();count++;}
				}
				
			if(cnt==0)
			{
				out.println("<meta http-equiv='refresh' content='3;URL=AdminOperationsList.jsp'>");
				   out.println("<p style='color:green;'>SLOTS EDITED SUCCESSFULLY!</p>");
			}
				
	
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
