import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Main class 
//Title page
/**
 * Client Code
 */ 
public class PacMan {
	public String filePath= "/Users/anna/Documents/PacMan";
	private JFrame frame;
	private JTextField txtPacman;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PacMan window = new PacMan();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**Constructor
	 *Pre: None
	 *Post: Calls initalize method 
	 */
	public PacMan() {
		initialize();
	}

	/**
	 * Pre: none
	 * Post: Creates frame, exit button, help button, play button, pacman title 
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menuText);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//Sets an icon to the frame 
		frame.setIconImage(new ImageIcon("icon.png").getImage());


		txtPacman = new JTextField();
		txtPacman.setForeground(SystemColor.window);
		txtPacman.setBounds(0, 0, 434, 81);
		txtPacman.setBackground(SystemColor.desktop);
		txtPacman.setHorizontalAlignment(SwingConstants.CENTER);
		txtPacman.setFont(new Font("Proxy 1", Font.BOLD, 41));
		txtPacman.setText("PacMan");
		frame.getContentPane().add(txtPacman);
		txtPacman.setColumns(10);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Proxy 9", Font.BOLD, 16));
		btnExit.setBounds(323, 228, 89, 23);
		frame.getContentPane().add(btnExit);

		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Proxy 9", Font.BOLD, 16));
		btnHelp.setBounds(10, 228, 89, 23);
		frame.getContentPane().add(btnHelp);

		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Proxy 9", Font.BOLD, 16));
		btnPlay.setBounds(146, 123, 130, 43);
		frame.getContentPane().add(btnPlay);

		//Exit Button
		frame.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close program
				System.exit(0);
			}
		});

		//Help Button 
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final ImageIcon icon = new ImageIcon (filePath+ "instructions.gif");
				Component frameHelp = null;
				//Display user instructions with image
				JOptionPane.showMessageDialog(frameHelp,"The goal of the game is for Pacman to accumulate points by collecting all of the coins and cherries in the maze.\n"
						+ "Coins are worth 20 points. \n"
						+ "Cherries are worth 40 points.\n"
						+ "If Pacman hits a ghost, he loses a life.\n"
						+ "Pacman has three lives. If he loses all three lives, the game is over.\n","Instructions", JOptionPane.INFORMATION_MESSAGE, icon);
			}
		});

		//Play button 
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 	
				//Close title page
				frame.setVisible(false);

				//Calls gameCall class
				gameCall main;
				try {
					main = new gameCall();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
	}
}



