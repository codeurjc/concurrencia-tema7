package ejercicio;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class ParallelForParaleloFJ {

	public double parallelForSum(int size, Function<Integer, Double> iteration)
			throws InterruptedException {

		// submit the task to the pool
		ForkJoinPool pool = new ForkJoinPool(4);
		SumArrayTask task = new SumArrayTask(size, iteration, 0, size);
		return pool.invoke(task);
	}

	public void exec() throws InterruptedException {

		double[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		double sum = parallelForSum(data.length, i -> data[i]);

		System.out.println("Suma: " + sum);

	}

	public static void main(String[] args) throws InterruptedException {
		new ParallelForParaleloFJ().exec();
	}

}
