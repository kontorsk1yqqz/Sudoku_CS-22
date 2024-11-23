package logic.sudoku.gameUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SudokuNumberChooser extends JDialog implements MouseListener {
	private SudokuCell cell;

	// Встановлення клітинки, на якій потрібно вибрати число
	public void setCell(SudokuCell cell) {
		this.cell = cell;
	}

	public SudokuNumberChooser(){
		// Сховати панель інструментів вгорі вікна
		this.setUndecorated(true);
		this.setSize(150, 150);
		this.setBackground(new Color(255, 204, 153, 123)); // Задній фон з напівпрозорим ефектом
		this.setLayout(null); // Встановлюємо абсолютне позиціонування
		addNum(); // Додаємо кнопки з числами
	}

	// Додаємо числа від 1 до 9
	private void addNum() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton btn = new JButton();
				btn.setSize(50, 50); // Розмір кнопки
				btn.setLocation(i * 50, j * 50); // Позиція кнопки
				btn.setText("" + (j * 3 + i + 1)); // Встановлюємо текст на кнопці (число)
				btn.addMouseListener(this); // Додаємо слухача подій
				this.add(btn); // Додаємо кнопку до вікна
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Метод не використовується
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int modes = e.getModifiers(); // Отримуємо інформацію про натискання клавіші
		if ((modes & InputEvent.BUTTON1_MASK) != 0) { // Перевіряємо, чи натиснута ліва кнопка миші
			JButton btn = (JButton) e.getSource(); // Отримуємо кнопку, на яку натиснули
			cell.setText(btn.getText()); // Встановлюємо текст клітинки
		}
		this.dispose(); // Закриваємо вікно після вибору числа
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
