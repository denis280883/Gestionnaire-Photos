package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

	public static final String gamename = "Ham Blaster! 2.0"; //OK
	public static final int menu = 0; //OK
	public static final int play = 1; //OK
	
	public Game(String gamename){
		super(gamename); // OK
		this.addState(new Menu(menu)); // OK
		this.addState(new Play(play)); // OK
	}
	


	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		this.getState(menu).init(gc, this); // OK
		this.getState(play).init(gc, this); // OK
		this.enterState(menu); // OK
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppGameContainer appgc; // OK
		try{
			appgc = new AppGameContainer(new Game(gamename)); // OK
			appgc.setDisplayMode(640, 360, false); // OK
			appgc.start(); //OK
		}catch(SlickException e){ // OK
			e.printStackTrace(); //OK
		}
		

		
	}
}


/*
 * 			System.out.println("");
			appgc = new AppGameContainer(sp);
			
			appgc.setDisplayMode(1280, 800, false);
			appgc.setForceExit(false);
			appgc.start();
 * */
