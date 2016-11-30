package ejercicio;

import java.util.function.Function;

public class ParallelForSecuencial {

	public double parallelForSum(int size,
			Function<Integer, Double> iteration) {
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += iteration.apply(i);
		}
		return sum;
	}

	public void exec() {

		double[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		double sum = parallelForSum(data.length, i -> data[i]);

		System.out.println("Suma: " + sum);

	}

	public static void main(String[] args) {
		new ParallelForSecuencial().exec();
	}

}
