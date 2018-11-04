import java.awt.Graphics;

public class Coin extends Sprite {

	double vertVel;
	double horiVel;

	//////////////////////////////////////
	/////////// Constructors /////////////
	//////////////////////////////////////

	Coin(Model m, int xx, int yy, int ww, int hh) {
		model = m;
		x = xx;
		y = yy;
		w = 75;
		h = 75;
		vertVel = -20;

		horiVel = Math.random() * 18;

		double multiplier = Math.random();
		if (multiplier >= .5)
			multiplier = 1.0;
		else
			multiplier = -1.0;

		horiVel = horiVel * multiplier;
	}

	Coin(Model m, Json ob) {
		model = m;
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
	}

	Coin(Coin c, Model m) {
		this.x = c.x;
		this.y = c.y;
		this.w = c.w;
		this.h = c.h;
		this.model = m;
	}

	//////////////////////////////////////
	/////////// Utility Methods //////////
	//////////////////////////////////////

	@Override
	public void update() {
		// TODO Auto-generated method stub
		vertVel += 3.14159;
		y += vertVel;
		x += horiVel;

		if (y > 400)
			model.removeCoin(this);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(View.coin, x - model.cameraPos, y, w, h, null);
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new Coin(this, m);
	}

	//////////////////////////////////////
	///////// Attribute Getters //////////
	//////////////////////////////////////

	@Override
	public Json marshal() {
		// TODO Auto-generated method stub
		Json ob = Json.newObject();

		ob.add("type", "coin");
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);

		return ob;
	}

	@Override
	public boolean isCoin() {
		return true;
	}
}
