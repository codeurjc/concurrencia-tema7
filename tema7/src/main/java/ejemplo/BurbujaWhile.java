package ejemplo;

import java.util.Arrays;
import java.util.Random;

public class BurbujaWhile {

	private static final int NUM_VALS = 100;
	private static int[] valores;

	public static void ordenarBurbuja() {

		int i = NUM_VALS;

		while (true) {

			i--;

			if (i <= 0) {
				break;
			}

			boolean intercambio = false;

			for (int j = 0; j < i; j++) {

				if (valores[j] > valores[j + 1]) {
					int temp = valores[j];
					valores[j] = valores[j + 1];
					valores[j + 1] = temp;
					intercambio = true;
				}
			}

			if (!intercambio) {
				break;
			}
		}
	}

	public static void inicializarArrayValores() {
		valores = new int[NUM_VALS];
		Random r = new Random();
		for (int i = 0; i < NUM_VALS; i++) {
			valores[i] = r.nextInt(NUM_VALS * 10);
		}
	}

	public static void main(String[] args) {

		inicializarArrayValores();

		ordenarBurbuja();

		System.out.println("Valores ordenados: " + Arrays.toString(valores));
	}

}
