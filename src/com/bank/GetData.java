package com.bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection connection=null;
		PreparedStatement pstmt1=null,pstmt2=null;
		ResultSet rs1=null,rs2=null;
		try
		{
			String search=request.getParameter("search");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tiger");
			pstmt1=connection.prepareStatement("select bc.account_number,bc.name,bc.location,bb.balance from bank_customers bc,bank_balance bb where bc.account_number=bb.account_number and bc.location=?");
			pstmt1.setString(1, search);
			rs1=pstmt1.executeQuery();
			while (rs1.next()) {
				System.out.println(rs1.getInt(1)+" "+rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getInt(4));
			}
			
			pstmt2=connection.prepareStatement("select sum(balance) from bank_customers bc,bank_balance bb where bc.account_number=bb.account_number and bc.location=?");
			pstmt2.setString(1, search);
			rs2=pstmt2.executeQuery();
			rs2.next();
			System.out.println(rs2.getInt(1));
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		catch (Exception e)
		{
			System.out.println("Invalid Input");
		}
		finally {
			try {
				if (pstmt1!=null && pstmt2!=null && connection!=null) {
					rs1.close();
					rs2.close();
					pstmt1.close();
					pstmt2.close();
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("doPost");
		doGet(request, response);
	}

}
