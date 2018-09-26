package com.service.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.dao.DAOOperationsImpl;
import com.service.model.Customer;
import com.service.model.Dealer;
import com.service.model.Manager;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("Login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		String userid=request.getParameter("userId");
		String password=request.getParameter("password");
		DAOOperationsImpl obj=new DAOOperationsImpl();
		try {
			Customer customer=obj.checkCustomer(userid, password);
			
				if(customer!=null)
				{
				HttpSession session= request.getSession(true);
				session.setAttribute("customer",customer );
				response.sendRedirect("CustomerHomePage.jsp");
				}
				else 
				{
					Dealer dealer=obj.checkDealer(userid, password);
					if(dealer!=null)
					{
					HttpSession session= request.getSession(true);
					session.setAttribute("dealer",dealer);
					response.sendRedirect("DealerHomePage.jsp");
					}
					else
					{
						Manager manager=obj.checkManager(userid, password);
						if(manager!=null)
						{
							String role=manager.getRole();
							if(role.equals("FM"))
							{
								HttpSession session= request.getSession(true);
								session.setAttribute("manager",manager);
								response.sendRedirect("FactoryManagerHomePage.jsp");
							}
							else
							{
								HttpSession session= request.getSession(true);
								session.setAttribute("manager",manager);
								response.sendRedirect("WarehouseManagerHomePage.jsp");
							}
						}
						else
						{
							request.setAttribute("msg","Wrong username or password");
							request.getRequestDispatcher("Login.jsp").forward(request, response);
						}
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
