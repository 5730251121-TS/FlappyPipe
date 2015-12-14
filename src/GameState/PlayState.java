package GameState;

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

public class PlayState extends GameState {

	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private HUD hud;

	// private AudioPlayer bgMusic;

	private long startTime;

	private int addCount;
	private int BomberBirdTime;
	private int minBomberBirdTime;
	private boolean hasDecrease;

	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		initialize();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		bg = new Background("/bg/bg760x490.png");

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

		// update populate
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

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_SPACE) {
			player.setJumping(false);
			player.setJumpedOnce(false);
		}
	}

}
