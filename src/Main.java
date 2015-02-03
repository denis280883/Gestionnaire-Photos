import javax.swing.JButton;
import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		final int DIM_X = 1024;
		final int DIM_Y = 768;
		
		// TODO Auto-generated method stub
		SlickPictures sp = new SlickPictures("Test", DIM_X, DIM_Y);
		
		AppGameContainer appgc;
		
		try {
			System.out.println("");
			appgc = new AppGameContainer(sp);
			appgc.setTargetFrameRate(25);
			appgc.setDisplayMode(1280, 800, false);
			appgc.setForceExit(false);
			appgc.start();
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
