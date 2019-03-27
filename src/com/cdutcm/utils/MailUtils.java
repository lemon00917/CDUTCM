package com.cdutcm.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.����һ���������ʼ��������Ự���� Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");//ʹ��Э�飺smtp
		props.setProperty("mail.smtp.host", "smtp.qq.com");//Э���ַ
		props.setProperty("mail.smtp.port", "587");//Э��˿�
		props.setProperty("mail.smtp.auth", "true");// ָ����֤Ϊtrue
		//qq:ssl��ȫ��֤
		//props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLServerSocket");
		//props.setProperty("mail.smtp.socketFactory.fallback", "false");
		//props.setProperty("mail.smtp.socketFactory.port", "587");
		//props.setProperty("mail.smtp.ssl.enable","true");
		

		// ������֤��
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("184441376@qq.com", "fpmgjfsfvyribgfd");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.����һ��Message�����൱�����ʼ�����
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("184441376@qq.com")); // ���÷�����

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // ���÷��ͷ�ʽ�������

		message.setSubject("�û�����");
		// message.setText("����һ�⼤���ʼ�����<a href='#'>���</a>");

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.���� Transport���ڽ��ʼ�����

		Transport.send(message, message.getAllRecipients());
	}
}
