import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.xml.ws.Service.Mode;

public class Menu extends Sprite {

	int borderSize;
	int marginSize;
	int lineSpaceSize;
	String backgroundColor;
	String borderColor;
	
	//////////////////////////////////////
	///////////// Constructors ///////////
	//////////////////////////////////////

	Menu(Model m) {
		model = m;
		x = 700;
		y = 10;
		w = 250;
		h = 175;
		borderSize = 5;
		marginSize = 30;
		lineSpaceSize = 20;
		backgroundColor = "220,227,239";
		borderColor = "62,65,71";
	}

	Menu(Model m, Json ob) {
		model = m;
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		w = (int) ob.getLong("w");
		h = (int) ob.getLong("h");
		marginSize = (int) ob.getLong("marginSize");
		borderSize = (int) ob.getLong("borderSize");
		lineSpaceSize = (int) ob.getLong("lineSpaceSize");
		backgroundColor = ob.getString("backgroundColor");
		borderColor = ob.getString("borderColor");
	}
	
	Menu(Menu that, Model m){
		this.model = m;
		this.x = that.x;
		this.y = that.y;
		this.w = that.w;
		this.h = that.h;
		this.borderSize = that.borderSize;
		this.marginSize = that.marginSize;
		this.lineSpaceSize = that.lineSpaceSize;
		this.backgroundColor = that.backgroundColor;
		this.borderColor = that.borderColor;
	}
	

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g) {
		int rValue, gValue, bValue;
		
		rValue = Integer.parseInt(backgroundColor.split(",")[0]);
		gValue = Integer.parseInt(backgroundColor.split(",")[1]);
		bValue = Integer.parseInt(backgroundColor.split(",")[2]);
		
		// Drawing Background
		g.setColor(new Color(rValue, gValue, bValue));
		g.fillRect(x, y, w, h);
		
		rValue = Integer.parseInt(borderColor.split(",")[0]);
		gValue = Integer.parseInt(borderColor.split(",")[1]);
		bValue = Integer.parseInt(borderColor.split(",")[2]);
		
		// Drawing Border
		g.setColor(new Color(rValue, gValue, bValue));
		g.fillRect(x, y, w , borderSize);                    // Top Line
		g.fillRect(x , y + h - borderSize,  w , borderSize); // Bottom Line
		g.fillRect(x, y, borderSize, h);                     // Left Line
		g.fillRect(x + w - borderSize, y, borderSize, h);    // Right Line
		
		// Displaying Strings
		g.setFont(new Font("TimesRoman", Font.BOLD, 12));
		g.drawString("Mario Position : " + model.cameraPos, x + marginSize, y + marginSize);
		g.drawString("Jump Count : " + model.mario.jumpCount, x + marginSize, y + marginSize + lineSpaceSize);
		g.drawString("Coin Count : " + model.mario.coinCount, x + marginSize, y + marginSize + (2 * lineSpaceSize));
		g.drawString("Goal Value : " + model.bestScore, x + marginSize, y + marginSize + (3 * lineSpaceSize));
		g.drawString("Distance to end: " + (2410 - model.cameraPos), x + marginSize, y + marginSize + (4 * lineSpaceSize));
 	}

	@Override
	public Json marshal() {
		Json ob = Json.newObject();

		ob.add("type", "menu");
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		ob.add("backgroundColor", this.backgroundColor);
		ob.add("borderColor", this.borderColor);
		ob.add("borderSize", this.borderSize);
		ob.add("marginSize", this.marginSize);
		ob.add("lineSpaceSize", this.lineSpaceSize);

		return ob;
	}

	@Override
	public Sprite cloneSprite(Model m) {
		return new Menu(this, m);
	}

}
