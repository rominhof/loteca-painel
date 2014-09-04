package loteca.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import loteca.dominio.GrupoCartela;
import loteca.service.GrupoCartelaService;

@FacesConverter(forClass=GrupoCartela.class)
public class GrupoCartelaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		 GrupoCartelaService gcService = new GrupoCartelaService();
		 GrupoCartela gc = new GrupoCartela();
		 if (arg2 == null || arg2.length() == 0) {
	            return new GrupoCartela();
	     }if(arg2!=null && !arg2.equals("")){
	        gc = gcService.consultarPorId(Integer.parseInt(arg2));
	     }
	     return gc;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		 if (arg2 == null) {
	            return null;
	        }
	        if (arg2 instanceof GrupoCartela) {
	        	GrupoCartela gc = (GrupoCartela) arg2;
	            System.out.println("O objeto veio como " + arg2.toString());
	            System.out.println("\tConver estou enviando a string" + arg2.getClass());
	            return ((GrupoCartela) arg2).getId()+"";
	        } else {
	            throw new IllegalArgumentException("object " + arg2 + " is of type " + arg2.getClass().getName() + "; expected type: " + GrupoCartela.class.getName());
	        }
	    }
	

	
	
}
