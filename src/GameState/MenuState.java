package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.AudioPlayer;
import Background.Background;

public class MenuState extends GameState {

	private Background bg;
	private BufferedImage head;

	private AudioPlayer optionSound;
	private AudioPlayer selectSound;

	private int currentChoice = 0;
	private String[] options = { "Start", "High Score", "HELP", "Quit" };

	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;

		bg = new Background("/bg/titleanimation.gif");
		optionSound = new AudioPlayer("/SFX/optionsound.wav");
		selectSound = new AudioPlayer("/SFX/coin.wav");

		try {
			// load floating head

			head = ImageIO.read(getClass().getResourceAsStream("/HUD/headOption.gif"));
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		// draw bg
		bg.draw(g);

		// draw menu options
		/*
		 * g.setFont(LoadFont.optionFont); for (int i = 0; i < options.length;
		 * i++) { g.setColor(Color.GREEN.darker()); g.drawString(options[i],
		 * 300, 260 + i * 80); }
		 */

		// draw floating head
		if (currentChoice == 0)
			g.drawImage(head, 225, 200, null);
		if (currentChoice == 1)
			g.drawImage(head, 175, 270, null);
		if (currentChoice == 2)
			g.drawImage(head, 250, 340, null);
		if (currentChoice == 3)
			g.drawImage(head, 250, 410, null);

	}

	private void select() {
		if (currentChoice == 0) {
			// start
			selectSound.play();
			gsm.setState(GameStateManager.PLAYSTATE);
		} else if (currentChoice == 1) {
			// high score
			selectSound.play();
			gsm.setState(GameStateManager.SCORESTATE);
		} else if (currentChoice == 2) {
			// help
			selectSound.play();
			gsm.setState(GameStateManager.HELPSTATE);
		} else if (currentChoice == 3) {
			// quit
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_ENTER) {
			select();
		}

		if (k == KeyEvent.VK_UP) {
			// AudioPlayer.menuOptionSound.play();
			optionSound.play();
			currentChoice--;
			if (currentChoice < 0) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			// AudioPlayer.menuOptionSound.play();
			optionSound.play();
			currentChoice++;
			if (currentChoice > options.length - 1) {
				currentChoice = 0;
			}
		}

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}
}
