package cartolafc.dominio;

public enum Placares {
	
		BRASILEIRO_A("441"), 
		BRASILEIRO_B("442"), 
		BRASILEIRO_C("443");
		
		String id;
		
		Placares(String id){
			this.id=id;
		}

		public String getId() {
			return id;
		}
		
		

}
