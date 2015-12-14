package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class BomberBird extends Enemy {

	private Random rand;
	private BufferedImage[] sprites;

	public BomberBird(double x, double y) {
		setPosition(x, y);
		rand = new Random();
		moveSpeed = 1;
		maxSpeed = rand.nextInt(5) + 1;

		width = 24;
		height = 25;

		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Entity/angrybird.gif"));

			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(150);

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
