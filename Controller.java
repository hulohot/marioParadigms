import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
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

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e) {	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			model.setDestination1(e.getX(), e.getY());
			model.setDestination2(e.getX(), e.getY());
			model.addCoinBlock();
		}
	}

	// Actions now based on pressed/release
	public void mousePressed(MouseEvent e)
	{
		model.setDestination1(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) 
	{    
		model.setDestination2(e.getX(), e.getY());
		model.addBrick();
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_S: keyS = true; break;
			case KeyEvent.VK_L: keyL = true; break; 
			case KeyEvent.VK_SPACE: keySpace = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_S: keyS = false; break;
			case KeyEvent.VK_L: keyL = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
		}
	}

	public void keyTyped(KeyEvent e) {   }

	void update()
	{
		model.mario.setPrevious();
		if(keyRight){
			Model.cameraPos += 10;
			model.mario.changeSpriteVal();
		}
		if(keyLeft){
			Model.cameraPos -= 10;
			model.mario.changeSpriteVal();
		} 
		if(keySpace){
			model.mario.jump();
		}
		if(keyS) model.marshal();
		if(keyL) model.unmarshal();
	}

}

