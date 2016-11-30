package ejemplo.forkjoin.max;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaxArrayTask extends RecursiveTask<Integer> {

	private int[] data;
	private int start;
	private int end;

	public MaxArrayTask(int[] data, int start, int end) {
		this.data = data;
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

		MaxArrayTask left = new MaxArrayTask(data, start, start + mid);
		left.fork();

		MaxArrayTask right = new MaxArrayTask(data, start + mid, end);
		right.fork();

		return Math.max(right.join(), left.join());
	}

	private Integer computeDirectly() {
		int max = Integer.MIN_VALUE;
		for (int i = start; i < end; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}
		return max;
	}

	public static void main(String[] args) {

		// create a random data set
		int[] data = new int[1000];
		Random random = new Random();
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(100);
		}

		// submit the task to the pool
		ForkJoinPool pool = new ForkJoinPool(4);
		MaxArrayTask task = new MaxArrayTask(data, 0, data.length);
		System.out.println("Max: " + pool.invoke(task));
	}
}