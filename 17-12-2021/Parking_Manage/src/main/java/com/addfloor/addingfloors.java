package com.addfloor;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

@WebServlet("/addfloors")
public class addingfloors extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<String> arr = new ArrayList<String>();
		String vehicle_type=req.getParameter("vehicle");
		int floor_num=Integer.parseInt(req.getParameter("floor_number"));
		int available_slots=Integer.parseInt(req.getParameter("available_slots"));
		String url="jdbc:mysql://localhost:3306/parkingsystem";
		String User_name="root";
		String Password="Magicalkd@11";
		String query1="insert into parkinglot values(?,?,?,?)";
		String query2="insert into floors values(?,?,?,?)";
		String query3="update floors set slot_code = ? where floor_number = ? order by length(slot_code) asc ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, User_name, Password);
			PreparedStatement st1=con.prepareStatement(query1);
			st1.setString(1,vehicle_type);
			st1.setInt(2, floor_num);
			st1.setInt(3, available_slots);
			st1.setInt(4, 0);
			int count=st1.executeUpdate();
			PreparedStatement st2=con.prepareStatement(query2);
			for(int indx=1;indx<=available_slots;indx++)
			{
				st2.setInt(1, floor_num);
				if(vehicle_type.equals("Bike")) {
					st2.setString(2, String.format("%dB%d", floor_num,indx));}
					else 
					{
						st2.setString(2, String.format("%dC%d", floor_num,indx));
					}
				st2.setString(3, "EMPTY");
				st2.setString(4, "NIL");
				st2.executeUpdate();
			}
			PreparedStatement st3=con.prepareStatement(query3)	;
			for(int indx=1;indx<=available_slots;indx++)
			{
				if(vehicle_type.equals("Bike")) {
					st2.setString(1, String.format("%dB%d", floor_num,indx));}
					else 
					{
						st2.setString(1, String.format("%dC%d", floor_num,indx));
					}
				st3.setInt(2,floor_num);
				st3.executeUpdate();
			}
			PrintWriter out=resp.getWriter();
			resp.setContentType("text/plain"); 
	        resp.setCharacterEncoding("UTF-8");
			if(count==1)
			{
				resp.getWriter().write("success");
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
