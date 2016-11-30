package ejercicio;

import java.util.function.Function;

public class ParallelForParaleloThreads {

	private static final int NUM_THREADS = 2;

	private double[] sumaCompar = new double[NUM_THREADS];
	
	private Function<Integer,Double> iteration;
	private int globalSize;

	public double parallelForSum(int size,
			Function<Integer, Double> iteration) throws InterruptedException {

		this.iteration = iteration;
		this.globalSize = size;
		
		Thread[] threads = new Thread[NUM_THREADS];

		for (int i = 0; i < NUM_THREADS; i++) {
			int numThread = i;
			Thread t = new Thread(() -> suma(numThread));
			threads[i] = t;
			t.start();
		}

		int suma = 0;
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i].join();
			suma += sumaCompar[i];
		}

		return suma;
	}

	private void suma(int numThread) {
		
		int sumaLocal = 0;		
		int size = globalSize / NUM_THREADS;
		int start = numThread * size;	
		int end = start + size;
		
		for (int i = start; i < end; i++) {
			sumaLocal += iteration.apply(i);
		}
		
		sumaCompar[numThread] = sumaLocal;
	}

	public void exec() throws InterruptedException {

		double[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		double sum = parallelForSum(data.length, i -> data[i]);

		System.out.println("Suma: " + sum);

	}

	public static void main(String[] args) throws InterruptedException {
		new ParallelForParaleloThreads().exec();
	}

}
