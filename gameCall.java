import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.IOException;


public class gameCall {

	/**
	 * Pre: play button needs to be pressed
	 * Post: Creates frame and calls game class 
	 */
	public  gameCall() throws IOException  {
		// TODO Auto-generated method stub
		int width=1137;
		int height =600;
	
		JFrame f = new JFrame();
		
		//Sets an icon to the frame 
		f.setIconImage(new ImageIcon("icon.png").getImage());
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		
		//Calls game class 
		game pacman= new game();
		f.add(pacman);
	}
}
