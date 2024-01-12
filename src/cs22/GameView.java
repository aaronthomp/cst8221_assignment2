package cs22;
/*
 * Student Name: Aaron Thompson
 * Student Number: 041058950
 * Lab Professor: Daniel Cormier
 * Lab Section: 302
 * Date: 2023-11-05
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.ImageIcon;

/**
 * GameView class contains all elements for the GUI
 */
public class GameView {

	/**
	 * instance of the Controller class
	 */
	private GameController control;

	/**
	 * variable stores an x coordinate
	 */
	private int xCoord;

	/**
	 * variable stores a y coordinate
	 */
	private int yCoord;

	/**
	 * variable holds row count
	 */
	private int row = 50;

	/**
	 * variable holds column count
	 */
	private int col = 50;

	/**
	 * variable holds size of a button
	 */
	private int buttonSize = 11;
	/**
	 * stores a grid of buttons
	 */
	private JButton gridButton[][];
	/**
	 * button to be used for the board
	 */
	private JButton boardButton;
	/**
	 * used for random button
	 */
	private JButton randButton;
	//	private JButton manualButton;
	/**
	 * button used to start execution
	 */
	private JButton startButton;
	/**
	 * button used to stop execution
	 */
	private JButton stopButton;
	/**
	 * button used to choose color
	 */
	private JButton colorButton;
	/**
	 * label to show model
	 */
	private JLabel modelLabel;
	/**
	 * text field for model
	 */
	private JTextField modelText;
	/**
	 * mutlicolor checkbox
	 */
	private JCheckBox multiCheck;
	/**
	 * label for steps
	 */
	private JLabel stepLabel;
	/**
	 * allows input of text for steps
	 */
	private JTextField stepText;
	/**
	 * menu item for color swatch
	 */
	JMenu gameMenu = new JMenu("Game");
	/**
	 * color menu item
	 */
	JMenuItem helpItem1 = new JMenuItem("Colors");
	/**
	 * English menu item
	 */
	JMenuItem langItem1 = new JMenuItem("English");
	/**
	 * French menu item
	 */
	JMenuItem langItem2 = new JMenuItem("French");
	/**
	 * language menu
	 */
	JMenu langMenu = new JMenu("Language");
	/**
	 * help menu
	 */
	JMenu helpMenu = new JMenu("Help");
	/**
	 * new menu item
	 */
	JMenuItem gameItem1 = new JMenuItem("New");
	/**
	 * solution menu item
	 */
	JMenuItem gameItem2 = new JMenuItem("Solution");
	/**
	 * exit menu item
	 */
	JMenuItem gameItem3 = new JMenuItem("Exit");
	/**
	 * about menu item
	 */
	JMenuItem helpItem2 = new JMenuItem("About");
	/**
	 * store language
	 */
	String selectedLanguage;
	/**
	 * initialized color variable to black
	 */
	Color color = Color.BLACK;

	/**
	 * empty no arg constructor
	 */
	public GameView() {
	}

	/**
	 * creating a new frame
	 */
	JFrame frame = new JFrame("Game of Life");
	/**
	 * new jpanel for the header of the frame
	 */
	JPanel pHeader = new JPanel();
	/**
	 * new jpanel for the body of the frame
	 */
	JPanel pGrid = new JPanel(new GridLayout(row, col));
	/**
	 * new jpanel for the footer of the frame
	 */
	JPanel pFooter = new JPanel(new GridLayout(2, 1));
	/**
	 * icon for about menu item
	 */
	ImageIcon aboutIcon = new ImageIcon("./src/cs22/menuiconabt.gif");
	/**
	 * icon for color menu item
	 */
	ImageIcon colorIcon = new ImageIcon("./src/cs22/menuiconcol.gif");
	/**
	 * icon for english menu item
	 */
	ImageIcon englishIcon = new ImageIcon("./src/cs22/menuiconeng.gif");
	/**
	 * icon for exit menu item
	 */
	ImageIcon exitIcon = new ImageIcon("./src/cs22/menuiconext.gif");
	/**
	 * icon for french menu item
	 */
	ImageIcon frenchIcon = new ImageIcon("./src/cs22/menuiconfra.gif");
	/**
	 * icon for new menu item
	 */
	ImageIcon newIcon = new ImageIcon("./src/cs22/menuiconnew.gif");
	/**
	 * icon for solution menu item
	 */
	ImageIcon solutionIcon = new ImageIcon("./src/cs22/menuiconsol.gif");
	/**
	 * map for my translations
	 */
	private Map<String, Map<String, String>> translations = new HashMap<>();

	/**
	 * show method instantiates the creation of the GUI
	 */
	public void show() {
		splashScreen();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		setHeader();
		initGrid();
		setFooter();
		pGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.pack();
		frame.setLocation(0, 0);
		setMenuBar();
		populateTranslations();
		frame.setVisible(true);
	}

