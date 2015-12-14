package Font;

import java.awt.Font;
import java.io.BufferedInputStream;

public class LoadFont {

	private Font optionFont;

	public LoadFont(String s, int size) {

		try {

			BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(s));

			optionFont = Font.createFont(Font.TRUETYPE_FONT, myStream).deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			optionFont = new Font("Arial", Font.PLAIN, size);
		}

	}

	public Font getFont() {
		return optionFont;
	}
}
