package cartolafc.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

import cartolafc.dominio.Atleta;
import cartolafc.dominio.Clube;
import cartolafc.dominio.Posicao;
import cartolafc.dominio.Time;
import cartolafc.dominio.TimeId;
import cartolafc.dominio.Times;

public class ParciaisUtil{

		public ParciaisUtil() {
		}

		
		public List<Atleta> getAllAtletas(){
			List<Atleta> atletasList = new ArrayList<Atleta>();
			TreeSet<Atleta> atletas = new TreeSet<Atleta>();
			List<Time> times = getTimes(Boolean.TRUE);
			for(Time time: times){
				atletas.addAll(time.getAtletas());
			}
			int i=1;
			for(Atleta atleta: atletas){
				atleta.setIndice(i);
				Map<String,Object> timesAtleta = new HashMap<String,Object>();
				for(Time time: times){
					if(time.getAtletas().contains(atleta)){
						timesAtleta.put(time.getNome(), time);
					}
				}
				atleta.setTimes(timesAtleta);
				atletasList.add(atleta);
				i++;
			}
			return atletasList;
		}
		
		public void compararTimes(Time time1, Time time2){
			for(Atleta a1: time1.getAtletas()){
				for(Atleta a2: time2.getAtletas()){
					if(a1.equals(a2)){
						time2.setQtdAtletasIguais(time2.getQtdAtletasIguais()+1);
						a2.setAtletaIgual(Boolean.TRUE);
						a1.setAtletaIgual(Boolean.TRUE);
						a1.setQtdAtletasIguais(a1.getQtdAtletasIguais()+1);
					}
				}
			}
		}
		
		public List<Time> getTimes(Boolean local) {
			String url = "http://127.0.0.1:8080/CartolaPainel/json/";
			if(!local){
				 url = "http://api.cartola.globo.com/time_adv/";
			}
			
			List<Time> times = new ArrayList<Time>();
			
			String jsonTime1 = getJsonTime(url+Times.BREGAFC.getSlug()+".json");
			Time time1 = getTime(jsonTime1);
			time1.setAtletas(getAtletas(jsonTime1));
			times.add(time1);
			
			String jsonTime2 = getJsonTime(url+Times.PICAPAUSFC.getSlug()+".json");
			Time time2 = getTime(jsonTime2);
			time2.setAtletas(getAtletas(jsonTime2));
			times.add(time2);
		
			String jsonTime3 = getJsonTime(url+Times.RMRFC.getSlug()+".json");
			Time time3 = getTime(jsonTime3);
			time3.setAtletas(getAtletas(jsonTime3));
			times.add(time3);
			
			String jsonTime4 = getJsonTime(url+Times.AINEGAFC.getSlug()+".json");
			Time time4 = getTime(jsonTime4);
			time4.setAtletas(getAtletas(jsonTime4));
			times.add(time4);
			
			String jsonTime5 = getJsonTime(url+Times.BRITOJRTEAM.getSlug()+".json");
			Time time5 = getTime(jsonTime5);
			time5.setAtletas(getAtletas(jsonTime5));
			times.add(time5);
			
			String jsonTime6 = getJsonTime(url+Times.MELUCOSSOCCER.getSlug()+".json");
			Time time6 = getTime(jsonTime6);
			time6.setAtletas(getAtletas(jsonTime6));
			times.add(time6);
			
			String jsonTime7 = getJsonTime(url+Times.SANTOSCRATOFC.getSlug()+".json");
			Time time7 = getTime(jsonTime7);
			time7.setAtletas(getAtletas(jsonTime7));
			times.add(time7);
			
			String jsonTime8 = getJsonTime(url+Times.AGFC.getSlug()+".json");
			Time time8 = getTime(jsonTime8);
			time8.setAtletas(getAtletas(jsonTime8));
			times.add(time8);
			
			String jsonTime9 = getJsonTime(url+Times.TIOXICO.getSlug()+".json");
			Time time9 = getTime(jsonTime9);
			time9.setAtletas(getAtletas(jsonTime9));
			times.add(time9);
			
			String jsonTime10 = getJsonTime(url+Times.DUDUSFC.getSlug()+".json");
			Time time10 = getTime(jsonTime10);
			time10.setAtletas(getAtletas(jsonTime10));
			times.add(time10);
			
			String jsonTime11 = getJsonTime(url+Times.PRISONWITHOUTBARS.getSlug()+".json");
			Time time11 = getTime(jsonTime11);
			time11.setAtletas(getAtletas(jsonTime11));
			times.add(time11);
			
			Collections.sort(times,new Comparator<Time>(){  
	            public int compare(Time t1, Time t2){  
	                return Double.compare(t1.getPontuacao(),t2.getPontuacao());  
	            }  
	        }); 
			Collections.reverse(times);
			
			return times;
			
		}
		
		public Time getTimeComAtletas(Boolean local, String slug) {
			String url = "http://127.0.0.1:8080/CartolaPainel/json/";
			if(!local){
				 url = "http://api.cartola.globo.com/time_adv/";
			}
			String jsonTime = getJsonTime(url+slug+".json");
			Time time = getTime(jsonTime);
			time.setAtletas(getAtletas(jsonTime));
			
			return time;
		}


