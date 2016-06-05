package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import beans.PersonDaily;
import database.Database;
import email.Send;
import internet.Internet;

public class Go {

	public static void main(String[] args) {
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd");
		String subject = "综合组" + format.format(calendar.getTime()) + "日报";
		
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
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

}
