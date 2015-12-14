package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Bird extends Enemy {

	private Random rand;
	private BufferedImage[] sprites;

	public Bird(double x, double y) {
		setPosition(x, y);
		rand = new Random();
		moveSpeed = 1;
		maxSpeed = rand.nextInt(3) + 1;

		width = 36;
		height = 25;

		damage = 1;

		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Entity/bird.gif"));

			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(200);
	}

	private void getNextPosition() {
		dx += moveSpeed;
		if (dx > maxSpeed)
			dx = maxSpeed;
	}

	public void update() {

		// update position
		getNextPosition();
		x += dx;

		// checkOnscreen
		if (x < GamePanel.WIDTH && x > 0)
			OnScreen = true;

		// check out bound
		if (x > GamePanel.WIDTH)
			outOfScreen = true;

		// update animation
		animation.update();

	}

	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
