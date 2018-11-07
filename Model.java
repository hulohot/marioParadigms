import java.util.*;

class Model {
	Mario mario;
	ArrayList<Sprite> sprites;
	int dest_x1, dest_x2;
	int dest_y1, dest_y2;
	int xx, yy, ww, hh;
	int cameraPos;
	int previousCameraPos;
	double bestScore;
	String fileName = "map.json";
	boolean isCopy;

	//////////////////////////////////////
	/////////// Constructors /////////////
	//////////////////////////////////////

	Model() {
		isCopy = false;
		cameraPos = 0;
		sprites = new ArrayList<Sprite>();
		mario = new Mario(this);
		Menu menu = new Menu(this);
		sprites.add(mario);
		sprites.add(menu);
		unmarshal();
	}

	Model(Model m) {
		isCopy = true;
		this.cameraPos = m.cameraPos;
		this.previousCameraPos = m.previousCameraPos;

		this.sprites = new ArrayList<Sprite>();
		for (int i = 0; i < m.sprites.size(); i++) {
			Sprite sprite = m.sprites.get(i);
			Sprite s = sprite.cloneSprite(this);
			this.sprites.add(s);
			if (s.isMario())
				this.mario = (Mario) s;
		}
	}

	//////////////////////////////////////
	/////////// Utility Methods //////////
	//////////////////////////////////////

	public void update() {
		for (int i = 0; i < sprites.size(); i++) {
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

	//////////////////////////////////////
	///////// Sprite Modification ////////
	//////////////////////////////////////

	public void addBrick() {
		Brick b = new Brick(xx + cameraPos, yy, ww, hh, this);
		sprites.add(b);
	}

	public void addCoin() {
		Sprite c = new Coin(this, xx + cameraPos, yy, ww, hh);
		sprites.add(c);
	}

	public void addCoin(Sprite s) {
		Sprite c = new Coin(this, s.x, s.y - 50, s.w, s.h);
		sprites.add(c);
	}

	public void removeCoin(Coin c) {
		sprites.remove(c);
	}

	public void addCoinBlock() {
		Sprite cB = new CoinBlock(this, xx + cameraPos, yy, ww, hh);
		sprites.add(cB);
	}

	//////////////////////////////////////
	//////////// JSON Methods ////////////
	//////////////////////////////////////

	// Unmarshal function that loads from JSON
	// TODO fix this method
	public void unmarshal() {
		sprites.clear();
		Json ob = Json.load(fileName);
		Json tmpList = ob.get("sprites");
		for (int i = 0; i < tmpList.size(); i++) {
			Json j = tmpList.get(i);
			String str = j.getString("type");
			if (str.equals("mario")) {
				mario = new Mario(this, j);
				sprites.add(mario);
			} else if (str.equals("brick"))
				sprites.add(new Brick(this, j));
			else if (str.equals("coinBlock"))
				sprites.add(new CoinBlock(this, j));
			else if (str.equals("coin"))
				sprites.add(new Coin(this, j));
			else if (str.equals("menu"))
				sprites.add(new Menu(this, j));
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

	//////////////////////////////////////
	////////////// AI Methods ////////////
	//////////////////////////////////////

	void doAction(Action action) {
		if (action == Action.RunRight) {
			mario.runRight();
		} else if (action == Action.Jump) {
			mario.jump();
		} else if (action == Action.RunAndJump) {
			mario.jump();
			mario.runRight();
		}
	}

	double evaluateAction(Action action, int depth) {
		int d = 10;
		int k = 5;
		// Evaluate the state
		if (depth >= d)
			return (cameraPos) + (5000 * mario.coinCount) - (2 * mario.jumpCount);

		// Simulate the action
		Model copy = new Model(this); // uses the copy constructor
		copy.doAction(action); // like what Controller.update did before
		copy.update(); // advance simulated time

		// Recurse
		if (depth % k != 0)
			return copy.evaluateAction(action, depth + 1);
		else {
			double best = copy.evaluateAction(Action.RunRight, depth + 1);
			best = Math.max(best, copy.evaluateAction(Action.Jump, depth + 1));
			best = Math.max(best, copy.evaluateAction(Action.RunAndJump, depth + 1));
			bestScore = best;
			return best;
		}
	}
}
