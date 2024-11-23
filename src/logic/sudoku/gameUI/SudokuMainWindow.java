package logic.sudoku.gameUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;


// Головне вікно судоку
public class SudokuMainWindow extends JFrame {

	public static int pass = 1; // Рівень
	public static JLabel lbPass; // Мітка для відображення рівня
	public static long usedTime = 0; // Час гри
	private SudokuCanvas panelCanvers; // Основна ігрова зона
	public static Timer userTimeAction;

	// Конструктор за замовчуванням
	public SudokuMainWindow() {
		// Ініціалізація
		init();
		// Додавання компонентів
		addComponent();// Додавання основної ігрової зони
		addCanvers();
	}


	// Додавання основної ігрової зони
	private void addCanvers() {
		panelCanvers = new SudokuCanvas();
		panelCanvers.setBorder(new TitledBorder("Ігрова зона"));

		// Додавання основної ігрової зони до вікна
		this.add(panelCanvers, BorderLayout.CENTER);
	}

	// Додавання компонентів
	private void addComponent() {
		JPanel panelComponent = new JPanel();
		// Додавання зони повідомлень
		addPanelMsg(panelComponent);
		// Додавання зони часу
		addPanelTime(panelComponent);

		// Додавання компонентів до верхньої частини вікна
		this.add(panelComponent, BorderLayout.NORTH);
	}

	private void addPanelTime(JPanel panelComponent) {
		JPanel panelTime = new JPanel();
		panelTime.setBorder(new TitledBorder("Час"));
		panelTime.setLayout(new GridLayout(2, 1));

		final JLabel lbSysTime = new JLabel();
		final JLabel lbUserTime = new JLabel();

		panelTime.add(lbSysTime, BorderLayout.NORTH);
		panelTime.add(lbUserTime, BorderLayout.SOUTH);

		// Таймер для системного часу
		Timer sysTimeAction = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				long timeMillis = System.currentTimeMillis();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				lbSysTime.setText("    Системний час:  " + df.format(timeMillis));
			}
		});
		sysTimeAction.start();
		userTimeAction = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lbUserTime.setText("    Час гри:  " + (++usedTime)+ " сек.");
			}
		});
		userTimeAction.start();

		panelComponent.add(panelTime, BorderLayout.EAST);
	}


	// Додавання зони повідомлень
	private void addPanelMsg(JPanel panelComponent) {
		panelComponent.setLayout(new GridLayout(1, 3));
		Font font14 = new Font("", 4, 14);
		Font font28 = new Font("", 2, 28);

		JPanel panelMsg = new JPanel();
		panelMsg.setBorder(new TitledBorder("Повідомлення"));

		JLabel lbPass1 = new JLabel("Рівень:  ");
		lbPass1.setFont(font14);
		panelMsg.add(lbPass1);

		// Відображення поточного рівня
		lbPass = new JLabel("" + pass);
		lbPass.setForeground(Color.RED);
		lbPass.setFont(font28);
		panelMsg.add(lbPass);

		JLabel lbPass2 = new JLabel(" рівень/загалом 10 рівнів");
		lbPass2.setFont(font14);
		panelMsg.add(lbPass2);

		panelComponent.add(panelMsg, BorderLayout.CENTER);
	}


	// Ініціалізація інтерфейсу
	private void init() {
		ImageIcon image = new ImageIcon("icon/icon.png");
		this.setIconImage(image.getImage());
		// Встановлення початкового розміру вікна
		this.setSize(515, 600);
		// Встановлення початкової позиції вікна
		this.setLocation(500, 50);
		// Встановлення заголовка вікна
		this.setTitle("Гра Судоку");
		// Заборона зміни розміру вікна
		this.setResizable(false);
		// Встановлення стандартної операції при закритті вікна
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
