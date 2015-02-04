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
	private static final int NB_DISPLAY_PICTURES_SMALL = 10;
	
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
	private int i_pic_small = 0;
	
	private ArrayList<String> pictures_path = new ArrayList<String>();
	
	//private Image[] pictures = new Image[NB_DISPLAY_PICTURES];
	//private Image[] pictures_select = new Image[NB_DISPLAY_PICTURES_SMALL];
	private ArrayList<Image> pictures_big = new ArrayList<Image>();
	private ArrayList<Image> pictures_select = new ArrayList<Image>();
	
	private Font polices;
	
	/** The input provider abstracting input */
	private InputProvider provider;
	
	/** The command for mouse left right */
	private Command mouse= new BasicCommand("mouse");
	
	/** The command for unselect*/
	private Command select = new BasicCommand("select");
	
	/** The command for unselect*/
	private Command unselect = new BasicCommand("unselect");
	
	
	private Command run = new BasicCommand("run");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	
	private boolean picture_select = false;

	
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
	
	// Load NB_DISPLAY_PICTURES BIG pictures on the screen
	private void LoadPicture() throws SlickException{
		
		// pictures left:
		if (i_picture<0)
			//pictures[0] = null; // not picture on the left
			pictures_big.add(null);
		else
			//pictures[0] = new Image(pictures_path.get(i_picture));
			pictures_big.add(new Image(pictures_path.get(i_picture)));
		
		if (pictures_path.size() != 0) {
			//pictures[1] = new Image(pictures_path.get(i_picture+1));
			pictures_big.add(new Image(pictures_path.get(i_picture+1)));
			
			// picture right
			if (pictures_path.size() > 1 && i_picture+2<pictures_path.size())
				//pictures[2] = new Image(pictures_path.get(i_picture+2));
				pictures_big.add(new Image(pictures_path.get(i_picture+2)));
			else
				//pictures[2] = null;
				pictures_big.add(null);
		}
		else {
			//pictures[1] = null;
			//pictures[2] = null;
			
			pictures_big.add(null);
			pictures_big.add(null);
		}
		
	}
	
	// Load PICTURES SMALL select on the screen
	private void LoadPicturesSelect() throws SlickException{
		//pictures_select[i_pic_small] = pictures_big.get(1);
		pictures_select.add(pictures_big.get(1));
		i_pic_small++;
		
		//Load others pictures
		try {
			pictures_big.clear(); // clear all list
			LoadPicture();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Unselect pictureselect 
	private void unselectpicture() {
		pictures_select.remove(1);
		
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
			pictures_big.clear(); // clear all list
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
		provider.bindCommand(new KeyControl(Input.KEY_UP), unselect);
		provider.bindCommand(new KeyControl(Input.KEY_DOWN), select);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), select);
		provider.bindCommand(new MouseButtonControl(0), mouse);
	}
	
	

	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub		
	}
	

	

	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		
		if (pictures_select.size()>0)	
			for (int i=0; i<pictures_select.size();  i++)
				//pictures_select[i].draw(10+(i*(1024/8))+30 , 700, 768/8, 768/8);
				pictures_select.get(i).draw(10+(i*(1024/8))+30 , 700, 768/8, 768/8);
		
		if (pictures_big.get(1) != null) {//(pictures[1] != null) {
			int width = pictures_big.get(1).getWidth();//pictures[1].getWidth();
			int height = pictures_big.get(1).getHeight();//pictures[1].getHeight();
			
			// reduce larg
			if ((width > MAX_WIDTH_CENTER) || (height > MAX_HEIGHT_CENTER)) {
				float facteur_width = width / MAX_WIDTH_CENTER;
				width = MAX_WIDTH_CENTER;
				height = (int)(height / facteur_width);
				
				// reduce height
				if (height > MAX_HEIGHT_CENTER) {
					float facteur_height = height / MAX_HEIGHT_CENTER;
					height = MAX_HEIGHT_CENTER;
					width = (int)(width / facteur_height);
				}
			}
			
			int pos_x = (dim_x - width) / 2;
			int pos_y = (dim_y - height) / 2;
			//pictures[1].draw(pos_x, pos_y, width, height);
			pictures_big.get(1).draw(pos_x, pos_y, width, height);
		}
		
		if (pictures_big.get(2) != null) {//(pictures[2] != null) {
			int width = pictures_big.get(2).getWidth();//pictures[2].getWidth();
			int height = pictures_big.get(2).getHeight();//pictures[2].getHeight();
			float facteur_height=0.0f;
			
			
			if (height > MAX_HEIGHT_SIDE) {
		        facteur_height = (float)height / (float)MAX_HEIGHT_SIDE;
				height = MAX_HEIGHT_SIDE;
				width = (int)(width / facteur_height);
			}
			
			int pos_x = dim_x - MARGIN_SIDE;
			int pos_y = (dim_y - height) / 2;
			//pictures[2].draw(pos_x, pos_y, width, height);
			pictures_big.get(2).draw(pos_x, pos_y, width, height);
		}
		
		if (pictures_big.get(0) != null) {//(pictures[0] != null) {
			int width = pictures_big.get(0).getWidth();//pictures[0].getWidth();
			int height = pictures_big.get(0).getHeight();//pictures[0].getHeight();
			
			if (height > MAX_HEIGHT_SIDE) {
				float facteur_height = height / MAX_HEIGHT_SIDE;
				height = MAX_HEIGHT_SIDE;
				width = (int)(width / facteur_height);
			}
			
			int pos_x = -dim_x + MARGIN_SIDE;
			int pos_y = (dim_y - height) / 2;
			
			//if (picture_select)	{
			//	picture_select = false;
			//	pictures_select[i_pic_small] = pictures_big.get(1);//pictures[1];
			//	i_pic_small++;
				
			//}
			//else {
				pictures_big.get(0).draw(pos_x, pos_y, width, height);//pictures[0].draw(pos_x, pos_y, width, height);
				
				
			//}
			
			//if (i_pic_small>0)	
			//	for (int i=0; i<i_pic_small;  i++)
			//		pictures_select[i].draw(10+(i*(width/8))+30 , 700, width/8, height/8);
				
		}
		
		
	       
		g.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls",10,50);
		g.drawString(message,50,100);

		// pointer left
		g.drawLine(50, 400, 70, 380);
		g.drawLine(50, 400, 70, 420);
		
		// pointer right
		g.drawLine(1200, 380, 1220, 400);
		g.drawLine(1200, 420, 1220, 400);
	       

	}



	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
	 */
	public void controlPressed(Command command) {
		message = "Pressed: "+command;
		Command comm = command;
		
		if (comm==select) {
			//picture_select = true;
			try {
				LoadPicturesSelect();
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (comm==unselect) {
			unselectpicture();
		}
		else {
			if ((command==mouse) && ((Mouse.getX()>45 && Mouse.getX()<65) && (Mouse.getY()>605 && Mouse.getY()<642))) 
				comm = left;
        
			if ((command==mouse) && ((Mouse.getX()>1200 && Mouse.getX()<1280) && (Mouse.getY()>605 && Mouse.getY()<642)))  
				comm = right;
			MovePictures(comm);
		}
		
 
	}



	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
	 */
	public void controlReleased(Command command) {
		message = "Released: "+command;
	}

}
