package main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import beans.PersonDaily;
import database.Database;
import email.Send;
import internet.Internet;
import util.Log;

public class Go {
	
	private static Log log = Log.getInstance();
	public static void main(String[] args) {
		
		boolean sendTime = false;
		boolean sendAlready = false;
		
		Go go = new Go();
		SimpleDateFormat format = new SimpleDateFormat("YY.MM.ddE");
		Thread.currentThread().setName("SendDaily");
		while( true ){
			sendTime = go.sendTime();
			if( sendTime ){
				if( !sendAlready){
					String subject = "综合组" + format.format(new Date() ) + "日报";
					sendAlready = go.run(subject);
					log.getBuffer().append("send daily report is ");
					if( sendAlready ){
						log.getBuffer().append("success!");
					}else{
						log.getBuffer().append("fail");
					}
				}
			}else{
				sendAlready = false;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean sendTime( ){
		Date now = new Date();
		if( now.getDay() == 3 || now.getDay() == 5 ){
			Date limit = new Date( now.getYear(), now.getMonth(), now.getDate(), 10, 30 );
			if( now.compareTo(limit) > 0  )return true;
			return false;
		}
		return false;
	}
	public boolean run( String subject ){
		
		boolean success = false;
		Database database = Database.getInstance();
		Send send = new Send();
		try{
			Internet.Connect();
			send.setSubject(subject);
			send.setContent( database.getProjectDaily("综合组") );
			
			List<PersonDaily> persons = database.getProjectPerson("综合组");
			for( PersonDaily p : persons ){
				send.getRecivers().add(p.getEmail());
			}
			send.getRecivers().clear();
			send.getRecivers().add("1739914236@qq.com");
			send.getRecivers().add("tenglongh@126.com");
			send.getRecivers().add("hongtenglong@sohu.com");
			success = send.send();
		}catch( Exception e ){
			log.getBuffer().append(e.getMessage());
			log.bufferWrite();
		}finally{
			Internet.disConnect();
		}
		return success;
	}
}