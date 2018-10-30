import java.awt.Graphics;

class Mario extends Sprite {
	int previousX;
	int previousY;
	int previousCameraPos;
	int solidCount = 0;
	int marioSpriteVal;
	int xSpeed = 10;
	double verticalVelocity;
	Model model;

	Mario(Model m) {
		model = m;
		x = 100;
		y = 100;
		w = 60;
		h = 95;
	}

	Mario(Model m, Json ob) {
		model = m;
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
	}
	
	Mario(Mario m) {
    	this.x = m.x;
    	this.y = m.y;
    	this.w = m.w;
    	this.h = m.h;
    }

	public void update() {
		verticalVelocity += 3.14159;
		y += verticalVelocity;

		if (y > 295) {
			verticalVelocity = 0.0;
			y = 295; // snap back to the ground
		}

		if (verticalVelocity == 0.0)
			solidCount = 0;
		else {
			solidCount++;
		}

		generalCollision();

	}

	// Collides with sprites chosen
	void generalCollision() {
		for (int i = 0; i < model.sprites.size(); i++) {
			Sprite mario = model.sprites.get(0);
			Sprite other = model.sprites.get(i);
			if (other.isBrick() || other.isCoinBlock()) {
				if (checkBrickCollision(model, mario, other)) {
					setBarrier(other, other.x, other.y, other.w, other.h);
				}
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

	void hitBottomCoinBlock(Sprite b) {
		((CoinBlock) b).spawnCoin();
	}

	void setBarrier(Sprite b, int brickX, int brickY, int brickW, int brickH) {
		// Left of Brick Barrier
		if ((x + Model.cameraPos + w > brickX) && (previousX + Model.cameraPos < brickX) && (previousY + h > brickY)
				&& (previousY < brickY + brickH)) {
			Model.cameraPos -= xSpeed;
		}
		// Right of Brick Barrier
		if ((previousX + Model.cameraPos + w > brickX + brickW) && (previousX + Model.cameraPos < brickX + brickW)
				&& (previousY + h > brickY) && (previousY < brickY + brickH)) {
			Model.cameraPos += xSpeed;
		}
		// Top of Brick Barrier
		if ((y + h >= brickY) && (previousY + h <= brickY)) {
			verticalVelocity = 0.0;
			y = brickY - h - 1;
			solidCount = 0;
		}
		// Bottom of Brick Barrier
		if ((y <= brickY + brickH) && (previousY > brickY + brickH)) {
			y = brickY + brickH + 1;
			verticalVelocity = 0.0;
			if (b.isCoinBlock())
				hitBottomCoinBlock(b);
		}
	}

	void jump() {
		if (solidCount < 5)
			verticalVelocity -= 7.0;
	}

	void setPrevious() {
		previousX = x;
		previousY = y;
		previousCameraPos = Model.cameraPos;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int j = this.getSpriteVal();
		g.drawImage(View.mario_images[j], this.x, this.y, null);
	}

	@Override
	public Json marshal() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sprite cloneSprite() {
		// TODO Auto-generated method stub
		return new Mario(this);
	}
}