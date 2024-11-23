package logic.sudoku.gameUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class SudokuCell extends JButton {
	public SudokuCell(){
		this.setSize(50,50);
		Font font = new Font("",2,24);
		this.setFont(font);
		this.setBackground(new Color(255,153,102));
		this.setForeground(Color.BLUE);
	}
}
