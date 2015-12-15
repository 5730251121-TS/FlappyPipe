package GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class InfoState extends GameState {

	private BufferedImage[] sprites;

	private int currentFrame;

	private int width, height;

	public InfoState(GameStateManager gsm) {
		// TODO Auto-generated constructor stub
		this.gsm = gsm;
		currentFrame = 0;
		width = GamePanel.WIDTH;
		height = GamePanel.HEIGHT;
		initialize();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/bg/how-to-play.gif"));
			sprites = new BufferedImage[2];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(sprites[currentFrame], 0, 0, null);
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if (currentFrame == sprites.length - 1)
			gsm.setState(GameStateManager.MENUSTATE);
		currentFrame++;
		if (currentFrame > sprites.length - 1)
			currentFrame = sprites.length - 1;
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

}
