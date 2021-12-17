package com.history;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get_his")
public class viewhistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date=request.getParameter("dateday");
		String url="jdbc:mysql://localhost:3306/parkingsystem";
		String user_name="root";
		String password="Magicalkd@11";
		String query="select * from customerhistory where exit_date = ?";
		PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user_name, password);
			PreparedStatement st1=con.prepareStatement(query);
		    st1.setString(1, date);
		    ResultSet rs=st1.executeQuery();
		    if(!(rs.next()))
			{
				out.println("<meta http-equiv='refresh' content='3;URL=totalamt.jsp'>");
				out.println("<p style='color:red;'>NO CUSTOMER HISTORY ON "+date+"</p>");
			}
		    else {
				request.setAttribute("exit_date", date);
				RequestDispatcher rd = 
			             request.getRequestDispatcher("viewcustomerhis.jsp");
				rd.forward(request, response);
			
		    }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
