package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Background.Background;
import Entity.HighScoreUtility;
import Font.LoadFont;
import Main.GamePanel;

public class ScoreState extends GameState {

	private Background bg;

	private String name = "";
	private int score;

	private LoadFont loadfont;

	public ScoreState(GameStateManager gsm) {
		this.gsm = gsm;
		bg = new Background("/bg/highScore.gif");
		HighScoreUtility highscore = new HighScoreUtility();
		name = highscore.getOldName();
		score = highscore.getOldScore();
		loadfont = new LoadFont("/Entity/a.ttf", 60);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		bg.draw(g);

		g.setColor(Color.BLACK);
		g.setFont(loadfont.getFont());
		int width = (int) g.getFontMetrics().getStringBounds(name, g).getWidth();
		g.drawString(name, GamePanel.WIDTH / 2 - width / 2, 360);
		width = (int) g.getFontMetrics().getStringBounds(String.valueOf(score), g).getWidth();
		g.setColor(Color.red);
		g.drawString(String.valueOf(score), GamePanel.WIDTH / 2 - width / 2, 430);
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		gsm.setState(GameStateManager.MENUSTATE);
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
