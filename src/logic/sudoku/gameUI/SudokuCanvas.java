package logic.sudoku.gameUI;

import logic.sudoku.src.SudokuHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SudokuCanvas extends JPanel implements MouseListener {
	SudokuCell[][] cells;
	// Отримання масиву судоку
	int[][] maps = new int[9][9];
	private SudokuNumberChooser selectNum;

	//  За замовчуванням конструктор
	public SudokuCanvas() {
		SudokuMainWindow.usedTime = 0;
		maps = SudokuHelper.getMap(); // Завантажуємо масив судоку
		// Налаштування макету для панелі
		this.setLayout(null);
		cells = new SudokuCell[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// Створення клітинок судоку
				cells[i][j] = new SudokuCell();
				// Встановлення позиції клітинки
				cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50 + (j / 3) * 5);
				if (passRole(SudokuMainWindow.pass)) {
					cells[i][j].setText("" + maps[i][j]);
					// Встановлення кольору фону клітинки
					cells[i][j].setBackground(getColor(maps[i][j]));
					cells[i][j].setEnabled(false);
					cells[i][j].setForeground(Color.gray);
				} else {
					cells[i][j].addMouseListener(this); // Додаємо слухача подій
				}
				this.add(cells[i][j]);
			}
		}
		checkFinish(); // Перевірка на завершення гри
	}

	// Перевірка, чи всі клітинки заповнені правильно
	private void checkFinish() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!check(i, j)) {
					return; // Якщо хоча б одна клітинка неправильна
				}
			}
		}

		// Зупинка таймера користувача
		SudokuMainWindow.userTimeAction.stop();
		// Видалення всіх слухачів клітинок
		clearAllListener();
		// Збільшуємо рівень
		SudokuMainWindow.pass += 1;
		if (SudokuMainWindow.pass > 10) {
			int o = JOptionPane.showConfirmDialog(this, "Ви пройшли всі рівні, хочете почати спочатку?", "", 0);
			if (o == 1) {
				System.exit(0);
			} else {
				SudokuMainWindow.pass = 1; // Перезапуск рівня
			}
		} else {
			JOptionPane.showMessageDialog(this, "Вітаємо, ви пройшли рівень! Час: "
					+ SudokuMainWindow.usedTime + " секунд. Перехід до наступного рівня!");
		}
		// Оновлення підказки рівня
		SudokuMainWindow.lbPass.setText("" + SudokuMainWindow.pass);
		// Перезапуск рівня
		reLoadCanvers();
		// Запуск таймера для користувача
		SudokuMainWindow.userTimeAction.start();
	}

	// Перевірка клітинки на правильність
	private boolean check(int i, int j) {
		if (cells[i][j].getText().isEmpty()) {
			return false; // Якщо клітинка пуста
		}

		for (int k = 0; k < 9; k++) {
			// Перевірка по горизонталі та вертикалі
			if (cells[i][j].getText().trim().equals(cells[i][k].getText().trim()) && j != k) {
				return false;
			}
			if (cells[i][j].getText().trim().equals(cells[k][j].getText().trim()) && i != k) {
				return false;
			}
			// Перевірка в межах 3x3 блоку
			int ii = (i / 3) * 3 + k / 3;
			int jj = (j / 3) * 3 + k % 3;
			if (cells[i][j].getText().trim().equals(cells[ii][jj].getText().trim()) && !(i == ii && j == jj)) {
				return false;
			}
		}
		return true;
	}

	// Перезавантаження судоку
	public void reLoadCanvers() {
		SudokuMainWindow.usedTime = 0;
		maps = SudokuHelper.getMap(); // Завантажуємо новий масив судоку
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.remove(cells[i][j]); // Видаляємо старі клітинки
				// Створюємо нові клітинки
				cells[i][j] = new SudokuCell();
				// Встановлення позиції клітинки
				cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50 + (j / 3) * 5);
				if (passRole(SudokuMainWindow.pass)) {
					cells[i][j].setText("" + maps[i][j]);
					// Встановлення кольору фону клітинки
					cells[i][j].setBackground(getColor(maps[i][j]));
					cells[i][j].setEnabled(false);
					cells[i][j].setForeground(Color.gray);
				} else {
					cells[i][j].addMouseListener(this); // Додаємо слухача подій
				}
				this.add(cells[i][j]);
			}
		}
		this.repaint(); // Перемалюємо панель
		checkFinish(); // Перевірка на завершення гри
	}

	// Генерація випадкових умов для показу числа на клітинці в залежності від рівня
	private boolean passRole(int pass) {
		return Math.random() * 11 > pass;
	}

	// Отримання кольору для числа
	private Color getColor(int i) {
		Color color = Color.pink;
		switch (i) {
			case 1:
				color = new Color(255, 255, 204);
				break;
			case 2:
				color = new Color(204, 255, 255);
				break;
			case 3:
				color = new Color(255, 204, 204);
				break;
			case 4:
				color = new Color(255, 204, 153);
				break;
			case 5:
				color = new Color(204, 255, 153);
				break;
			case 6:
				color = new Color(204, 204, 204);
				break;
			case 7:
				color = new Color(255, 204, 204);
				break;
			case 8:
				color = new Color(255, 255, 255);
				break;
			case 9:
				color = new Color(153, 255, 153);
				break;
			default:
				break;
		}
		return color;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Метод не використовується
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int modes = e.getModifiers();
		if ((modes & InputEvent.BUTTON3_MASK) != 0) { // Клік правою кнопкою миші
			// Очищення вмісту клітинки
			((SudokuCell) e.getSource()).setText("");
		} else if ((modes & InputEvent.BUTTON1_MASK) != 0) { // Клік лівою кнопкою миші
			// Якщо вікно вибору числа відкрите, закриваємо його
			if (selectNum != null) {
				selectNum.dispose();
			}
			// Створюємо нове вікно для вибору числа
			selectNum = new SudokuNumberChooser();
			selectNum.setModal(true); // Встановлюємо модальний режим
			selectNum.setLocation(e.getLocationOnScreen().x, e.getLocationOnScreen().y); // Встановлюємо позицію вікна
			selectNum.setCell((SudokuCell) e.getSource()); // Передаємо клітинку
			selectNum.setVisible(true); // Показуємо вікно
		}
		checkFinish(); // Перевірка завершення
	}

	// Видалення всіх слухачів клітинок
	private void clearAllListener() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				cells[i][j].removeMouseListener(this);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Метод не використовується
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Метод не використовується
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Метод не використовується
	}
}
