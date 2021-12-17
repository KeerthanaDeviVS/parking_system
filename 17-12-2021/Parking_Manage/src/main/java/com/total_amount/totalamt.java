package com.total_amount;

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

@WebServlet("/getamt")
public class totalamt extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String date=request.getParameter("dateday");
		String url="jdbc:mysql://localhost:3306/parkingsystem";
		String user_name="root";
		String password="Magicalkd@11";
		String query="select charge from customerhistory where exit_date = ?";
		int total_amount=0;
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
				out.println("<p style='color:red;'>NO VEHICLES ARE EXITED ON "+date+"</p>");
			}
		    else {
			do
			{
			total_amount+=rs.getInt(1);
			
			}while(rs.next());
		    }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>TOTAL COLLECTION</title>");            
        out.println("</head>");
        out.println("<body onLoad=\"showResult();\">");
        out.println("<script>");
        out.println("function showResult(){");
        out.println("alert(\"TOTAL COLLECTED AMOUNT : "+total_amount+"\")");
        out.println("}");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
		out.println("<meta http-equiv='refresh' content='10;URL=AdminOperationsList.jsp'>");
		
	}

	
}
