package cartolafc.teste;

import java.util.List;

import loteca.dominio.Confronto;

import cartolafc.dominio.Placares;
import cartolafc.util.PlacaresUtil;


public class MainTeste {

	public static void main (String args[]){
		
		//ParciaisUtil pu = new ParciaisUtil();
	/**	
		List<Time> times = new ArrayList<Time>();
		times=pu.getTimes();
		for(Time time: times){
			System.out.println(time.getNome());
		}
		**/
		/**int i=1;
		for(Atleta atleta: pu.getAllAtletas()){
			System.out.print(i+ " - ");
			System.out.print(atleta);
			System.out.println(" - "+atleta.getTimes().size());
			i++;
		}*/
		
		//JPAUtil.getEntityManager();
		
		PlacaresUtil plU = new PlacaresUtil();
		String placares = plU.getJsonPlacares(Placares.BRASILEIRO_A.getId());

		List<Confronto> confrontos = plU.getConfrontos(placares);
		
		for (Confronto confronto : confrontos) {
			System.out.println(confronto.getTempo()+": "+confronto.getMandante()+" "+confronto.getPtn_mandante()+" x "+confronto.getProx_visitante()+" "+confronto.getVisitante()+" ");
		}

	}
	
	
	
}
