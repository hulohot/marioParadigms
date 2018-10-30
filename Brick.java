import java.awt.Graphics;

class Brick extends Sprite 
{
	Model model;

    Brick(int xPos, int yPos, int wDim, int hDim, Model m)
    {
        x = xPos;
        y = yPos;
        w = wDim;
        h = hDim;
        model = m;
    }

    /**
     * A Brick constructor that accepts a JSON object
     * @param JSON object from the model.fileName file
     */
    Brick(Model m, Json ob)
    {
    	model = m;
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }
    
    Brick(Brick b, Model m) {
    	this.x = b.x;
    	this.y = b.y;
    	this.w = b.w;
    	this.h = b.h;
    	this.model = m;
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(View.brick, x - model.cameraPos, y, w, h, null);
	}
	
	@Override
	public Json marshal() {
		// TODO Auto-generated method stub
		Json ob = Json.newObject();
		
		ob.add("type", "brick");
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		
		return ob;
	}

	@Override
	public boolean isBrick() {
		return true;
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new Brick(this, m);
	}
}