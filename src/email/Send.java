package email;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Send {

	private List<String> recivers;
	private List<InternetAddress> reciverAddresses;
	private String subject;
	
	private String content;
	private String port = "25";
	private String host ="smtp.163.com";
	private String username = "jinghuashuiyuezi";
	private String password = "c++!huibian@";
	private String sender = "jinghuashuiyuezi@163.com";
	
	public Send(){
		recivers = new ArrayList<String>();
		reciverAddresses = new ArrayList<InternetAddress>();
	}
	public List<String> getRecivers() {
		return recivers;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	
	public void send() throws MessagingException{
		Properties p = new Properties();
		p.put("mail.smtp.host", host );
		p.put("mail.smtp.port", port);
		p.put("mail.smtp.auth", "true");
		p.put("mail.transport.protocol", "smtp");
		
		Session session = Session.getInstance(p);
		session.setDebug(true);
		Transport transport = session.getTransport();
		transport.connect( host, username, password );
		
		MimeMessage message = new MimeMessage( session );
		message.setFrom( new InternetAddress( sender ));
		
		for( String r : recivers ){
			reciverAddresses.add(new InternetAddress(r) );
		}
		
		message.setRecipients(Message.RecipientType.TO, 
				reciverAddresses.toArray( new InternetAddress[recivers.size()]));
		
		message.setSubject( subject );
		message.setContent( content, "text/html;charset=gbk");
		
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
	}
	
}
