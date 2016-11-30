package ejemplo;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BurbujaParalelo {

	private static final int NUM_VALS = 100;
	private static final int NUM_THREADS = 5;
	private static final int NUM_ZONAS = 7;
	
	private static int[] valores;
	
	private static boolean fin = false;
	private static int iCompartido;
	private static Lock[] locks;
	
	private static void ordenarBurbuja() {

		int tamannoZona = (NUM_VALS / NUM_ZONAS) + 1;
				
		while (!fin) {
			
			int numZona = 0;
			boolean intercambio = false;

			locks[numZona].lock();
			
			int i = iCompartido--;	//Inicializado a NUM_VALS-1		
						
			if (i <= 0) {
				fin = true;
				locks[numZona].unlock();
				break;
			}
			
			int finZona = tamannoZona;
			for (int j = 0; j < i; j++) {
				
				if (valores[j] > valores[j + 1]) {
					int temp = valores[j];
					valores[j] = valores[j + 1];
					valores[j + 1] = temp;
					intercambio = true;
				}
				
				if (j == finZona) {
					locks[numZona].unlock();
					numZona++;
					locks[numZona].lock();
					finZona += tamannoZona;
				}
			}
			locks[numZona].unlock();
			if (!intercambio) {
				fin = true;
			}
		}
	}
	
	public static void inicializarArrayValores(){
		valores = new int[NUM_VALS];
		Random r = new Random(0);
		for(int i=0; i<NUM_VALS; i++){
			valores[i] = r.nextInt(NUM_VALS * 10);
		}		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		inicializarArrayValores();
		
		iCompartido = NUM_VALS;
		
		locks = new Lock[NUM_ZONAS];
		for(int i=0; i<NUM_ZONAS; i++){
			locks[i] = new ReentrantLock();
		}
		
		Thread[] threads = new Thread[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; i++) {
			Thread t = new Thread() {
				public void run() {
					ordenarBurbuja();
				}
			};
			threads[i] = t;
			t.start();
		}

		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i].join();
		}
		
		System.out.println("Valores ordenados: "+Arrays.toString(valores));
	}
}
