package org.crawlscript.net;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
public class Page {
	
	public  String getHtml(String url, String encoding) {
		try {
			HttpClient httpclient = new DefaultHttpClient();

			HttpGet httpgets = new HttpGet(url);

			HttpResponse response = httpclient.execute(httpgets);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String str = EntityUtils.toString(entity, encoding);
				httpgets.abort();
				return str;
			} else
				return "error";
		} catch (Exception e) {
			return "error";
		}

	}
	
	public  String guessEncoding(byte[] bytes) {
	    String DEFAULT_ENCODING = "UTF-8";
	    org.mozilla.universalchardet.UniversalDetector detector =
	        new org.mozilla.universalchardet.UniversalDetector(null);
	    detector.handleData(bytes, 0, bytes.length);
	    detector.dataEnd();
	    String encoding = detector.getDetectedCharset();
	    detector.reset();
	    if (encoding == null) {
	        encoding = DEFAULT_ENCODING;
	    }
	    return encoding;
	}
	
	public  String getHtmlAuto(String url,String agent,String proxyip,Integer proxyport) {
		try {
			
			HttpHost proxy;
			HttpClient httpclient = new DefaultHttpClient();
		
			if(proxyip!=null && proxyport!=null)
			{
				proxy = new HttpHost(proxyip,proxyport);	
				httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
				System.out.println("agent="+proxyip+" "+proxyport);
			}
			HttpGet httpgets = new HttpGet(url);
			httpgets.setHeader("User-Agent", agent);
			HttpResponse response = httpclient.execute(httpgets);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				byte[] buf=EntityUtils.toByteArray(entity);
				String str=new String(buf,guessEncoding(buf));
				//String str = EntityUtils.toString(entity);
				httpgets.abort();
				return str;
			} else
				return "error";
		} catch (Exception e) {
			return "error";
		}

	}

	public String getHtml(String url) {
		try {
			HttpClient httpclient = new DefaultHttpClient();

			HttpGet httpgets = new HttpGet(url);

			HttpResponse response = httpclient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String str = EntityUtils.toString(entity, "gb2312");
				// System.out.println("Do something");
				// System.out.println(str);

				httpgets.abort();
				return str;
			} else
				return "error";
		} catch (Exception e) {
			return "error";
		}

	}

}
