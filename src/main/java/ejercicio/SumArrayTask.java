package ejercicio;

import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

public class SumArrayTask extends RecursiveTask<Integer> {

	private int size;
	private Function<Integer, Double> iteration;
	
	private int start;
	private int end;	

	public SumArrayTask(int size, Function<Integer, Double> iteration, int start, int end) {
		this.size = size;
		this.iteration = iteration;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int length = end - start;
		if (length < 5) {
			return computeDirectly();
		} else {
			return computeInSubtasks();
		}
	}

	private Integer computeInSubtasks() {
		int mid = (end - start) / 2;

		SumArrayTask left = new SumArrayTask(size, iteration, start, start + mid);
		left.fork();

		SumArrayTask right = new SumArrayTask(size, iteration, start + mid, end);
		right.fork();

		return right.join() + left.join();
	}

	private Integer computeDirectly() {
		int sum = 0;
		for (int i = start; i < end; i++) {
			sum += iteration.apply(i);
		}
		return sum;
	}

}