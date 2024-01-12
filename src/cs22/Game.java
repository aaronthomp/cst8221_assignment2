package cs22;
/*
 * Student Name: Aaron Thompson
 * Student Number: 041058950
 * Lab Professor: Daniel Cormier
 * Lab Section: 302
 * Date: 2023-11-05
 * 
 */


/**
 * class contains the main method
 */
public class Game {
	/**
	 * main method executes all instructions
	 * @param args takes in arguments
	 */
	public static void main(String[] args) {
        GameModel model = new GameModel(); // Set the dimensions of the grid
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
        controller.launch();
    }
}
