package loteca.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import loteca.dominio.ConfrontoNovo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static final String filePath = "E:\\Projetos\\Loteca\\ loteca-painel\\exemplo.json";
	private static final String file = "exemplo.json";
	private static final String jsonUrl = "http://www.futebolinterior.com.br/gerados/placar_445.json";

	public static void main(String[] args) throws IOException {
		System.out.println("Inicio - Processo de atualização de cartelas");
		// Baixar Json
		System.out.println("Baixar Json");
		// HttpUtil.downloadFile(jsonUrl, filePath);

		// Comparar
		System.out.println("Comparar Json");

		// popular loteca - gabarito
		System.out.println("Popular gabarito");
		ConfrontoNovo confronto = parserJson(filePath);
		popularLoteria(confronto);

		// Comparar as cartelas
		compararCartelas();
		System.out.println("Comparar cartelas");

		System.out.println("Fim - Processo de atualização de cartelas");
	}

	private static void compararCartelas() {
		// TODO Auto-generated method stub
	}

	private static void popularLoteria(ConfrontoNovo confronto) {
		// TODO Auto-generated method stub

	}

	public static ConfrontoNovo parserJson(String jsonFilePath) {

		Reader reader;
		ConfrontoNovo confronto = null;
		try {
			reader = new InputStreamReader(
					JsonUtil.class.getResourceAsStream("exemplo.json"), "UTF-8");

			Gson gson = new GsonBuilder().create();
			ConfrontoNovo[] listConfrontos = gson.fromJson(reader,
					ConfrontoNovo[].class);
			confronto = listConfrontos[0];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return confronto;
	}
}
