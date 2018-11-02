import java.awt.Graphics;

public class CoinBlock extends Sprite{
	
	Model model;
	static MySoundClip coin;
	
	// Coin block is full (0) until it runs out of coins (1)
	int coinBlockVal;
	int coinImgVal;	
	
	// Coin Blocks have a static width and height
	CoinBlock(Model m, int xx, int yy, int ww, int hh){
		coinBlockVal = 0;
		coinImgVal = 0;
		model = m;
		x = xx;
		y = yy;
		w = 89;
		h = 83;
		
		try {
			coin = new MySoundClip("coin.wav", 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	CoinBlock(Model m, Json ob){
		model = m;
		x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        coinBlockVal = (int)ob.getLong("coinBlockVal");
        
        try {
			coin = new MySoundClip("coin.wav", 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	CoinBlock(CoinBlock c, Model m) {
    	this.x = c.x;
    	this.y = c.y;
    	this.w = c.w;
    	this.h = c.h;
    	this.coinBlockVal = c.coinBlockVal;
//    	this.coinImgVal = c.coinImgVal;
    	this.model = m;
    }
	
	@Override
	public void update() {
		if(coinBlockVal < 5)
			coinImgVal = 0;
		else {
			coinImgVal = 1;			
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(View.coinBlocks[coinImgVal], x - model.cameraPos, y, w, h, null);
	}

	@Override
	public Json marshal() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new CoinBlock(this, m);
	}

}
