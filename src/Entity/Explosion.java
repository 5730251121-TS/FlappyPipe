package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Explosion {

	private int x;
	private int y;

	private int width;
	private int height;

	private Animation animation;
	private BufferedImage[] sprites;

	private boolean remove;

	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;

		width = 36;
		height = 25;

		try {

			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Entity/birdexplosion.gif"));

			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(50);

	}

	public void update() {
		animation.update();
		if (animation.hasPlayedOnce()) {
			remove = true;
		}
	}

	public boolean shouldRemove() {
		return remove;
	}

	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x - width / 2, y - height / 2, null);
	}

}
