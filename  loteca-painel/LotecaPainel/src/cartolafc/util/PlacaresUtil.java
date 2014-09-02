package cartolafc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import loteca.dominio.Confronto;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class PlacaresUtil{
	
		private static final String URL_FI = "http://www.futebolinterior.com.br/gerados/placar_";

		public PlacaresUtil() {
		}

		
	
		public String getJsonPlacares(String idPlacar){
			String urlString = URL_FI+idPlacar+".json";
			HttpHost proxy = new HttpHost("10.70.124.16", 8080);
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urlString);
	        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

			try {
				HttpResponse response = httpclient.execute(httpget);

				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();

					String json = toString(instream);
					
				
					return json;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Falha ao acessar Web service do FI");
			}
			return null;
			
		}
		
		public List<Confronto> getConfrontos(String json) {

			List<Confronto> confrontos = new ArrayList<Confronto>();

			try {
				JSONArray confrontoList = new JSONArray(json);
				JSONArray confrontosArray = confrontoList.getJSONObject(3).getJSONArray("tabela");

				JSONObject confronto;

				for (int i = 0; i < confrontosArray.length(); i++) {
					confronto = confrontosArray.getJSONObject(i);

					Confronto objetoConfronto = new Confronto();
					try{
						
						objetoConfronto.setId(confronto.getInt("id"));
						objetoConfronto.setMandante(confronto.getString("mandante"));
						objetoConfronto.setMandante(confronto.getString("visitante"));
						objetoConfronto.setPtn_mandante(confronto.getInt("ptn_mandante"));
						objetoConfronto.setPtn_visitante(confronto.getInt("ptn_visitante"));
						objetoConfronto.setTempo(confronto.getString("tempo"));
						
					}catch(org.json.JSONException ex){
					
					}

					confrontos.add(objetoConfronto);
				}
			} catch (JSONException e) {
				System.out.println("Erro ao converter para JSON Confronto");
			}

			return confrontos;
		}
	
		private String toString(InputStream is) throws IOException {

			byte[] bytes = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int lidos;
			while ((lidos = is.read(bytes)) > 0) {
				baos.write(bytes, 0, lidos);
			}
			return new String(baos.toByteArray());
		}
		
	



}