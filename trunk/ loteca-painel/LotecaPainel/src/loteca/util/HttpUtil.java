package loteca.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	
	public static String conteudoPagina(String ...params){
		String urlString = params[0];
		HttpHost proxy = new HttpHost("10.70.124.16", 8080);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(urlString);
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

		try {
			HttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();

				String html = toString(instream);
				
			
				return html;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Falha ao acessar Web service");
		}
		return null;
		
	}
	
	private static String toString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}
	

}
