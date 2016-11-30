package ejemplo.forkjoin.filesearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FileTask extends RecursiveTask<List<File>> {

	private File dir;
	private String pattern;

	public FileTask(File dir, String pattern) {
		this.dir = dir;
		this.pattern = pattern;
	}

	@Override
	protected List<File> compute() {

		List<File> filesFound = new ArrayList<>();
		List<FileTask> taskList = new ArrayList<>();

		for (File file : dir.listFiles()) {
			
			if (file.isDirectory()) {				
				FileTask task = new FileTask(file,pattern);
				task.fork();
				taskList.add(task);
				
			} else {
				
				if (file.getName().contains(pattern)) {
					filesFound.add(file);
				}
			}
		}

		// Wait for tasks
		for (FileTask task : taskList) {
			filesFound.addAll(task.join());
		}

		return filesFound;
	}

	public static void main(String[] args) {

		ForkJoinPool pool = new ForkJoinPool();
		FileTask task = new FileTask(new File("/home/mica/Data"), "notas.txt");
		List<File> fileList = pool.invoke(task);
		System.out.println("Files found: " + fileList);
	}
}
