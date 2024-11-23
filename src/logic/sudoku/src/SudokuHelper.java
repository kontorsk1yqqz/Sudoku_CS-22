package logic.sudoku.src;

// Допоміжний клас для генерації та перевірки карт гри Судоку
public class SudokuHelper {
	private static int[][] maps = new int[9][9]; // Мапа 9x9 для гри в Судоку

	private static int[] canPutSum = new int[9]; // Массив для підрахунку можливих варіантів для кожної клітинки
	static int[] used = new int[9]; // Массив для відслідковування використаних чисел
	static boolean isOk = true; // Флаг, який вказує, чи вдалося заповнити карту

	// Отримати поточну карту
	public static int[][] getMap() {
		do {
			isOk = true;
			initMaps(); // ініціалізація карти
		} while (!isOk); // повторювати, поки карта не буде правильно заповнена
		return maps;
	}

	// Ініціалізація карти
	private static void initMaps() {
		// ініціалізація карти - заповнення всієї карти значенням -1 (порожні клітинки)
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				maps[i][j] = -1;
			}
		}

		// Заповнення числами від 1 до 9
		for (int num = 1; num <= 9; num++) {
			for (int i = 0; i < 9; i++) {
				used[i] = -1; // скидаємо використані числа
				canPutSum[i] = -1; // скидаємо підрахунок можливих варіантів
			}
			// Перевірка можливості поставити число на кожну клітинку
			for (int i = 0; i < 9; i++) {
				if (canPutSum[i] == -1) {
					canPutSum[i] = getCanPutSum(i, num); // отримуємо кількість можливих місць для числа
				}
				if (canPutSum[i] == 1) {
					used[i] = -1; // позначаємо, що можна використовувати число
				}

				if (canPutSum[i] == 0) {
					canPutSum[i] = -1;
					used[i] = -1;
					// якщо немає можливості поставити число, то видаляємо попередні варіанти
					if (i > 0) {
						// Якщо попереднє число вже використано, очищуємо його
						if (used[i - 1] != -1) {
							clearNum(i - 1, num);
						}
						i -= 2; // йдемо на 2 кроки назад, щоб повторити перевірку
						continue;
					} else {
						isOk = false; // якщо не вдалося заповнити карту, ставимо флаг в false
						return;
					}
				} else {
					// Якщо можна поставити число на поточну клітинку
					boolean flag = false;
					while (!flag) {
						int j = (int) (Math.random() * 9); // випадковий вибір клітинки
						// обчислюємо індекси для підсітки
						int ii = (i / 3) * 3 + j / 3;
						int jj = (i % 3) * 3 + j % 3;
						// Перевіряємо, чи можна поставити число
						if (maps[ii][jj] == -1 && j != used[i] && isCanPut(ii, jj, num)) {
							maps[ii][jj] = num; // ставимо число на клітинку
							used[i] = j; // позначаємо використану клітинку
							canPutSum[i] -= 1; // зменшуємо кількість можливих варіантів
							flag = true; // припиняємо пошук
						}
					}
				}
			}
		}
	}

	// Очищуємо число на підсітці
	private static void clearNum(int i, int num) {
		for (int j = 0; j < 9; j++) {
			int ii = (i / 3) * 3 + j / 3;
			int jj = (i % 3) * 3 + j % 3;
			// якщо число є в підсітці, видаляємо його
			if (maps[ii][jj] == num) {
				maps[ii][jj] = -1;
			}
		}
	}


	//Отримуємо кількість клітинок, де можна поставити число num в підсітці
	private static int getCanPutSum(int i, int num) {
		int sum = 0;
		// Перевіряємо підсітку
		for (int j = 0; j < 9; j++) {
			int ii = (i / 3) * 3 + j / 3;
			int jj = i % 3 * 3 + j % 3;
			// перевіряємо, чи можна поставити число в клітинку
			if (maps[ii][jj] == -1 && isCanPut(ii, jj, num)) {
				++sum;
			}
		}

		return sum;
	}

	// Перевіряє, чи можна поставити число num в клітинку [ii][jj]
	private static boolean isCanPut(int ii, int jj, int num) {
		// Перевіряємо, чи не зустрічається число в тому ж рядку чи стовпці
		for (int i = 0; i < 9; i++) {
			if (maps[ii][i] == num) {
				return false;
			}
			if (maps[i][jj] == num) {
				return false;
			}
		}
		return true;
	}
}
