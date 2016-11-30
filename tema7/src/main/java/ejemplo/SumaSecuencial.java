package ejemplo;

public class SumaSecuencial {

	public static void main(String[] args) {

		int[] valores = new int[1000];
			
		int suma = 0;
		
		for(int i=0; i<valores.length; i++){
			suma += valores[i];
		}
		
		System.out.println("Suma = "+suma);
	}

}
