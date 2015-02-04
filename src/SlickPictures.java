import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;





import org.newdawn.slick.*;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
//import org.newdawn.slick.command.MouseButtonControl;
import org.lwjgl.input.Mouse;




public class SlickPictures extends BasicGame implements InputProviderListener  {
	
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

	// Variables
	private int dim_x, dim_y;
	private int i_picture = -1;
	
	private ArrayList<String> pictures_path = new ArrayList<String>();
	
	private Image[] pictures = new Image[NB_DISPLAY_PICTURES];
	private Font polices;
	
	/** The input provider abstracting input */
	private InputProvider provider;
	
	/** The command for attack */
	private Command attack = new BasicCommand("attack");
	/** The command for jump */
	private Command jump = new BasicCommand("jump");
	
	
	
	
	private Command run = new BasicCommand("run");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	

	
	/** The message to be displayed */
	private String message = "";
	
	// list files
	public void findFiles(String directoryPath) throws SlickException {
		File directory = new File(directoryPath);
		
		if (directory.exists()) {
			File[] subfiles = directory.listFiles();
			
			for (int i=0 ; i<subfiles.length; i++) {
				String name = PICTURES_PATH + subfiles[i].getName();
				BufferedImage temp;
				try {
					temp = ImageIO.read(new File(name));
					if ((temp.getWidth() <= MAX_WIDTH_PICTURE)
							&& (temp.getHeight() <= MAX_HEIGHT_PICTURE))
						pictures_path.add(name);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public SlickPictures(String title, int dim_x, int dim_y) {
		super(title);
		
		this.dim_x = dim_x;
		this.dim_y = dim_y;
	}
	
	// Load NB_DISPLAY_PICTURES pictures on the screen
	private void LoadPicture() throws SlickException{
		
		// pictures left:
		if (i_picture<0)
			pictures[0] = null; // not picture on the left
		else
			pictures[0] = new Image(pictures_path.get(i_picture));
		
		if (pictures_path.size() != 0) {
			pictures[1] = new Image(pictures_path.get(i_picture+1));
			
			// picture right
			if (pictures_path.size() > 1 && i_picture+2<pictures_path.size())
				pictures[2] = new Image(pictures_path.get(i_picture+2));
			else
				pictures[2] = null;
		}
		else {
			pictures[1] = null;
			pictures[2] = null;
		}
		
	}
	
	private void MovePictures(Command command)
	{
		if (command==right && i_picture+NB_DISPLAY_PICTURES<=pictures_path.size()) {
			i_picture++;
		}
		else if (command==left && i_picture>=0) {		
			i_picture--;
		}
			
		
		//Load others pictures
		try {
			LoadPicture();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	// Chargement des ressources graphiques + initialisation de l'état de l'appli
	public void init(GameContainer arg0) throws SlickException {	
		
		// initialisation des polices
		polices = new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16), false); 
		
		// lecture du dossier
		findFiles(PICTURES_PATH);
		
		//pictures[0] = null;
		LoadPicture();
		

		
        //set up a nice blue background
       // Color background = new Color(71,102,124);
        //Color background = new Color(71,102,255);
        //arg0.getGraphics().setBackground(background);
        
        // full screen
        //arg0.setFullscreen(true);
        
        // Load event Keyboard
		provider = new InputProvider(arg0.getInput());
		provider.addListener(this);
		
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		//provider.bindCommand(new KeyControl(Input.KEY_A), run);
		
		
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), run);
		provider.bindCommand(new KeyControl(Input.KEY_UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_W), jump);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), attack);
		provider.bindCommand(new MouseButtonControl(0), attack);
		provider.bindCommand(new ControllerButtonControl(0, 1), attack);
		

        
	}
	
	

	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
		Input input = arg0.getInput();
		
        if(input.isKeyDown(Input.KEY_DOWN))
        {
        	

        }
        /*
        if ((Mouse.isButtonDown(0)) && ((input.getMouseX()>45 && input.getMouseX()<55) && (input.getMouseY()>370 && input.getMouseY()<500))) { 
        	MovePictures(left);
        }
        
        if ((Mouse.isButtonDown(0)) && ((input.getMouseX()>1200 && input.getMouseX()<1280) && (input.getMouseY()>370 && input.getMouseY()<500))) { 
        	MovePictures(right);
        }*/

	}
	

	

	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (pictures[1] != null) {
			int width = pictures[1].getWidth();
			int height = pictures[1].getHeight();
			
			// réduction en largeur
			if ((width > MAX_WIDTH_CENTER) || (height > MAX_HEIGHT_CENTER)) {
				float facteur_width = width / MAX_WIDTH_CENTER;
				width = MAX_WIDTH_CENTER;
				height = (int)(height / facteur_width);
				
				// réduction en hauteur
				if (height > MAX_HEIGHT_CENTER) {
					float facteur_height = height / MAX_HEIGHT_CENTER;
					height = MAX_HEIGHT_CENTER;
					width = (int)(width / facteur_height);
				}
			}
			
			int pos_x = (dim_x - width) / 2;
			int pos_y = (dim_y - height) / 2;
			pictures[1].draw(pos_x, pos_y, width, height);
		}
		
		if (pictures[2] != null) {
			int width = pictures[2].getWidth();
			int height = pictures[2].getHeight();
			float facteur_height=0.0f;
			
			
			if (height > MAX_HEIGHT_SIDE) {
		        facteur_height = (float)height / (float)MAX_HEIGHT_SIDE;
				height = MAX_HEIGHT_SIDE;
				width = (int)(width / facteur_height);
			}
			
			int pos_x = dim_x - MARGIN_SIDE;
			int pos_y = (dim_y - height) / 2;
			pictures[2].draw(pos_x, pos_y, width, height);
		}
		
		if (pictures[0] != null) {
			int width = pictures[0].getWidth();
			int height = pictures[0].getHeight();
			
			if (height > MAX_HEIGHT_SIDE) {
				float facteur_height = height / MAX_HEIGHT_SIDE;
				height = MAX_HEIGHT_SIDE;
				width = (int)(width / facteur_height);
			}
			
			int pos_x = -dim_x + MARGIN_SIDE;
			int pos_y = (dim_y - height) / 2;
			pictures[0].draw(pos_x, pos_y, width, height);
		}
		
		
		/*for (int i=0; i<NB_DISPLAY_PICTURES; i++) {
			if (!pictures[i].getName().isEmpty()) {
				int pos_x;
				//if (i==0) {
				// = (dim_x - picture.getWidth()) / 2;
				//int pos_y = (dim_y - picture.getHeight()) / 2;
				//picture.draw(pos_x, pos_y);
			}
		}*/
		//pictures[0].draw(0, 0, new Color(90, 90, 90));
		
	       
		g.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls",10,50);
		g.drawString(message,50,100);
		g.drawLine(30, 400, 100, 100);
	       

	}



	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
	 */
	public void controlPressed(Command command) {
		message = "Pressed: "+command;
		Command comm = command;
		
        if ((command==attack) && ((Mouse.getX()>45 && Mouse.getX()<55) && (Mouse.getY()>370 && Mouse.getY()<500))) 
        	comm = left;
        
        
        if ((command==attack) && ((Mouse.getX()>1200 && Mouse.getX()<1280) && (Mouse.getY()>370 && Mouse.getY()<500)))  
        	comm = right;
		
		MovePictures(comm);
 
	}

	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
	 */
	public void controlReleased(Command command) {
		message = "Released: "+command;
	}

}
