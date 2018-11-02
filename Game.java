import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame {
	Model model;
	Controller controller;
	View view;
	static MySoundClip marioBackground;

	public Game() throws Exception {
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		marioBackground = new MySoundClip("overworld.wav", 2);
		this.setTitle("Mario!");
		this.setSize(1000, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public static void main(String[] args) throws Exception {
		Game g = new Game();
		marioBackground.play();
		g.run();
	}

	public void run() {
		while (true) {
			controller.updateNew();
//			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 miliseconds (25 fps)
			try {
				Thread.sleep(40);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
