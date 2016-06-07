package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import beans.PersonDaily;
import database.Database;
import email.Send;
import internet.Internet;
import util.Log;

public class Go {

	public static void main(String[] args) {
		
		boolean sendTime = false;
		boolean sendAlready = false;
		Log log = Log.getInstance();
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
		if( now.getDay() == 1 || now.getDay() == 2 ){
			Date limit = new Date( now.getYear(), now.getMonth(), now.getDate(), 21, 30 );
			if( now.compareTo(limit) > 0  )return true;
			return false;
		}
		return false;
	}
	public boolean run( String subject ){
		
		Internet net = new Internet();
		Database database = new Database();
		Send send = new Send();
		try{
			net.Connect();
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
			
			send.send();
			return true;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
}
