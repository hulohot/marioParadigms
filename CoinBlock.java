import java.awt.Graphics;

public class CoinBlock extends Sprite {

	static MySoundClip coin;

	int coinBlockVal;
	int coinImgVal;
	int coinTimer;
	boolean coinHit;

	//////////////////////////////////////
	///////////// Constructors ///////////
	//////////////////////////////////////

	CoinBlock(Model m, int xx, int yy, int ww, int hh) {
		coinBlockVal = 0;
		coinImgVal = 0;
		coinTimer = 0;
		coinHit = false;
		model = m;
		x = xx;
		y = yy;
		w = 89;
		h = 83;

		try {
			coin = new MySoundClip("coin.wav", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	CoinBlock(Model m, Json ob) {
		model = m;
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
		coinBlockVal = (int) ob.getLong("coinBlockVal");

		try {
			coin = new MySoundClip("coin.wav", 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	CoinBlock(CoinBlock c, Model m) {
		this.x = c.x;
		this.y = c.y;
		this.w = c.w;
		this.h = c.h;
		this.coinBlockVal = c.coinBlockVal;
		this.coinTimer = c.coinTimer;
		this.coinHit = c.coinHit;
		this.model = m;
	}

	//////////////////////////////////////
	/////////// Utility Methods //////////
	//////////////////////////////////////

	public void generateCoin(Sprite m) {
		if (!coinHit && coinTimer < 1 && coinBlockVal < 5) {
			coinHit = !coinHit;
			coinBlockVal++;
			model.addCoin(this);
			((Mario) m).coinCount++;
			if (model.isCopy == false)
				coin.play();
		}
	}

	@Override
	public void update() {
		if (coinBlockVal < 5)
			coinImgVal = 0;
		else {
			coinImgVal = 1;
		}
		if(coinHit) {
			coinHit = !coinHit;
			coinTimer++;
		}
		else {
			coinTimer = 0;
		}
			
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new CoinBlock(this, m);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(View.coinBlocks[coinImgVal], x - model.cameraPos, y, w, h, null);
	}

	//////////////////////////////////////
	///////// Attribute Getters //////////
	//////////////////////////////////////

	@Override
	public Json marshal() {
		Json ob = Json.newObject();
		ob.add("type", "coinBlock");
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		ob.add("coinBlockVal", this.coinBlockVal);
		return ob;
	}

	@Override
	public boolean isCoinBlock() {
		return true;
	}

}
