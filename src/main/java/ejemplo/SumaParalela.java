package ejemplo;

public class SumaParalela {

	private static final int NUM_THREADS = 2;
	private static final int NUM_VALS = 1000;

	private static double[] valores;
	private static double[] sumaCompar = new double[NUM_THREADS];

	private static void suma(int numThread) {
		
		int tamChunk = NUM_VALS / NUM_THREADS;

		int start = tamChunk * numThread;
		int end;

		if (numThread == NUM_THREADS - 1) {
			end = NUM_VALS - 1;
		} else {
			end = start + tamChunk;
		}

		int sumaLocal = 0;

		for (int i = start; i < end; i++) {
			sumaLocal += valores[i];
		}

		sumaCompar[numThread] = sumaLocal;
	}

	private static void inicializaArrayValores() {
		valores = new double[NUM_VALS];
		for (int i = 0; i < NUM_VALS; i++) {
			valores[i] = i;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		inicializaArrayValores();
		
		Thread[] threads = new Thread[NUM_THREADS];

		for (int i = 0; i < NUM_THREADS; i++) {

			final int numThread = i;

			Thread t = new Thread() {
				public void run() {
					suma(numThread);
				}
			};

			threads[i] = t;
			t.start();
		}

		int suma = 0;
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i].join();
			suma += sumaCompar[i];
		}

		System.out.println("Suma de los elementos del array:"+suma);
	}
}