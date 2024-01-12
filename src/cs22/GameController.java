package cs22;
/*
 * Student Name: Aaron Thompson
 * Student Number: 041058950
 * Lab Professor: Daniel Cormier
 * Lab Section: 302
 * Date: 2023-11-05
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * controller class in the MVC design pattern
 */
public class GameController implements ActionListener{
	/**
	 * timer variable
	 */
	Timer timer;
	/**
	 * creating a model object
	 */
	GameModel model = new GameModel();
	/**
	 * creating a view object
	 */
	GameView view = new GameView();
	/**
	 * stores the current step count
	 */
	int currentStep = 0;
	
	
	/**
	 * constructor for controller class
	 * @param model takes in model object
	 * @param view takes in view object
	 */
	public GameController(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
	}

	/** trying to get rid of this-escape warning with this method
	 * @param cont takes in controller object
	 */
	public void initCont(GameController cont){
		view.setController(cont);
	}
	/**
	 * launches the GUI
	 */
	public void launch() {
		initCont(this);
		view.show();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		switch(actionCommand) {
		
		case "boardClickAction":
			// to put it simply, this shit clicks
			try {
				// put this in to stop grid iteration if user randomly clicks board while its iterating
				timer.stop();
			}
			catch(NullPointerException error) {
				
			}
			// just flip the state of the cell at x, y
			model.setCell(view.getXCoord(), view.getYCoord());
			for(int x = 0; x<model.getRow(); x++) {
    			for(int y = 0; y<model.getCol(); y++) {
    				// PAINTTTTTTTTTT YES SIR!!!!!!!!!!!!
			view.updateBoard(model.board, model.countNeighbours(model.board, x, y), x, y);
    			}
			}
			break;
		
		case "Random":
			//randomly initalize the board 
			model.randomInit();
			for(int x = 0; x<model.getRow(); x++) {
    			for(int y = 0; y<model.getCol(); y++) {
    				// paint based on model classes board
			view.updateBoard(model.board, model.countNeighbours(model.board, x, y), x, y);
    			}
			}
			break;
			
		case "Start":
		    try {// validate model is not nonsense garbage
		    	String result = view.modelValidation();
		    	if (!result.matches("[01]{18}")) {
		    		return;
		    	}
		    	else {
		        model.gameRules(view.modelValidation());
		    	}
		    } catch (NullPointerException error) {
		    	return;
		    }

		    int numberOfSteps = view.stringToInt(view.getSteps());
		    int delay = 500; 
		    currentStep = 0;
		    // stackoverflow was the brain child behind the delay in the beast of code below
		    timer = new Timer(delay, new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            if (currentStep < numberOfSteps) {
		            	// needed to create 2 nested for loops for the multicolor, couldnt find another way to do it
		            	// start of inefficiency called "code"
		            	for(int x = 0; x<model.getRow(); x++) {
		        			for(int y = 0; y<model.getCol(); y++) {
		        				// calculate the next gen of the board and store in temp board
		        				model.calcCellRules(model.countNeighbours(model.board, x, y), model.board[x][y], x, y);		        				
		        			}
		            	}
		            	// the ending of the inefficiency
		            	for(int x = 0; x<model.getRow(); x++) {
		        			for(int y = 0; y<model.getCol(); y++) {
		        				// we check the neighbours of the next gen to do multicolors, otherwise its just extra for no reason
		        				view.updateBoard(model.temp, model.countNeighbours(model.temp, x,y), x, y);
		        			}
		            	}
		            	// set model to next gen temp board
		                model.updateBoard();
		                currentStep++;
		            } else {
		                ((Timer) e.getSource()).stop(); 
		            }
		        }
		    });
		 // Start the timer
		    timer.start(); 
		    break;
		    
		case "Stop":
			// this ones difficult to see what its doing, definitely not stopping the program <0.0>
			try {
			timer.stop();
			}
			catch(NullPointerException error) {
				
			}
			break;
			
		case "newBoard":
			// just run init board method and update board color, everything is false so white
			model.initBoard(model.getRow(), model.getCol());
			for(int x = 0; x<model.getRow(); x++) {
    			for(int y = 0; y<model.getCol(); y++) {
			view.updateBoard(model.board, model.countNeighbours(model.board,x, y), x, y);
    			}
			}
			break;
		case "colorMenu":
			// calls the JColorChooser to let user change color then updates the board with the color
			view.setColor();
			for(int x = 0; x<model.getRow(); x++) {
    			for(int y = 0; y<model.getCol(); y++) {
			view.updateBoard(model.board, model.countNeighbours(model.board,x, y), x, y);
    			}
			}
			break;
			
		
			
		case "solution":
			// show solution based on the steps provided by user, no delay needed since we just show the solution
			 try {
			    	String result = view.modelValidation();
			    	if (!result.matches("[01]{18}")) {
			    		return;
			    	}
			    	else {
			        model.gameRules(view.modelValidation());
			    	}
			    } catch (NullPointerException error) {
			    	return;
			    }
			 for(int i = 0; i < view.stringToInt(view.getSteps()); i++) {
				 for(int x = 0; x<model.getRow(); x++) {
	        			for(int y = 0; y<model.getCol(); y++) {
			 model.calcCellRules(model.countNeighbours(model.board,x, y), model.board[x][y], x, y);
	        			}
				 }
				 for(int x = 0; x<model.getRow(); x++) {
	        			for(int y = 0; y<model.getCol(); y++) {
				 view.updateBoard(model.temp, model.countNeighbours(model.temp,x, y), x, y);
	        			}
				 }
				 model.updateBoard();
             }
			break;
			
		case "english":
			//changes language to arabic
			view.changeLanguage("English");
			break;
			
		case "french":
			//changes language to chinese
			view.changeLanguage("French");
			break;
			
		case "about":
			// if user clicks about menu option brings up "About the game of life" dialog
			view.showAboutDialog();
			break;
			
		case "exit":
			// exits program through menu button "Exit"
			view.closeFrame();
			break;
		}
	}
	
}