	/**
	 * splash screen method creates the splash screen
	 */
	// method created using various references
	public void splashScreen() {
		JWindow splash = new JWindow();
		ImageIcon image = new ImageIcon("./src/cs22/game.png");
		JLabel label = new JLabel(image);
		splash.getContentPane().add(label);
		splash.pack();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - splash.getWidth()) / 2;
		int y = (screenSize.height - splash.getHeight()) / 2;

		splash.setLocation(x, y);
		splash.setVisible(true);

		// create delay of 1 second
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// hide splash screen before disposing
		splash.setVisible(false);
		// get rid of splash screen
		splash.dispose();
	}

	/**
	 * sets the menu bar for the frame
	 */
	public void setMenuBar() {
		// GeeksForGeeks was the reference
		JMenuBar menuBar = new JMenuBar();
		gameItem1.setIcon(newIcon);
		gameItem1.setActionCommand("newBoard");
		gameItem1.addActionListener(e -> control.actionPerformed(e));

		gameItem2.setIcon(solutionIcon);
		gameItem2.setActionCommand("solution");
		gameItem2.addActionListener(e -> control.actionPerformed(e));

		gameItem3.setIcon(exitIcon);
		gameItem3.setActionCommand("exit");
		gameItem3.addActionListener(e -> control.actionPerformed(e));
		langItem1.setIcon(englishIcon);
		langItem1.setActionCommand("english");
		langItem1.addActionListener(e -> control.actionPerformed(e));
		langItem2.setIcon(frenchIcon);
		langItem2.setActionCommand("french");
		langItem2.addActionListener(e -> control.actionPerformed(e));
		helpItem1.setIcon(colorIcon);
		helpItem1.setActionCommand("colorMenu");
		helpItem1.addActionListener(e -> control.actionPerformed(e));

		helpItem2.setIcon(aboutIcon);
		helpItem2.setActionCommand("about");
		helpItem2.addActionListener(e -> control.actionPerformed(e));
		gameMenu.add(gameItem1);
		gameMenu.add(gameItem2);
		gameMenu.add(gameItem3);
		langMenu.add(langItem1);
		langMenu.add(langItem2);
		helpMenu.add(helpItem1);
		helpMenu.add(helpItem2);
		menuBar.add(gameMenu);
		menuBar.add(langMenu);
		menuBar.add(helpMenu);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * sets the header for pHeader variable to be set on frame
	 */
	public void setHeader() {
		// stackhowto.com for image reference
		// changed it slightly to benefit my code
		JLabel headerImage = new JLabel(new ImageIcon("./src/cs22/gl.png"));
		pHeader.add(headerImage);
		pHeader.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.add(pHeader, BorderLayout.NORTH);
	}

	/**
	 * sets the footer for pFooter variable to be set on frame
	 */
	public void setFooter() {
		JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel botRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
		randButton = new JButton("Random");
		randButton.setActionCommand("Random");
		randButton.addActionListener(e -> control.actionPerformed(e));
		modelLabel = new JLabel("Model:");
		modelText = new JTextField(18);
		multiCheck = new JCheckBox("Multicolor");
		colorButton = new JButton("Color");
		colorButton.setActionCommand("colorMenu");
		colorButton.addActionListener(e -> control.actionPerformed(e));
		startButton = new JButton("Start");
		startButton.setActionCommand("Start");
		startButton.addActionListener(e -> control.actionPerformed(e));
		stepLabel = new JLabel("Steps:");
		stepText = new JTextField(5);
		stopButton = new JButton("Stop");
		stopButton.setActionCommand("Stop");
		stopButton.addActionListener(e -> control.actionPerformed(e));
		topRow.add(randButton);
		topRow.add(modelLabel);
		topRow.add(modelText);
		topRow.add(multiCheck);
		topRow.add(colorButton);
		botRow.add(startButton);
		botRow.add(stepLabel);
		botRow.add(stepText);
		botRow.add(stopButton);
		pFooter.add(topRow);
		pFooter.add(botRow);
		frame.add(pFooter, BorderLayout.PAGE_END);
	}

	/**
	 * initializes the grid of buttons and sets it in the frame
	 */
	public void initGrid() {
		gridButton = new JButton[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {

				boardButton = new JButton();
				boardButton.setMaximumSize(new Dimension(buttonSize, buttonSize));
				boardButton.setMinimumSize(new Dimension(buttonSize, buttonSize));
				boardButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
				boardButton.setBackground(Color.WHITE);
				gridButton[i][j] = boardButton;
				int jCoord = j;
				int iCoord = i;
				boardButton.setActionCommand("boardClickAction");
				boardButton.addActionListener(e -> {
					xCoord = iCoord;
					yCoord = jCoord;
					control.actionPerformed(e);
				});
				pGrid.add(boardButton);
			}
		}
		frame.add(pGrid, BorderLayout.CENTER);
	}

	/**
	 * sets the color of alive squares
	 */
	public void setColor() {
		color = JColorChooser.showDialog(helpItem1, null, color);
	}

	/**
	 * gets the x coordinate
	 * 
	 * @return xCoord
	 */
	public int getXCoord() {
		return xCoord;
	}

	/**
	 * gets the y coordinate
	 * 
	 * @return yCoord
	 */
	public int getYCoord() {
		return yCoord;
	}

	/**
	 * updates the current board state with correct colors
	 * 
	 * @param board     takes in the board from model class
	 * @param neighbour counted neighbours
	 * @param i         i is x coordinate
	 * @param j         j is y coordinate
	 */
	public void updateBoard(boolean board[][], int neighbour, int i, int j) {
		//		for(int i = 0; i<row; i++) {
		//			for(int j = 0; j<col; j++) {
		if (board[i][j]) {
			if (multiCheck.isSelected()) {
				switch (neighbour) {
				case 0:
					gridButton[i][j].setBackground(Color.RED);
					break;

				case 1:
					gridButton[i][j].setBackground(Color.GREEN);
					break;

				case 2:
					gridButton[i][j].setBackground(Color.BLUE);
					break;

				case 3:
					gridButton[i][j].setBackground(Color.YELLOW);
					break;

				case 4:
					gridButton[i][j].setBackground(Color.PINK);
					break;

				case 5:
					gridButton[i][j].setBackground(Color.CYAN);
					break;

				case 6:
					gridButton[i][j].setBackground(Color.MAGENTA);
					break;

				case 7:
					gridButton[i][j].setBackground(Color.ORANGE);
					break;

				case 8:
					gridButton[i][j].setBackground(Color.BLACK);
					break;
				}
			} else {
				gridButton[i][j].setBackground(color);
			}
		} else {
			gridButton[i][j].setBackground(Color.WHITE);
		}
	}
	//		}
	//	}

	/**
	 * validates the string entered by user from model text field
	 * 
	 * @return null if error or the binary rule if valid
	 */
	public String modelValidation() {
		String binaryRule = modelText.getText();
		if (binaryRule.matches("[01]{18}")) {
			return binaryRule;
		} else {
			if (selectedLanguage == "English" || selectedLanguage == null) {
				showErrorDialog("Invalid input please enter an 18-bit binary number");
				return null;
			} else {
				showErrorDialog("Entrée non valide, veuillez entrer un nombre binaire de 18 bits");
				return null;
			}
		}
		//		return null;
	}

	/**
	 * creates a dialog error message
	 * 
	 * @param message input error message
	 */
	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(frame, message, "Invalid Entry", JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * creates a dialog about message to let the user know about the Game of Life
	 */
	public void showAboutDialog() {
		if (selectedLanguage == "English" || selectedLanguage == null) {
			JOptionPane.showMessageDialog(frame,
					"The Game of Life is a cellular automaton that is played on a 2D\n"
							+ " square grid. Each square (or \"cell\") on the grid can be either alive or dead,\n"
							+ " and they evolve according to a set of rules that YOU the user can set!",
							"About", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame,
					"Le Jeu de la Vie est un automate cellulaire qui se joue sur une grille carrée en 2D.\n"
							+ " Chaque carré (ou « cellule ») de la grille peut être vivant ou mort,\n"
							+ " et ils évoluent selon un ensemble de règles que VOUS, l’utilisateur, pouvez définir!",
							"About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * gets steps from user entry in steps text field
	 * 
	 * @return step on valid input or null on error
	 */
	public String getSteps() {
		String step = stepText.getText();
		//valid with regex, first number cant be 0 only allowing up to 4 numbers total
		if (step.matches("[1-9]?[0-9]{0,3}")) {
			return step;
		} else {
			if(selectedLanguage == "English" || selectedLanguage == null)
			showErrorDialog("Invalid input please enter a valid integer between 1-9999");
			else {
				showErrorDialog("Entrée non valide, veuillez entrer un entier valide entre 1 et 9999");
			}
		}
		return null;
	}

	/**
	 * converts string input to int
	 * 
	 * @param input takes in string input
	 * @return returns int output
	 */
	public int stringToInt(String input) {
		int output = 1;
		try {
			output = Integer.parseInt(input);
		} catch (NumberFormatException e) {
		}
		return output;
	}

	/**
	 * used to close the frame for the exit menu in Game Menu bar
	 */
	public void closeFrame() {
		// probably not the best way to close the frame but im running out of time to
		// finish this and this works.
		frame.setVisible(false);
		frame.dispose();
	}

	/**
	 * populate a map of english and french translations to swap between easily
	 */
	public void populateTranslations() {
		// used this method in A12 got it originally from chatGPT but this
		// implementation was just a copy of previous assignment
		Map<String, String> englishTranslations = new HashMap<>();
		englishTranslations.put("Title", "Game of Life");
		englishTranslations.put("GameMenu", "Game");
		englishTranslations.put("LangMenu", "Language");
		englishTranslations.put("HelpMenu", "Help");
		englishTranslations.put("NewMenuItem", "New");
		englishTranslations.put("SolutionMenuItem", "Solution");
		englishTranslations.put("ExitMenuItem", "Exit");
		englishTranslations.put("EnglishMenuItem", "English");
		englishTranslations.put("FrenchMenuItem", "French");
		englishTranslations.put("ColorMenuItem", "Color");
		englishTranslations.put("AboutMenuItem", "About");
		englishTranslations.put("RandomButton", "Random");
		englishTranslations.put("ModelLabel", "Model:");
		englishTranslations.put("MultiCheck", "Multicolor");
		englishTranslations.put("ColorButton", "Color");
		englishTranslations.put("StartButton", "Start");
		englishTranslations.put("StepLabel", "Step:");
		englishTranslations.put("StopButton", "Stop");
		translations.put("English", englishTranslations);

		Map<String, String> frenchTranslations = new HashMap<>();
		frenchTranslations.put("Title", "Jeu de la Vie");
		frenchTranslations.put("GameMenu", "Jeu");
		frenchTranslations.put("LangMenu", "Langue");
		frenchTranslations.put("HelpMenu", "Aide");
		frenchTranslations.put("NewMenuItem", "Nouveau");
		frenchTranslations.put("SolutionMenuItem", "Solution");
		frenchTranslations.put("ExitMenuItem", "Sortie");
		frenchTranslations.put("EnglishMenuItem", "Anglaise");
		frenchTranslations.put("FrenchMenuItem", "Français");
		frenchTranslations.put("ColorMenuItem", "Couleur");
		frenchTranslations.put("AboutMenuItem", "Environ");
		frenchTranslations.put("RandomButton", "Aléatoire");
		frenchTranslations.put("ModelLabel", "Modèle");
		frenchTranslations.put("MultiCheck", "Multicolore");
		frenchTranslations.put("ColorButton", "Couleur");
		frenchTranslations.put("StartButton", "Commencer");
		frenchTranslations.put("StepLabel", "Étape");
		frenchTranslations.put("StopButton", "Arrêt");
		translations.put("French", frenchTranslations);
	}

	/**
	 * changes the language based on the input language string
	 * 
	 * @param language takes in the language
	 */
	public void changeLanguage(String language) {
		// re-utilized method from A12 like above method
		selectedLanguage = language;

		Map<String, String> selectedTranslations = translations.get(selectedLanguage);

		if (selectedTranslations != null) {
			// Update UI elements with translations
			frame.setTitle(selectedTranslations.get("Title"));
			gameMenu.setText(selectedTranslations.get("GameMenu"));
			langMenu.setText(selectedTranslations.get("LangMenu"));
			helpMenu.setText(selectedTranslations.get("HelpMenu"));
			gameItem1.setText(selectedTranslations.get("NewMenuItem"));
			gameItem2.setText(selectedTranslations.get("SolutionMenuItem"));
			gameItem3.setText(selectedTranslations.get("ExitMenuItem"));
			langItem1.setText(selectedTranslations.get("EnglishMenuItem"));
			langItem2.setText(selectedTranslations.get("FrenchMenuItem"));
			helpItem1.setText(selectedTranslations.get("ColorMenuItem"));
			helpItem2.setText(selectedTranslations.get("AboutMenuItem"));
			randButton.setText(selectedTranslations.get("RandomButton"));
			modelLabel.setText(selectedTranslations.get("ModelLabel"));
			multiCheck.setText(selectedTranslations.get("MultiCheck"));
			colorButton.setText(selectedTranslations.get("ColorButton"));
			startButton.setText(selectedTranslations.get("StartButton"));
			stepLabel.setText(selectedTranslations.get("StepLabel"));
			stopButton.setText(selectedTranslations.get("StopButton"));
		}
	}

	/**
	 * sets the controller instance
	 * 
	 * @param control used for controller instance
	 */
	/*
	 * i could not for the life of me figure out how to get the controller to work
	 * with the view class. originally i was working with JavaFX and got frustrated
	 * after 6 hours of not being able to find the info i wanted so i re-did my
	 * whole project in swing,i dont care if marks get deducted for using ChatGPT on
	 * this method because my program and sanity would not be intact without this
	 * below
	 */
	public void setController(GameController control) {
		this.control = control;
	}
}