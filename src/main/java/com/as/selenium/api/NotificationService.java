package com.as.selenium.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.as.selenium.util.Reporting;

public class NotificationService {
public NotificationService(){}
	
	// Common variables
		public static String host;
		public static String from;
		public static String to;
		public static String tocc;
		public static String bcc;
		public static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		public static String subject = "";
		public static String fileName = "mail.properties"; 
		
		static {
			Properties mailprops = new Properties();
		try {
			File file = new File("").getCanonicalFile();
			FileInputStream in = new FileInputStream(file.getParent()+"/resources/"+fileName);
			mailprops.load(in);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		from = mailprops.getProperty("mailFrom");
		to = mailprops.getProperty("mailTo");
		host = mailprops.getProperty("host");
		tocc = mailprops.getProperty("mailToCC");
		bcc = mailprops.getProperty("mailToBCC");
		System.out.println(host);
		System.out.println(from);
		}
		
		
	
	public static void sendEmail(String modDate, String message) {
		
		// Set properties
		Properties props = new Properties();	
		props.put("mail.smtp.host", host);
		props.put("mail.debug", "true");
		try {
			File file = new File("").getCanonicalFile();
			FileInputStream in = new FileInputStream(file.getParent()+"/resources/mail.properties");
			props.load(in);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {		
			
 
			// Get session
			Session session = Session.getInstance(props);
			
		    // Instantiate a message
		    Message msg = new MimeMessage(session);
		    
		    // Set the FROM message
		    msg.setFrom(new InternetAddress(from));
	 
		    // use 'new InternetAddress(to)' for only one address.
		    InternetAddress[] address = {new InternetAddress(to)};
		    msg.setRecipients(Message.RecipientType.TO, address);
		                  
		  // The recipients can be more than one so we use an array but you can
		    // use 'new InternetAddress(cc)' for only one address.
		    String[] cc = {tocc};
		    
		    InternetAddress[] addressCC = new InternetAddress[cc.length];
            for (int i = 0; i < cc.length; i++)
            {
                addressCC[i] = new InternetAddress(cc[i]);
            }
		    
            msg.setRecipients(RecipientType.CC, addressCC);
		    		    
		    
		    // Set the message subject and date we sent it.
		    msg.setSubject(subject.concat(df.format(new Date())).concat("Fathom Automation Test -" + message));
		    msg.setSentDate(new Date());
		 
		    // Set message content
		  //  msg.setText(body);
		    
		    Multipart mp = new MimeMultipart();
		    //Create a new part for body
		    MimeBodyPart matter = new MimeBodyPart();
		    String body = "Dear All,".concat("\n").concat("\n").concat("Fathom application test completed on ").concat(modDate).concat("\n").concat("\n").concat(Reporting.printResponseReport()).concat("\n").concat("\n").concat("Thanks and Regards").concat("\n");
		    matter.setText(body);
		    //matter.setText(modDate);
		    mp.addBodyPart(matter);

		    msg.setContent(mp);
	 
		    // Send the message
		    Transport.send(msg);
	}
	catch (MessagingException mex) {
	    mex.printStackTrace();
	}
	catch(Exception e){
		e.printStackTrace();
	}
   
   }
}
