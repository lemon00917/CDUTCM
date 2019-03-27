package com.cdutcm.web.servlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.cdutcm.domain.User;
import com.cdutcm.service.UserService;
import com.cdutcm.utils.CommonsUtils;
import com.cdutcm.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//��ñ�������
		Map<String, String[]> properties = request.getParameterMap();
		User user=new User();
		try {
			//�Լ�ָ��һ������ת��������Stringת��Date��
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(Class clazz, Object value) {
					//��stringת��date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			//ӳ���װ
			BeanUtils.populate(user, properties);
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//private String uid;
		user.setUid(com.cdutcm.utils.CommonsUtils.getUUID());
		//private String telephone;
		user.setTelephone(null);
		//private int state;//�Ƿ񼤻�
		user.setState(0);
		//private String code;//������
		String activeCode = com.cdutcm.utils.CommonsUtils.getUUID();
		user.setCode(activeCode);
		
		
		System.out.println(user);
		//��user���ݸ�service��
		UserService service = new UserService();
		boolean isRegisterSuccess = false;
		try {
			isRegisterSuccess = service.regist(user);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//�Ƿ�ע��ɹ�
		if(isRegisterSuccess){
			//���ͼ����ʼ�
			String emailMsg = "��ϲ��ע��ɹ���������������ӽ��м����˻�"
					+ "<a href='http://120.78.67.244:8080/bysj/active?activeCode="+activeCode+"'>"
							+ "http://120.78.67.244:8080/bysj/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			
			//��ת��ע��ɹ�ҳ��
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//��ת��ʧ�ܵ���ʾҳ��
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}