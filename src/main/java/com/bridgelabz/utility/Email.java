package com.bridgelabz.utility;

import java.sql.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public static void sendMail(String email, String subject, String url) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("imterdal@gmail.com", "imrankhancs");
			}
		};

		Session session = Session.getInstance(properties, authenticator);

		try {
			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTMl; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8-bit");
			message.setReplyTo(InternetAddress.parse("imterdal@gmail.com", false));
			message.setSubject(subject, "UTF-8");
			//message.setText("Please Click To verfiy" + url, "UTF-8");
			message.setContent("Please Click To verfiy<a href='" + url + "'>Click</a>", "text/html");
			message.setSentDate(new Date(System.currentTimeMillis()));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));

			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
