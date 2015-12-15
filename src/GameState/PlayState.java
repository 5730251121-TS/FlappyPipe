package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

//import Audio.AudioPlayer;
import Background.Background;
import Entity.Bird;
import Entity.BomberBird;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.HighScoreUtility;
import Entity.Player;
import Font.LoadFont;
import Main.GamePanel;

public class PlayState extends GameState {

	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private static final AlphaComposite transcluentWhite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
	protected static final AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);

	private LoadFont font;

	private HUD hud;

	private long Countpause;
	private long elapsed;

	// private AudioPlayer bgMusic;

	private long startTime;

	private int addCount;
	private int BomberBirdTime;
	private int minBomberBirdTime;
	private boolean hasDecrease;

	private boolean pause, alreadyPause;

	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		initialize();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Countpause = System.nanoTime();
		bg = new Background("/bg/bg760x490.png");

		font = new LoadFont("/Entity/a.ttf", 70);

		player = new Player();
		player.setTime(0);

		hud = new HUD(player);

		enemies = new ArrayList<Enemy>();
		addCount = 0;
		BomberBirdTime = 8;
		minBomberBirdTime = 2;

		explosions = new ArrayList<Explosion>();
		// bgMusic = new AudioPlayer("/SFX/coin.wav");
		// bgMusic.play();

		startTime = System.nanoTime();
	}

	public void populateEnemies() {

		Random rand = new Random();
		int yBombBird;
		if (addCount % BomberBirdTime == 0) {
			yBombBird = 100 + rand.nextInt(325);
		} else {
			yBombBird = 0;
		}
		int yBird = 100 + rand.nextInt(325);
		while (yBird < yBombBird + 30 && yBird > yBombBird - 30) {
			yBird = 100 + rand.nextInt(325);
		}
		Bird b = new Bird(-36 - rand.nextInt(300), yBird);

		BomberBird d = new BomberBird(-36 - rand.nextInt(300), 100 + rand.nextInt(325));
		enemies.add(b);
		addCount++;

		if (addCount % BomberBirdTime == 0)
			enemies.add(d);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

		if (pause) {
			Countpause = System.nanoTime();
		}

		if (!pause) {
			// update populate
			elapsed = (System.nanoTime() - Countpause) / 1000000;
			if ((int) (elapsed / 1000) > 2) {
				if ((int) ((player.getTime() % 3600) / 60) % 10 == 0 && ((player.getTime() % 3600) / 60) > 0) {
					if (!hasDecrease) {
						BomberBirdTime -= 2;
						hasDecrease = true;
					}
					if (BomberBirdTime < minBomberBirdTime)
						BomberBirdTime = minBomberBirdTime;
				}
				if ((int) ((player.getTime() % 3600) / 60) % 10 != 0 && ((player.getTime() % 3600) / 60) > 0) {
					hasDecrease = false;
				}

				// update player
				player.update();
				long elasped = (System.nanoTime() - startTime) / 1000000;
				if ((elasped / 100) % 40 == 0) {
					populateEnemies();
				}
				// bird collision
				player.checkCollision(enemies);

				// update all enemies
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).update();
					Enemy e = enemies.get(i);
					if (e.isDead()) {
						enemies.remove(i);
						i--;
						explosions.add(new Explosion(e.getx(), e.gety()));
					}
				}
				// update explosion
				for (int i = 0; i < explosions.size(); i++) {
					explosions.get(i).update();
					if (explosions.get(i).shouldRemove()) {
						explosions.remove(i);
						i--;
					}
				}

				// check gameover
				if (player.isDead()) {
					HighScoreUtility highscore = new HighScoreUtility(player);
					highscore.checkNewHighScore();
					if (!highscore.isNewHighScore()) {
						JOptionPane.showMessageDialog(null, "You can't reach new high score", "GAME OVER!!", JOptionPane.INFORMATION_MESSAGE);
					}
					gsm.setState(GameStateManager.MENUSTATE);
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

		// draw bg
		bg.draw(g);

		// draw player
		player.draw(g);

		// draw enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// draw hud
		hud.draw(g);

		// draw pause
		if (pause) {
			g.setComposite(transcluentWhite);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setComposite(opaque);
			g.setFont(font.getFont());
			g.setColor(Color.WHITE);
			int width = (int) g.getFontMetrics().getStringBounds("PAUSE", g).getWidth();
			g.drawString("PAUSE", GamePanel.WIDTH / 2 - width / 2, 240);
		}

		// draw count start
		if (elapsed / 1000 < 3) {
			g.setComposite(transcluentWhite);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setComposite(opaque);

			g.setFont(font.getFont());
			g.setColor(Color.WHITE);
			if ((int) (elapsed / 1000) <= 0) {
				int width = (int) g.getFontMetrics().getStringBounds("3", g).getWidth();
				g.drawString("3", GamePanel.WIDTH / 2 - width / 2, 240);
			}
			if ((int) (elapsed / 1000) <= 1 && (int) (elapsed / 1000) > 0) {
				int width = (int) g.getFontMetrics().getStringBounds("2", g).getWidth();
				g.drawString("2", GamePanel.WIDTH / 2 - width / 2, 240);
			}
			if ((int) (elapsed / 1000) <= 2 && (int) (elapsed / 1000) > 1) {
				int width = (int) g.getFontMetrics().getStringBounds("1", g).getWidth();
				g.drawString("1", GamePanel.WIDTH / 2 - width / 2, 240);
			}
		}

	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_SPACE) {
			if (!player.isJumpedOnce()) {
				player.setJumping(true);
				player.setJumpedOnce(true);
			}
		}
		if (k == KeyEvent.VK_R) {
			player.setBombAll();
			player.BombAll(enemies);
		}

		if (k == KeyEvent.VK_ENTER) {
			if (!alreadyPause) {
				pause = !pause;
				alreadyPause = true;
			}

		}

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_SPACE) {
			player.setJumping(false);
			player.setJumpedOnce(false);
		}
		if (k == KeyEvent.VK_ENTER) {

			alreadyPause = false;

		}
	}

}
