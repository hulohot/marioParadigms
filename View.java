import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel {
	Model model;
	static BufferedImage[] mario_images = null;
	static BufferedImage[] coinBlocks = null;
	static BufferedImage coin;
	static BufferedImage brick;
	static BufferedImage background;

	//////////////////////////////////////
	///////////// Constructors ///////////
	//////////////////////////////////////

	View(Controller c, Model m) throws IOException {
		if (mario_images == null) {
			model = m;
			c.setView(this);

			background = ImageIO.read(new File("background.png"));

			mario_images = new BufferedImage[5];
			mario_images[0] = ImageIO.read(new File("mario1.png"));
			mario_images[1] = ImageIO.read(new File("mario2.png"));
			mario_images[2] = ImageIO.read(new File("mario3.png"));
			mario_images[3] = ImageIO.read(new File("mario4.png"));
			mario_images[4] = ImageIO.read(new File("mario5.png"));

			coinBlocks = new BufferedImage[2];
			coinBlocks[0] = ImageIO.read(new File("coinBlockFull.png"));
			coinBlocks[1] = ImageIO.read(new File("coinBlockEmpty.png"));

			brick = ImageIO.read(new File("brick.png"));
			coin = ImageIO.read(new File("coin.png"));
		}
	}

	//////////////////////////////////////
	/////////// Utility Methods //////////
	//////////////////////////////////////

	public void paintComponent(Graphics g) {
		// Tiles the background image for 8000 px surrounding mario on both sides
		for (int i = -10; i < 10; i++) {
			g.drawImage(View.background, (800 * i) - (model.cameraPos / 2), 0, null);
		}

		// Draws all bricks
		for (int i = 0; i < model.sprites.size(); i++) {
			Sprite sprite = model.sprites.get(i);
			sprite.draw(g);
		}
	}
}
