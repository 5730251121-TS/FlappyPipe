package Main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame f = new JFrame("Flappy Pipe");
		f.add(new GamePanel());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);

	}
}
