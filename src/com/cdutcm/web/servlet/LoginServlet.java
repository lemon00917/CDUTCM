package com.cdutcm.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cdutcm.domain.User;
import com.cdutcm.service.UserService;


public class LoginServlet extends HttpServlet {
 public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	//ʹ��request�����getSession()��ȡsession�����session�������򴴽�һ��
	      
	       //���������û���������
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			HttpSession session = request.getSession();
		      //�����ݴ洢��session��
		     session.setAttribute("username",username);
			//���û��������봫�ݸ�service��
			UserService service = new UserService();
			User user = null;
			try {
				user = service.login(username,password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//�ж��û��Ƿ��¼�ɹ� user�Ƿ�Ϊ��
			if(user!=null) {
				//��¼�ɹ�
				//�ض�����ҳ
				System.out.println(session.getAttribute("username"));
				response.sendRedirect(request.getContextPath()+"/index.html");
			}
			else {
				request.setAttribute("loginError", "�û������������");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				
			}
			}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 }
