package loteca.teste;

public class TesteThread extends Thread {
	
	private Boolean threadLigada = true;
	
	@Override
	public void run() {
		while(threadLigada){
			System.out.println("Fa�a alguma coisa");
			try {
				this.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
