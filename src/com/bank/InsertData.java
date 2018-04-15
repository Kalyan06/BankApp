package com.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertData() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt1=null;
		PrintWriter out=null;
		response.setContentType("text/html");
		out=response.getWriter();
		try {
			
			int accno=Integer.parseInt(request.getParameter("accno"));
			String name=request.getParameter("name");
			String loc=request.getParameter("loc");
			int amt=Integer.parseInt(request.getParameter("amt"));
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
			pstmt=connection.prepareStatement("insert into bank_customers values(?,?,?)");
			
			pstmt.setInt(1, accno);
			pstmt.setString(2, name);
			pstmt.setString(3, loc);
			pstmt.executeUpdate();
			pstmt1=connection.prepareStatement("insert into bank_balance values(?,?)");
			pstmt1.setInt(1, accno);
			pstmt1.setInt(2, amt);
			pstmt1.executeUpdate();
			
			out.println("Record Inserted Successfully...");
		}catch(SQLException sql){
			System.out.println(sql);
		}
		catch (Exception e) {
			System.out.println("Invalid Input");
		}
		
		finally {
			try {
				if (pstmt!=null && pstmt1!=null && connection!=null) {
					pstmt.close();
					pstmt1.close();
					connection.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}