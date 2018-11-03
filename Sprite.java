import java.awt.Graphics;

public abstract class Sprite {
	int x, y, w, h;
	Model model;
	
	//////////////////////////////////////
	/////////// Abstract Methods /////////
	//////////////////////////////////////
	
	public abstract void update();
	public abstract void draw(Graphics g);
    public abstract Json marshal();
    
	//////////////////////////////////////
	/////// Identification Methods ///////
	//////////////////////////////////////
    
    public boolean isBrick() {
    	return false;
    }
    public boolean isMario() {
    	return false;
    }
    public boolean isCoin() {
    	return false;
    }
    public boolean isCoinBlock() {
    	return false;
    }
    
	//////////////////////////////////////
	////////// Utility Methods ///////////
	//////////////////////////////////////
    
    public abstract Sprite cloneSprite(Model m);
    
    public boolean checkCollision(Model m, Sprite a, Sprite b) {
        // Check left of brick
        if (a.x + m.cameraPos + a.w < b.x)
            return false;
        // Check right of brick
        else if (a.x + m.cameraPos > b.x + b.w)
            return false;
        // Check top of the brick
        else if (a.y + a.h < b.y)
            return false;
        // Check bottom of brick
        else if (a.y > b.y + b.h)
            return false;
        else
        	return true;
    }
}
