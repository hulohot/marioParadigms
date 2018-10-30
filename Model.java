import java.util.*;

class Model {
	Mario mario;
	ArrayList<Sprite> sprites;
	int dest_x1, dest_x2;
	int dest_y1, dest_y2;
	int xx, yy, ww, hh;
	int jumpCount;
	int coinCount;
	int d = 25;
	int k = 6;
	int cameraPos;
	String fileName = "map.json";

	Model() {
		jumpCount = 0;
		coinCount = 0;
		cameraPos = 50; // Change this back
		sprites = new ArrayList<Sprite>();
		mario = new Mario(this);
		sprites.add(mario);
		unmarshal();
	}
	
	Model(Model m){
		this.jumpCount = m.jumpCount;
		this.coinCount = m.coinCount;
		this.cameraPos = m.cameraPos;
		sprites = new ArrayList<Sprite>();
		Iterator<Sprite> it = m.sprites.iterator();
		while(it.hasNext()) {
			Sprite sprite = it.next();
			Sprite s = sprite.cloneSprite(m);
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
		
		Brick b = new Brick(xx + cameraPos, yy, ww, hh, this);
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
	
	void doAction(Action action) {
		mario.setPrevious(); 
		if(action == Action.RunRight){
			cameraPos += 10;
		}
		if(action == Action.Jump){
			mario.jump();
			jumpCount++;
		}
		if(action == Action.RunAndJump){
			mario.jump();
			cameraPos += 10;
			jumpCount++;
		}
		if(action == Action.Wait) {
			
		}
	}
	
	double evaluateAction(Action action, int depth)
	{
		// Evaluate the state
		if(depth >= d)
			return (mario.x + cameraPos) + (5000 * coinCount) - (2 * jumpCount);

		// Simulate the action
		Model copy = new Model(this); // uses the copy constructor
		copy.doAction(action); // like what Controller.update did before
		copy.update(); // advance simulated time

		// Recurse
		if(depth % k != 0)
		   return copy.evaluateAction(action, depth + 1);
		else
		{
		   double best = copy.evaluateAction(Action.RunRight, depth + 1);
		   best = Math.max(best,
			   copy.evaluateAction(Action.Jump, depth + 1));
		   best = Math.max(best,
				   copy.evaluateAction(Action.Wait, depth + 1));
		   best = Math.max(best,
			   copy.evaluateAction(Action.RunAndJump, depth + 1));
		   return best;
		}
	}
}
