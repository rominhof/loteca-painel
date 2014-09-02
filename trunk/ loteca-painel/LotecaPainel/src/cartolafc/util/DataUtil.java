package cartolafc.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.SelectItem;

public class DataUtil {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static String formataData(Date data){
		return sdf.format(data);
	}
	
	public static Date addDias(Date date, int a){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.DATE, gc.get(Calendar.DATE) + a);  
		return gc.getTime();		
	}
	
	public static Date addMeses(Date date, int a){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + a);  
		return gc.getTime();		
	}
	
	public static Date addDiasRdo(Date date){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		if (date.getDay()==6) {
			gc.set(Calendar.DATE, gc.get(Calendar.DATE) + 2);
		} else {
			gc.set(Calendar.DATE, gc.get(Calendar.DATE) + 1);  
		}
		return gc.getTime();		
	}
	
	public static Date setUltimoHorarioDia(Date data){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		gc.set(GregorianCalendar.MILLISECOND, 0);
		return gc.getTime();
	}
	
	public static Date setPrimeiroHorarioDia(Date data){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(data);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		gc.set(GregorianCalendar.MILLISECOND, 0);
		return gc.getTime();
	}
	
	public static Date getDateUltimoDiaDoMes(Date data){
		GregorianCalendar ultimoDiaMes = new GregorianCalendar();
		ultimoDiaMes.setTime(data);
        ultimoDiaMes.set(Calendar.DATE, 1);
        ultimoDiaMes.add(Calendar.MONTH, 1);
        ultimoDiaMes.add(Calendar.DATE, -1);
        return ultimoDiaMes.getTime();
	}
	
	public static Date getDateUltimoDiaDoMes(Integer ano, Integer mes){
		GregorianCalendar ultimoDiaMes = new GregorianCalendar();
        ultimoDiaMes.set(Calendar.YEAR, ano);
        ultimoDiaMes.set(Calendar.MONTH, mes);
        ultimoDiaMes.set(Calendar.DATE, 1);
        //ultimoDiaMes.add(Calendar.MONTH, 1);
        ultimoDiaMes.add(Calendar.DATE, -1);
        return ultimoDiaMes.getTime();
	}	

	public static int getUltimoDiaDoMes(Integer ano, Integer mes){
		GregorianCalendar ultimoDiaMes = new GregorianCalendar();
        ultimoDiaMes.set(Calendar.YEAR, ano);
        ultimoDiaMes.set(Calendar.MONTH, mes);
        ultimoDiaMes.set(Calendar.DATE, 1);
        ultimoDiaMes.add(Calendar.MONTH, 1);
        ultimoDiaMes.add(Calendar.DATE, -1);
        return ultimoDiaMes.get(Calendar.DATE);
	}	
	
	public static int getDiasUteisDoMes(Date dataInicio, Date dataFim){
		GregorianCalendar i = new GregorianCalendar();
        i.setTime(dataInicio);
        GregorianCalendar f = new GregorianCalendar();
        f.setTime(dataFim);
        Integer cont = 0;
        for (int j = 1; i.compareTo(f)<=0; j++) {
			if ( (i.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (i.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) ){
        		cont++;
        	}
			i.add(Calendar.DATE, 1);
		}
		return cont;
	}
	
	public static List<SelectItem> listMesesdoPeriodo(Date dataInicio, Date dataFim){
		List<SelectItem> periodos = new ArrayList<SelectItem>();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dataInicio);
		int inicio = gc.get(Calendar.YEAR)*100 + gc.get(Calendar.MONTH)+1;
		gc.setTime(dataFim);
		int fim = gc.get(Calendar.YEAR)*100 + gc.get(Calendar.MONTH)+1;
		for (Integer i = inicio; i <= fim; i++) {
			if (Integer.parseInt(i.toString().substring(4))<=12 && Integer.parseInt(i.toString().substring(4)) > 0) {
				periodos.add(new SelectItem(i.toString(),i.toString().substring(4)+"/"+i.toString().subSequence(0, 4)));
			}			
		}
		return periodos;
	}
	
	public static List<Date> resolveSemanasDoMes(Integer ano, Integer mes){  
        GregorianCalendar calendar = new GregorianCalendar();  
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        GregorianCalendar ultimoDiaMes = new GregorianCalendar();
        ultimoDiaMes.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        ultimoDiaMes.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        ultimoDiaMes.set(Calendar.DATE, calendar.get(Calendar.DATE));
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, -1);
        GregorianCalendar primeiroDiaMes = new GregorianCalendar();
        primeiroDiaMes.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        primeiroDiaMes.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        primeiroDiaMes.set(Calendar.DATE, calendar.get(Calendar.DATE));
        
        List<Date> datas = new ArrayList<Date>();        
        while (primeiroDiaMes.compareTo(ultimoDiaMes)<=0) {
        	if (primeiroDiaMes.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || 
        			primeiroDiaMes.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY ||
        			primeiroDiaMes.get(Calendar.DATE)==1 || 
        			primeiroDiaMes.get(Calendar.DATE)==ultimoDiaMes.get(Calendar.DATE)){
        		datas.add(primeiroDiaMes.getTime());
        	}
            primeiroDiaMes.add(Calendar.DATE, 1);
		}
        return datas;  
    }  


	public static Date convertStringToDate(String yyyy_MM_dd_HH_mm_ss){
		Date data = null;
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(0, 4)));
			c.set(Calendar.MONTH, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(5, 7))-1);
			c.set(Calendar.DATE, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(8, 10)));				
			c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(11, 13)));				
			c.set(Calendar.MINUTE, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(14, 16)));				
			c.set(Calendar.SECOND, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(17, 19)));				
			c.set(Calendar.MILLISECOND, Integer.parseInt(yyyy_MM_dd_HH_mm_ss.substring(20, yyyy_MM_dd_HH_mm_ss.length()))*1000);
			data = c.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
										
		return data;		
	}
	
	public static Date criaData(Integer dia, Integer mes, Integer ano){
		GregorianCalendar data = new GregorianCalendar();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, mes-1);
		data.set(Calendar.DATE, dia);
		return data.getTime();
	}
}
