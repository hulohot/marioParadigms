import java.util.*;

class Model {
	Mario mario;
	ArrayList<Sprite> sprites;
	int dest_x1, dest_x2;
	int dest_y1, dest_y2;
	int xx, yy, ww, hh;
	static int cameraPos = 0;
	String fileName = "map.json";

	Model() {
		sprites = new ArrayList<Sprite>();
		mario = new Mario(this);
		sprites.add(mario);
		unmarshal();
	}
	
	Model(Model m){
		Iterator<Sprite> it = m.sprites.iterator();
		while(it.hasNext()) {
			Sprite sprite = it.next();
			Sprite s = sprite.cloneSprite();
			sprites.add(s);
			if(s.isMario())
				mario = (Mario)s;
		}
	}

	public void update() {
		for(int i = 0; i < sprites.size(); i++) {
			sprites.get(i).update();
		}
	}

	public void setDestination1(int x, int y) {
		this.dest_x1 = x;
		this.dest_y1 = y;
	}

	public void setDestination2(int x, int y) {
		this.dest_x2 = x;
		this.dest_y2 = y;

		determineBoundries();
	}
	
	public void determineBoundries() {
		xx = Math.min(dest_x1, dest_x2);
		yy = Math.min(dest_y1, dest_y2);
		ww = Math.abs(dest_x1 - dest_x2);
		hh = Math.abs(dest_y1 - dest_y2);
	}

	public void addBrick() {
		
		Brick b = new Brick(xx + cameraPos, yy, ww, hh);
		sprites.add(b);
	}
	
	public void addCoin() {
		Sprite c = new Coin(this, xx + cameraPos, yy, ww, hh);
		sprites.add(c);
	}
	
	public void addCoin(int x, int y, int w, int h) {
		Sprite c = new Coin(this, x + cameraPos, y, w, h);
		sprites.add(c);
	}
	
	public void removeCoin(Coin c) {
		sprites.remove(c);
	}
	
	public void addCoinBlock() {
		Sprite cB = new CoinBlock(this, xx + cameraPos, yy, ww, hh);
		sprites.add(cB);
	}

	// Unmarshal function that loads from JSON
	// TODO fix this method
	public void unmarshal() {
		sprites.clear();
		Json ob = Json.load(fileName);
		Json tmpList = ob.get("sprites");
	    for(int i = 0; i < tmpList.size(); i++) {
	    	Json j = tmpList.get(i);
	    	String str = j.getString("type");
	    	if(str.equals("mario")) {
	    		mario = new Mario(this, j);
	    		sprites.add(mario);
	    	}
	    	else if(str.equals("brick"))
	    		sprites.add(new Brick(this, j));
	    	else if(str.equals("coinBlock")) 
	    		sprites.add(new CoinBlock(this, j));
	    	else if(str.equals("coin"))
	    		sprites.add(new Coin(this, j));
	    }
	}

	// marshal function that save to JSON
	public void marshal() {
		Json ob = Json.newObject();
		Json tmpList = Json.newList();
		ob.add("sprites", tmpList);
		for (int i = 0; i < sprites.size(); i++)
			tmpList.add(sprites.get(i).marshal());
		ob.save(fileName);
	}
}
