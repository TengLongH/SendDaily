package internet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Internet {

	private String postUrl = "http://10.100.29.2:801/eportal/?c=ACSetting&a=Login&wlanuserip=null&wlanacip=null&wlanacname=null&port=&iTermType=1&mac=000000000000&ip=10.114.208.228&redirect=null&session=null";
	
	public void Connect() throws ClientProtocolException, IOException{
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();//标准Cookie策略
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		
		List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
		valuePairs.add( new BasicNameValuePair("DDDDD", "21150211099") );
		valuePairs.add( new BasicNameValuePair("upass", "102857"));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
		HttpPost post = new HttpPost(postUrl);
        post.setEntity(entity);
        CloseableHttpResponse postRes = httpClient.execute(post);//登录
        System.out.println(EntityUtils.toString(postRes.getEntity()));
		postRes.close();
	}
}