		private List<Atleta> getAtletas(String jsonString) {

			List<Atleta> atletas = new ArrayList<Atleta>();

			try {
				JSONObject atletaList = new JSONObject(jsonString);
				JSONArray atletasArray = atletaList.getJSONArray("atleta");

				JSONObject atleta;

				for (int i = 0; i < atletasArray.length(); i++) {
					atleta = atletasArray.getJSONObject(i);

					//System.out.println("apelido=" + atleta.getString("apelido"));

					Atleta objetoAtleta = new Atleta();
					try{
						
						objetoAtleta.setId(atleta.getInt("id"));
						objetoAtleta.setApelido(atleta.getString("apelido"));
						objetoAtleta.setDataJogo(atleta.getString("partida_data"));
						
						//Add Clube
						Clube objetoClube = new Clube();
						JSONObject clubeJO = new JSONObject();
						clubeJO = atleta.getJSONObject("clube");
						objetoClube.setAbreviacao(clubeJO.getString("abreviacao").toUpperCase());
						objetoClube.setId(clubeJO.getInt("id"));
						objetoClube.setSlug(clubeJO.getString("slug"));
						objetoAtleta.setClube(objetoClube);
						
						//Add Posicao
						Posicao objetoPosicao = new Posicao();
						JSONObject posicaoJO = new JSONObject();
						posicaoJO = atleta.getJSONObject("posicao");
						objetoPosicao.setAbreviacao(posicaoJO.getString("abreviacao").toUpperCase());
						objetoPosicao.setId(posicaoJO.getInt("id"));
						objetoAtleta.setPosicao(objetoPosicao);
						
						//Add Clube Visitante
						Clube objetoClubeV = new Clube();
						JSONObject clubeVJO = new JSONObject();
						clubeVJO = atleta.getJSONObject("partida_clube_visitante");
						objetoClubeV.setAbreviacao(clubeVJO.getString("abreviacao").toUpperCase());
						objetoClubeV.setId(clubeVJO.getInt("id"));
						objetoClubeV.setSlug(clubeVJO.getString("slug"));
						objetoAtleta.setClubeVisitante(objetoClubeV);
						
						//Add Clube Casa
						Clube objetoClubeC = new Clube();
						JSONObject clubeCJO = new JSONObject();
						clubeCJO = atleta.getJSONObject("partida_clube_casa");
						objetoClubeC.setAbreviacao(clubeCJO.getString("abreviacao").toUpperCase());
						objetoClubeC.setId(clubeCJO.getInt("id"));
						objetoClubeC.setSlug(clubeCJO.getString("slug"));
						objetoAtleta.setClubeCasa(objetoClubeC);
						
						objetoAtleta.setPontos(atleta.getDouble("pontos"));
					}catch(org.json.JSONException ex){
						//System.out.println("Atleta sem foto!");
					}

					atletas.add(objetoAtleta);
				}
			} catch (JSONException e) {
				System.out.println("Erro ao converter para JSON Atleta");
			}

			return atletas;
		}
		
		private Time getTime(String jsonString) {

			Time objetoTime = new Time();

			try {
				JSONObject timeList = new JSONObject(jsonString);
				JSONObject timejs = timeList.getJSONObject("time");
				
				objetoTime.setSlug(timejs.getString("slug"));
				objetoTime.setNome(timejs.getString("nome"));
				objetoTime.setNome_cartola(timejs.getString("nome_cartola"));
				objetoTime.setPontuacao(timejs.getDouble("pontuacao"));
				objetoTime.setPatrimonio(timejs.getDouble("patrimonio"));
				objetoTime.setId(new TimeId(new Long(timejs.getInt("cadun_id")), timejs.getInt("rodada")));
				JSONObject imgescudo = timejs.getJSONObject("imagens_escudo");
				objetoTime.setImg_escudo_32x32(imgescudo
						.getString("img_escudo_32x32"));
				
			} catch (JSONException e) {
				System.out.println("Erro ao converter para JSON Time");
			}

			return objetoTime;
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
		
	
	public String getJsonTime(String ...params){
		String urlString = params[0];
		//HttpHost proxy = new HttpHost("10.70.124.16", 8080);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(urlString);
       // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

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
			System.out.println("Falha ao acessar Web service");
		}
		return null;
		
	}
	
	
	
	public void baixarTodosArquivosAtualizados(String path){
		for(Times timeEnum: Times.values()){
			baixarArquivosAtualizados(timeEnum, path);
		}
	}
	
	public void baixarArquivosAtualizados(Times timeEnum, String path){
		String timeJson = this.getJsonTime(timeEnum.getUrl());
		System.out.println(timeJson);
		
		BufferedWriter br;
		
		
		
		try {
			br = new BufferedWriter(new FileWriter(new File(path+"json/"+timeEnum.getSlug()+".json")));
			
			System.out.println("gravando arquivo em: "+path+"json/"+timeEnum.getSlug());
			
			br.write(timeJson);  
			br.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
	public String getConfrontosDaRodadaHtml(){
		String paginaToda = HttpUtil.conteudoPagina("http://globoesporte.globo.com/cartola-fc/");
		String jogosDaRodadaHtml = paginaToda.split("<div id=\"jogos-rodada\">")[1].split("<div id=\"top-maiores-pontuadores\">")[0];
		return jogosDaRodadaHtml;
	}
	



}