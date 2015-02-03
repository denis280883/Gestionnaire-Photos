package javagame;


import java.util.ArrayList;


import org.lwjgl.input.Mouse;

import org.newdawn.slick.*; // OK
import org.newdawn.slick.state.*; // OK

public class Menu extends BasicGameState{ // OK

	// constantes
	private static final String PICTURES_PATH = "./Pictures/";
	private static final int NB_DISPLAY_PICTURES = 3;
	
	private static final int MAX_WIDTH_PICTURE = 2048;
	private static final int MAX_HEIGHT_PICTURE = 1536;
	private static final int MAX_WIDTH_CENTER = 800;
	private static final int MAX_HEIGHT_CENTER = 600;
	//private static final int MAX_WIDTH_SIDE = 600;
	private static final int MAX_HEIGHT_SIDE = 450;
	private static final int MARGIN_SIDE = 100;
	
	private Image[] pictures = new Image[NB_DISPLAY_PICTURES];
	
	
	// Variables
	private int dim_x, dim_y;
	private int i_picture = -1;
	private String mouse="No input yet!";
	
	private ArrayList<String> pictures_path = new ArrayList<String>();
	
	
	public Menu(int state){ // OK
	}
	


	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		
    	// TODO Auto-generated method stub
		g.drawString(mouse, 50, 50);
		g.drawRect(50, 100, 60, 120);
		
		Image face = new Image(PICTURES_PATH+"img1.JPG");
		g.drawImage(face, 200, 130);


		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)	throws SlickException {
		// TODO Auto-generated method stub
		
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse ="Mouse position x: " + xpos + " y: "+ypos;
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
