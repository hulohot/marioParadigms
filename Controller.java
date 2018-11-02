import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener {
	View view;
	Model model;

	// Keys used as inputs
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keyS; // Save JSON value
	boolean keyL; // Load JSON value
	boolean keyC;
	boolean keySpace;

	Controller(Model m) {
		model = m;
	}

	void setView(View v) {
		view = v;
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			model.setDestination1(e.getX(), e.getY());
			model.setDestination2(e.getX(), e.getY());
			model.addCoinBlock();
		}
	}

	// Actions now based on pressed/release
	public void mousePressed(MouseEvent e) {
		model.setDestination1(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		model.setDestination2(e.getX(), e.getY());
		model.addBrick();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_S:
			keyS = true;
			break;
		case KeyEvent.VK_L:
			keyL = true;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_S:
			keyS = false;
			break;
		case KeyEvent.VK_L:
			keyL = false;
			break;
		case KeyEvent.VK_SPACE:
			keySpace = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

//	void doAction(Action action) {
//		model.mario.setPrevious();
//		if (action == Action.RunRight) {
//			model.mario.runRight();
//			model.mario.changeSpriteVal();
//		}
//		if (action == Action.RunAndJump) {
//			model.mario.runRight();
//			model.mario.changeSpriteVal();
//			model.mario.jump();
//		}
//		if (action == Action.Jump) {
//			model.mario.jump();
//		}
//	}

	void updateNew() {
		// Evaluate each possible action
		model.mario.setPrevious();
		double score_run = model.evaluateAction(Action.RunRight, 0);
		double score_jump = model.evaluateAction(Action.Jump, 0);
		double score_jump_and_run = model.evaluateAction(Action.RunAndJump, 0);
		System.out.println("Coin Count: " + model.mario.coinCount);
		System.out.println("Jump Count: " + model.mario.jumpCount);
		System.out.println("Run: " + score_run);
		System.out.println("Jump: " + score_jump);
		System.out.println("Jump and Run: " + score_jump_and_run);

		if (score_jump_and_run > score_jump && score_jump_and_run > score_run)
			model.doAction(Action.RunAndJump);
		else if (score_jump > score_run)
			model.doAction(Action.Jump);
		else
			model.doAction(Action.RunRight);
	}

	void update() {
		model.mario.setPrevious();
		if (keyRight) {
			model.mario.runRight();
			model.mario.changeSpriteVal();
		}
		if (keyLeft) {
			model.mario.runLeft();
			model.mario.changeSpriteVal();
		}
		if (keySpace) {
			model.mario.jump();
		}
		if (keyS)
			model.marshal();
		if (keyL)
			model.unmarshal();
	}

}
