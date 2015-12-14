package Entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class HighScoreUtility {

	private String name;
	private int score;

	private String oldName;
	private int oldScore;

	private boolean NewHighScore;

	private static String readFileName = "highscore";

	public HighScoreUtility() {
		loadOldScore();
	}

	public HighScoreUtility(Player player) {
		score = player.getScore();
		loadOldScore();

	}

	public void checkNewHighScore() {
		if (score > oldScore) {
			NewHighScore = true;
			name = JOptionPane.showInputDialog(null, "Enter Your Name : ", "congratulations!! New high score.", JOptionPane.PLAIN_MESSAGE);
			saveScore();
		}
	}

	private void saveScore() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore"));
			String str = name + "\n" + String.valueOf(score);
			out.write(str);
			out.close();
		} catch (IOException e1) {
		}
	}

	private void loadOldScore() {
		File f = new File(readFileName);
		if (!f.exists()) {
			createDefaultScoreFile();
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String str = "";
			int c;
			while ((c = in.read()) != -1) {
				str += (char) c;
			}
			in.close();
			String[] readfile = str.split("\n");
			oldName = readfile[0];
			oldScore = Integer.parseInt(readfile[1]);
		} catch (Exception e) {
			// highScoreRecord = null;
		}
	}

	private void createDefaultScoreFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore"));
			String str = "Thanet\n0";
			out.write(str);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getOldName() {
		return oldName;
	}

	public int getOldScore() {
		return oldScore;
	}

	public boolean isNewHighScore() {
		return NewHighScore;
	}

}
