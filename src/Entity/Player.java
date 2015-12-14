package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.AudioPlayer;

public class Player extends MapObject {

	// player stuff
	private int health, maxHealth;
	private boolean dead, flinching;
	private boolean BombAll;
	private int BombAllcharge, BombAllmax;
	private long flinchTimer;
	private int space;
	private boolean hasJumpedOnce;
	private boolean bottom, top;
	private int score;
	private int time;

	// rectangle pipe
	private Rectangle toppipe;
	private Rectangle bottompipe;

	// images
	private BufferedImage[] sprite;

	// sound
	private AudioPlayer jumpSound;
	private AudioPlayer hitSound;
	private AudioPlayer punchSound;
	private AudioPlayer bombSound;

	public Player() {
		x = 620;
		y = 200;
		space = 200;
		width = 80;
		height = 490;
		falling = true;

		BombAllcharge = BombAllmax = 1000;

		fallSpeed = 1;
		maxFallSpeed = 5;

		jumpStart = -10;

		health = maxHealth = 3;

		jumpSound = new AudioPlayer("/SFX/Mario Jump.wav");
		jumpSound.volume(-30.0f);
		hitSound = new AudioPlayer("/SFX/hitBound.wav");
		hitSound.volume(-10.0f);
		punchSound = new AudioPlayer("/SFX/punch.wav");
		punchSound.volume(-10.0f);
		bombSound = new AudioPlayer("/SFX/bomb.wav");
		bombSound.volume((float) (Math.log(1) / Math.log(10.0) * 20.0));
		// load player image
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Entity/pipe.gif"));
			sprite = new BufferedImage[2];
			for (int i = 0; i < sprite.length; i++) {
				sprite[i] = spritesheet.getSubimage(i * 80, 0, width, height);
			}
		} catch (Exception e) {
		}
	}

	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);

		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		if (h > maxHealth)
			h = maxHealth;
		health = h;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setBombAll() {
		BombAll = true;
	}

	public void setJumpedOnce(boolean b) {
		hasJumpedOnce = b;
	}

	public boolean isJumpedOnce() {
		return hasJumpedOnce;
	}

	public void getNextPosition() {
		// dropping

		if (true) {
			dy += fallSpeed;

		}
	}

	public void checkCollision(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);

			// check normal bird
			if (e instanceof Bird) {
				Rectangle ee = new Rectangle((int) e.getx(), (int) e.gety(), e.width, e.height);
				toppipe = new Rectangle(640, (int) (y - height), width, height);
				bottompipe = new Rectangle(640, (int) y + space, width, height);
				if (toppipe.intersects(ee) || bottompipe.intersects(ee)) {
					// punchSound.play();
					e.hit();
					score++;
				}
				if (e.isOutOfScreen()) {
					// punchSound.play();
					e.setDead(true);
					if (flinching)
						return;
					health--;
					if (health < 0)
						health = 0;
					if (health == 0)
						setDead(true);
					flinching = true;
					flinchTimer = System.nanoTime();
				}
			}

			// check bomberbird
			if (e instanceof BomberBird) {
				Rectangle ee = new Rectangle((int) e.getx(), (int) e.gety(), e.width, e.height);
				toppipe = new Rectangle(640, (int) (y - height), width, height);
				bottompipe = new Rectangle(640, (int) y + space, width, height);
				if (toppipe.intersects(ee) || bottompipe.intersects(ee)) {
					// punchSound.play();
					e.setDead(true);
					if (flinching)
						return;
					health--;
					if (health < 0)
						health = 0;
					if (health == 0)
						setDead(true);
					flinching = true;
					flinchTimer = System.nanoTime();

				}
				if (e.isOutOfScreen()) {
					// punchSound.play();
					e.hit();
					score++;

				}
			}
		}
	}

	public void BombAll(ArrayList<Enemy> enemies) {
		if (BombAll) {
			BombAll = false;
			if (BombAllcharge >= BombAllmax) {
				bombSound.play();
				BombAllcharge -= BombAllmax;
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).setDead(true);
					if (enemies.get(i).isOnScreen()) {
						score++;
					}
				}
			}
		}

	}

	public void update() {

		// bomb update
		BombAllcharge++;
		if (BombAllcharge > BombAllmax)
			BombAllcharge = BombAllmax;

		time++;

		getNextPosition();

		// check flinch
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 2000) {
				flinching = false;
			}
		}

		// check jumping
		if (jumping) {
			jumpSound.play();
			dy = jumpStart;
			jumping = false;
		}

		// falling
		if (falling) {
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}
		y += dy;
		// check bound
		if ((int) y > 450) {
			bottom = true;
		} else {
			bottom = false;
		}
		if ((int) y + space < 0) {
			top = true;
		} else {
			top = false;
		}
		if (top) {
			hitSound.play();
			y = 0 - space;
		}
		if (bottom) {
			y = 450;
		}

	}

	public void draw(Graphics2D g) {
		// draw pipe
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		g.drawImage(sprite[0], (int) x, (int) (y - height), null);
		g.drawImage(sprite[1], (int) x, (int) (y + space), null);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getBombAllcharge() {
		return BombAllcharge;
	}

	public int getBombAllmax() {
		return BombAllmax;
	}

}
