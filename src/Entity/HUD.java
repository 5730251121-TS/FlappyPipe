package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Font.LoadFont;
import Main.GamePanel;

public class HUD {

	private Player player;
	private BufferedImage[] images;
	private BufferedImage bombbar;

	private LoadFont loadfont;
	private LoadFont loadfont2;

	public HUD(Player player) {
		this.player = player;
		images = new BufferedImage[4];

		loadfont = new LoadFont("/Entity/a.ttf", 60);
		loadfont2 = new LoadFont("/Entity/a.ttf", 20);

		try {
			BufferedImage imagesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/health.gif"));
			bombbar = ImageIO.read(getClass().getResourceAsStream("/HUD/bombbar.gif"));
			for (int i = 0; i < images.length; i++) {
				images[i] = imagesheet.getSubimage(i * 85, 0, 85, 30);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {

		g.setColor(Color.WHITE);
		g.drawImage(images[player.getHealth()], 15, 15, null);

		g.setFont(loadfont.getFont());
		g.drawString(String.valueOf(player.getScore()), GamePanel.WIDTH / 2 - 30, 65);
		g.setFont(loadfont2.getFont());
		g.drawString(player.getTimeToString(), 705, 30);

		g.drawImage(bombbar, 15, 50, null);
		g.setColor(Color.RED);
		g.fillRect(34, 59, (int) ((player.getBombAllcharge() * 1.0 / player.getBombAllmax()) * 62), 7);
	}

	public void update() {
	}

}
