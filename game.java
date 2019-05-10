import java.awt.Component; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class game extends JPanel implements KeyListener, ActionListener {
	//Declare variables 

	//File path
	PacMan image = new PacMan();
	private String filePath = image.filePath;

	final int RIGHT =0, LEFT=1, UP=2, DOWN=3;
	final int RED =0, PINK =1, ORANGE =2, BLUE =3;
	final int dimensions=20;	//Restrictions on movement

	//Timer 
	Timer t = new Timer(1000, this);
	Timer tm = new Timer(5, this);

	char [] [] board;	//Grid data 

	int numOfGhost = 4; 
	int numOfLives=3;	//Life counter 
	int numOfCoins=0;	//Coin counter
	int numOfCherry=0;	//Cherry counter
	int score=0; 		//Score counter

	int x, y ;																//Pacman coordinates 
	int currentDirect;														//Pacman direction
	int gPinkX, gPinkY, gRedX,gRedY, gOrangeX, gOrangeY, gBlueX, gBlueY;	//Ghost coordinates
	int [] [] gVel = new int [numOfGhost][2];								//Ghost velocity
	int [] currentGDirect=new int[numOfGhost];								//Ghost direction

	//Images 
	Image [] pacManImg= new Image [4];
	Image coin;
	Image cherry;
	Image [][] ghost= new Image [numOfGhost][4];

	//Labels 
	private JLabel lblScore;
	private JLabel lblLife1;
	private JLabel lblLife2;
	private JLabel lblLife3;

	PacManBoard2 grid = new PacManBoard2(); 

	/**
	 * Constructor
	 * Pre: play button needs to be pressed
	 * Post: starts timer,creates score label, life label images, exit button,
	 * imports images and assign to variables, calls initialize method
	 * calls PacManBoard class to get board from input file
	 * calculates numOfCherry and numOfCoins.
	 */
	public game() 
	{	
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setBackground(Color.BLUE);
		setLayout(null);

		//Score Labe;
		lblScore = new JLabel();
		lblScore.setBounds(getWidth()/2, 490, 434, 81);
		lblScore.setForeground(SystemColor.window);
		lblScore.setFont(new Font("Proxy 1", Font.BOLD, 30));
		lblScore.setText("Score: 0");
		add(lblScore);

		//Exit Button
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Proxy 9", Font.BOLD, 16));
		btnExit.setBounds(1000, 520, 89, 23);
		add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Close program
				System.exit(0);
			}
		});

		//Life Label
		JLabel lblLives = new JLabel();
		lblLives.setBounds(320, 490, 434, 81);
		lblLives.setForeground(SystemColor.window);
		lblLives.setFont(new Font("Proxy 1", Font.BOLD, 30));
		lblLives.setText("Lives: ");
		add(lblLives);

		//Life images
		lblLife1 = new JLabel("");
		lblLife1.setIcon(new ImageIcon(filePath+"pacmanright.jpg"));
		lblLife1.setBounds(444, 485, 22, 96);
		add(lblLife1);

		lblLife2 = new JLabel("");
		lblLife2.setIcon(new ImageIcon(filePath+"pacmanright.jpg"));
		lblLife2.setBounds(476, 485, 22, 96);
		add(lblLife2);

		lblLife3 = new JLabel("");
		lblLife3.setIcon(new ImageIcon(filePath+"pacmanright.jpg"));
		lblLife3.setBounds(508, 485, 22, 96);
		add(lblLife3);


		//Import images 
		//Pacman 
		File  inpt1=new File (filePath+"pacmanright.jpg");		
		File  inpt2=new File (filePath+"pacmanleft.jpg");
		File  inpt3=new File (filePath+"pacmanup.jpg");
		File  inpt4=new File (filePath+"pacmandown.jpg");

		//Ghost
		File  input1=new File (filePath+"ghost12.jpg");	
		File  input2=new File (filePath+"ghost13.jpg");
		File  input3=new File (filePath+"ghost20.png");	
		File  input4=new File (filePath+"ghost21.png");	
		File  input5=new File (filePath+"ghost32.png");	
		File  input6=new File (filePath+"ghost33.png");	
		File  input7=new File (filePath+"ghost40.jpg");	
		File  input8=new File (filePath+"ghost41.jpg");

		//Cherry and Coin
		File  input9 = new File (filePath+"coin.png");
		File  input10 = new File (filePath+"cherry.png");

		try 
		{
			pacManImg[0] =ImageIO.read(inpt1);
			pacManImg[1]=ImageIO.read(inpt2);
			pacManImg[2]=ImageIO.read(inpt3);
			pacManImg[3]=ImageIO.read(inpt4);

			ghost [RED][UP] = ImageIO.read(input1);
			ghost [RED][DOWN] = ImageIO.read(input2);
			ghost[ORANGE][RIGHT]= ImageIO.read(input3);
			ghost[ORANGE][LEFT]= ImageIO.read(input4);
			ghost [BLUE][UP] = ImageIO.read(input5);
			ghost [BLUE][DOWN] = ImageIO.read(input6);
			ghost [PINK][RIGHT] = ImageIO.read(input7);
			ghost [PINK][LEFT] = ImageIO.read(input8);

			cherry = ImageIO.read(input9);
			coin = ImageIO.read(input10);
		} 
		catch (IOException e) {
			System.out.println("Problem reading file.");
			System.err.println("IOException: " + e.getMessage());
		}

		//Calls method to initialize variables
		initialize();

		//Calls PacManBoard class
		board=PacManBoard2.board();
		

		//Calculate numOfCoins and numOfCherry
		for (int r=0; r<board.length; r++) {
			for (int c=0; c<board[0].length; c++) {
				if (board[r][c]=='3')
				{
					numOfCoins=numOfCoins+1;
				}
				if (board[r][c]=='4')
				{
					numOfCherry=numOfCherry+1;
				}}}
	}

	/**
	 * Description: Draw images of the game
	 * pre: Images need to be imported, and board need be created 
	 * post: Display images with current position and updated board
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		//Path 
		for (int r=0; r<board.length; r++) {
			for (int c=0; c<board[0].length; c++) {
				if (board[r][c] != '0') {
					g.fillRect(c*dimensions, r*dimensions, dimensions, dimensions);
					g.setColor(Color.BLACK);
				}}}

		//Inside board black part: Design purposes
		for (int r=0; r<board.length; r++) {
			for (int c=0; c<board[0].length; c++) {
				if (board[r][c] == '8') {
					g.fillRect(c*dimensions, r*dimensions, 5, 5);
					g.setColor(Color.BLACK);
				}}}

		//Coin
		for (int r=0; r<board.length; r++) {
			for (int c=0; c<board[0].length; c++) {
				if (board[r][c] == '4') {
					g.drawImage(coin, c*dimensions,r*dimensions,null);
				}}}

		//Cherry
		for (int r=0; r<board.length; r++) {
			for (int c=0; c<board[0].length; c++) {
				if (board[r][c] == '3') {
					g.drawImage(cherry, c*dimensions,r*dimensions,null);
				}}}


		//Fix board
		g.fillRect(0,0,dimensions,dimensions);
		g.setColor(Color.BLACK);

		//Draw Pacman with current direction of mouth 
		g.drawImage(pacManImg[currentDirect],x,y,null);

		//Draw each Ghost with direction of eyes 
		g.drawImage(ghost[PINK][currentGDirect[PINK]], gPinkX, gPinkY, null);
		g.drawImage(ghost[RED][currentGDirect[RED]], gRedX, gRedY, null);
		g.drawImage(ghost[ORANGE][currentGDirect[ORANGE]], gOrangeX, gOrangeY, null);
		g.drawImage(ghost[BLUE][currentGDirect[BLUE]], gBlueX, gBlueY, null);

		//Timer
		tm.start();
	}

	/**
	 * Pre: An action event  must occur
	 * Post; calls method to check current position of pacman
	 * calls method to move pacman
	 * Calls method to ghost
	 */
	public void actionPerformed(ActionEvent e){
		currentPosition();
		ghostMove();
	}

	/**
	 * Pre; action event 
	 * Post: Calls each ghost method to move
	 */
	private void ghostMove() 
	{
		pinkGhost();
		redGhost();
		orangeGhost();
		blueGhost();
	}

	/**
	 * Pre: called from ghostMove()
	 * Post: X,Y coordinates of Ghost updated  and paintComponent is called
	 * Description: Vertical movement of blue ghost 
	 */
	private void blueGhost() {
		//Check if Y coordinate is out of bounds and update direction and speed
		if (gBlueY < 120 )
		{
			gVel[BLUE][1] = -gVel[BLUE][1];
			currentGDirect[BLUE]=DOWN;
			repaint();
		}
		else if (gBlueY >480)
		{
			gVel[BLUE][1] = -gVel[BLUE][1];
			currentGDirect[BLUE]=UP;
			repaint();
		}

		gBlueY =gBlueY + gVel[BLUE][1];
		repaint();

	}

	/**
	 * Pre: called from ghostMove()
	 * Post: X,Y coordinates of Ghost updated  and paintComponent is called
	 * Description: Horizontal movement of pink ghost 
	 */
	private void pinkGhost(){
		//Check if X coordinate is out of bounds and update direction and speed
		if (gPinkX < 0 )
		{
			gVel[PINK][0] = -gVel[PINK][0];
			currentGDirect[PINK]=RIGHT;
			repaint();
		}
		else if (gPinkX >1100)
		{
			gVel[PINK][0] = -gVel[PINK][0];
			currentGDirect[PINK]=LEFT;
			repaint();
		}

		gPinkX = (gPinkX + gVel[PINK][0]);
		repaint();
	}

	/**
	 * Pre: called from ghostMove()
	 * Post: X,Y coordinates of Ghost updated  and paintComponent is called
	 * Description: Vertical movement of red ghost 
	 */
	private void redGhost(){
		//Check if Y coordinate is out of bounds and update direction and speed
		if (gRedY < 120 )
		{
			gVel[RED][1] = -gVel[RED][1];
			currentGDirect[RED]=DOWN;
			repaint();
		}
		else if (gRedY >480)
		{
			gVel[RED][1] = -gVel[RED][1];
			currentGDirect[RED]=UP;
			repaint();
		}
		gRedY =gRedY + gVel[RED][1];
		repaint();
	}

	/**
	 * Pre: called from ghostMove()
	 * Post: X,Y coordinates of Ghost updated  and paintComponent is called
	 * Description: Horizontal movement of orange ghost 
	 */
	private void orangeGhost(){
		if (gOrangeX < 120 )
		{
			gVel[ORANGE][0] = -gVel[ORANGE][0];

			currentGDirect[ORANGE]=RIGHT;
			repaint();
		}
		else if (gOrangeX >980)
		{
			gVel[ORANGE][0] = -gVel[ORANGE][0];
			currentGDirect[ORANGE]=LEFT;
			repaint();
		}
		gOrangeX = (gOrangeX + gVel[ORANGE][0]);
		repaint();
	}

	/**
	 * Description: Checks if a key is pressed and call the appropriate method
	 * Pre: Arrow Key is pressed
	 * Post: Calls method to update x,y coordinate
	 */
	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP){
			up();
		}

		else if (code == KeyEvent.VK_DOWN){
			down();
		}

		else if (code == KeyEvent.VK_LEFT){
			left();
		}

		else if (code == KeyEvent.VK_RIGHT){
			right();
		}
	}

	/**
	 * Description : Pacman moves up if Pcman is able to move according the board's path
	 * Pre: Up key must be pressed 
	 * Post: Updates y coordinate of pacman and current direction of pacman
	 */
	public void up(){
		//Check if updated y coordinate is not out of bounds
		if (y/dimensions-1>=0){

			//Check if updated y is part of pacman's path 
			if (board[y/dimensions-1][x/dimensions]!='0')
			{
				//Update y coordinate so move 20 units up
				y=y-dimensions;

				//Update pacman direction to up
				currentDirect = UP;
			}}}

	/**
	 * Description : Pacman moves down if Pacman is able to move according the board's path
	 * Pre: Down key must be pressed 
	 * Post: Updates y coordinate of pacman and current direction of pacman
	 */
	public void down(){
		//Check if updated y coordinate is not out of bounds
		if (y/dimensions+1<board.length){

			//Check if updated y is part of pacman's path 
			if (board[y/dimensions+1][x/dimensions]!='0')
			{
				//Update y coordinate so move 20 units down
				y=y+dimensions;

				//Update pacman direction to up
				currentDirect = DOWN;
			}}}

	/**
	 * Description : Pacman moves left if Pacman is able to move according the board's path
	 * Pre: Left key must be pressed 
	 * Post: Updates y coordinate of pacman and current direction of pacman
	 */
	public void left(){
		//Check if updated x coordinate is not out of bounds
		if (x/dimensions-1>=0){

			//Check if updated x is part of pacman's path 
			if (board[y/dimensions][x/dimensions-1]!='0')
			{
				//Update x coordinate so move 20 units left
				x=x-dimensions;

				//Update pacman direction to left
				currentDirect = LEFT;
			}}}

	/**
	 * Description : Pacman moves right if Pacman is able to move according the board's path
	 * Pre: Right key must be pressed 
	 * Post: Updates y coordinate of pacman and current direction of pacman
	 */
	public void right(){
		//Check if updated x coordinate is not out of bounds
		if (x/dimensions+1<board[0].length)
		{
			//Check if updated x is part of pacman's path 
			if (board[y/dimensions][x/dimensions+1]!='0')
			{
				//Update x coordinate so move 20 units left
				x=x+dimensions;

				//Update pacman direction to right
				currentDirect = RIGHT;
			}}}

	/**
	 * Description: Checks the current position of pacman if there are any collisions with cherry, coins ,ghost
	 * Pre: An action event has occurred and Pacman coordinates have been updated 
	 * Post: Updates board to clear the cherry, score
	 * Calls score method if collision with coin/cherry, calls lives method if collision ghost
	 */
	public void  currentPosition(){

		//Coin 
		//If pacman coordinates equal to a coin (3)
		if (board[y/dimensions][x/dimensions]=='3')
		{
			//Clear the board to a black square
			board[y/dimensions][x/dimensions]='1';

			//Calls method to increment score with coin value
			Score(20); 

		}
		//Cherry
		//If pacman coordinates equal to a cherry (4)
		else if(board[y/dimensions][x/dimensions]=='4')
		{
			//Clear the board to a black square
			board[y/dimensions][x/dimensions]='1';

			//Calls method to increment score with coin value
			Score(40); 
		}
		//Check ghost collision if pacman coordinates equal to ghost coordinates 
		if((x==gPinkX && y == gPinkY)|| (x==gRedX && y==gRedY)|| (x==gOrangeX && y==gOrangeY)|| (x==gBlueX && y==gBlueY))
		{
			//Calls method to decrease number of lives
			lives();
		}
	}

	/**
	 * Description: Updates numOfLive and sets lblLife to invisible
	 * pre: pacman coordinates equal to ghost coordinates
	 * post: Updates value of num of lives, calls initalize method, lblLife is updated to invisible, calls appropriate method for dialog 
	 */
	private void lives()
	{
		//Decrease num of lives 
		numOfLives= numOfLives-1;
		if (numOfLives ==2)
		{
			//Calls initialize method and lifeLost method
			initialize();
			lifeLost();

			//Sets label to invisible 
			lblLife3.setVisible(false);
		}
		else if (numOfLives ==1)
		{
			//Calls initialize method and lifeLost method
			initialize();
			lifeLost();

			//Sets label to invisible 
			lblLife2.setVisible(false);
		}
		else if (numOfLives==0)
		{
			//Sets label to invisible 
			lblLife1.setVisible(false);

			//Calls gameover method
			gameOver();
		}
	}

	/**
	 * Description: Rest values of variables
	 * Pre: called from constructor, user loses a life  
	 * Post: initialize variables to apprioriate values 
	 */
	private void initialize() {
		//Pacman X and Y
		x=540;
		y=240;
		//Ghost coordinates
		gPinkX=0;
		gPinkY=400;
		gRedX=200;
		gRedY = 120;
		gOrangeX=980;
		gOrangeY=120;
		gBlueX=900;
		gBlueY=480;

		//Direction of ghost and pacman 
		currentGDirect[RED]=DOWN;
		currentGDirect[PINK]=RIGHT;
		currentGDirect[ORANGE]=RIGHT; 
		currentGDirect[BLUE]=UP; 

		currentDirect = RIGHT; 

		//Velocity of ghost 
		gVel[BLUE][0]=gVel[BLUE][1]= gVel[ORANGE][0]=gVel[ORANGE][1]=gVel[PINK][0]=gVel[PINK][1]= gVel[RED][0]= gVel [RED] [1]=1;
	}

	/**
	 * Description: Lost a life message displayed
	 * Pre: User lost a life
	 * Post:Displays a dialog with life lost message with a image 
	 */
	private void lifeLost()
	{
		final ImageIcon icon = new ImageIcon (filePath+ "lives.gif");
		Component frameLives = null;
		JOptionPane.showMessageDialog(frameLives,"You lost a life!","Life Lost",JOptionPane.INFORMATION_MESSAGE,icon);
	}
	
	/**
	 * Description: Displays a dialog and closes program
	 * Pre: User lost the game
	 * Post: Dialog message game over with image and closes program
	 */
	private void gameOver()
	{
		final ImageIcon icon = new ImageIcon (filePath+ "pacmanDying.gif");
		Component frameGameOver= null;
		JOptionPane.showMessageDialog(frameGameOver,"You died!\nGame Over","Game Over",JOptionPane.INFORMATION_MESSAGE,icon);
		System.exit(0);
	}

	/**
	 * Description: dispays updated score value and checks if user collected all coins/cherry
	 * Pre:	Pacman x and y coordinates equal to coin/cherry 
	 * Post: Updated score value, displayed on frame and calls win method if score equals to max number of points 
	 */
	private void Score(int coinValue ) 
	{
		//Increment score with coin value 
		score= score+coinValue; 
		
		//Display updated score on frame
		lblScore.setText("Score: "+ score);

		//If user collected max number of points call win method
		if (score == numOfCoins*20+numOfCherry*40)
		{
			win();
		}
	}

	/**
	 * Description: Displays a dialog and closes program
	 * Pre: Score equals to max number of points
	 * Post: Dialog message You Win with image and closes program
	 */
	private void win()
	{
		final ImageIcon icon = new ImageIcon (filePath+ "win.gif");
		Component frameWin= null;
		JOptionPane.showMessageDialog(frameWin,"You collected all the coins\nYou Win!","Win",JOptionPane.INFORMATION_MESSAGE,icon);
		System.exit(0);
	}

	public void keyTyped(KeyEvent e){}

	public void keyReleased(KeyEvent e){}
}