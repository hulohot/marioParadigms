import java.awt.Graphics;

class Mario extends Sprite {
	int previousX;
	int previousY;
	int solidCount;
	int marioSpriteVal;
	int jumpCount;
	int coinCount;
	int xSpeed = 10;
	double verticalVelocity;
	Model model;

	Mario(Model m) {
		model = m;
		x = 100;
		y = 100;
		w = 60;
		h = 95;
		solidCount = 0;
		jumpCount = 0;
		coinCount = 0;
		verticalVelocity = 0;
	}

	Mario(Model m, Json ob) {
		model = m;
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
		solidCount = 0;
	}
	
	Mario(Mario m, Model newModel) {
		this.model = newModel;
    	this.x = m.x;
    	this.y = m.y;
    	this.w = m.w;
    	this.h = m.h;
    	this.previousY = m.previousX;
    	this.previousY = m.previousY;
    	this.coinCount = m.coinCount;
    	this.jumpCount = m.jumpCount;
    	this.verticalVelocity = m.verticalVelocity;
    	this.marioSpriteVal = m.marioSpriteVal;
    	this.solidCount = m.solidCount;
    }

	public void update() {
		verticalVelocity += 5;
		y += verticalVelocity;
		solidCount++;

		if (y > 295) {
			verticalVelocity = 0.0;
			y = 295; // snap back to the ground
			solidCount = 0;
		}
		
		generalCollision();
	}

	// Collides with sprites chosen
	void generalCollision() {
		for (int i = 0; i < model.sprites.size(); i++) {
			Sprite s = model.sprites.get(i);
			if ((s.isBrick() || s.isCoinBlock()) && checkCollision(model, this, s)) {
				setBarrier(s);
			}
		}
	}

	public void changeSpriteVal() {
		marioSpriteVal++;
		if (marioSpriteVal == 5)
			marioSpriteVal = 0;
	}

	public int getSpriteVal() {
		return marioSpriteVal;
	}

	void setBarrier(Sprite b) {
		// Left of Brick Barrier
		if ((x + model.cameraPos + w >= b.x) && (model.previousCameraPos + previousX < b.x))
//				&& (previousY + h > b.y) && (previousY < b.y + b.h)) 
		{
			model.cameraPos -= xSpeed;
		}
		// Right of Brick Barrier
		if ((x + model.previousCameraPos > b.x + b.w) && (model.cameraPos + x <= b.x + b.w))
//				&& (previousY + h > b.y) && (previousY < b.y + b.h))
		{
			model.cameraPos += xSpeed;
		}
		// Top of Brick Barrier
		if ((y + h >= b.y) && (previousY + h <= b.y)) {
			verticalVelocity = 0.0;
			y = b.y - h - 1;
			solidCount = 0;
		}
		// Bottom of Brick Barrier
		if ((y <= b.y + b.h) && (previousY > b.y + b.h)) {
			y = b.y + b.h + 1;
			verticalVelocity = 0.0;
			System.out.println("SolidCount is: " + solidCount);
			if (b.isCoinBlock() && ((CoinBlock) b).coinBlockVal < 5 && solidCount < 4) {
				((CoinBlock) b).coinBlockVal++;
				coinCount++;
				model.addCoin(b);
			}
		}
	}
	
	void runRight() {
		model.cameraPos += xSpeed;
	}
	
	void runLeft() {
		model.cameraPos -= xSpeed;
	}

	void jump() {
		if (solidCount < 4) {
			verticalVelocity -= 15.0;
			jumpCount++;
		}
//		System.out.println("Mario's Jump Count: " + jumpCount);
	}

	void setPrevious() {
		this.previousX = x;
		this.previousY = y;
		model.previousCameraPos = model.cameraPos;
	}

	@Override
	public void draw(Graphics g) {
		int j = this.getSpriteVal();
		g.drawImage(View.mario_images[j], this.x, this.y, null);
	}

	@Override
	public Json marshal() {
		Json ob = Json.newObject();

		ob.add("type", "mario");
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);

		return ob;
	}

	@Override
	public boolean isMario() {
		return true;
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new Mario(this, m);
	}
}