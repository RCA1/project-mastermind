package mastermind;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gui.HotSpot;

class Guesses
{
	private List<MasterMindColor> color_master_list;
	
	private List<Guess> player_guesses;
	private Guess secret_guess;

	private java.awt.Image black_button;
	private java.awt.Image white_button;
	
	private boolean is_game_over;
	
	public boolean isPlayerGuessComplete()
	{
		if(player_guesses.size() == 0)
		{
			return true;
		}
		
		Guess last_guess = player_guesses.get(player_guesses.size()-1);
		return last_guess.isFull();
	}
	
	public boolean isGameOver()
	{
		int num_guesses = player_guesses.size();
		if (num_guesses == 0) return false;
		
		Guess last_guess = player_guesses.get(num_guesses - 1);
		
		boolean is_game_over = last_guess.isFull() && (last_guess.isCorrect(secret_guess) || num_guesses == 8);
		return is_game_over;
	}

	public Guesses()  
	{
		is_game_over = false;
		player_guesses = new ArrayList<Guess>();

		gui.ImageLoader il = gui.ImageLoader.getImageLoader();
		black_button = il.getImage("images/black_button.gif");
		black_button = il.getTransparentImage(black_button, java.awt.Color.WHITE);
		white_button = il.getImage("images/white_button.gif");
		white_button = il.getTransparentImage(white_button, java.awt.Color.WHITE);

		java.awt.Image[] guess_color_images = new java.awt.Image[7];
		guess_color_images[0] = il.getImage("images/red_guess.jpg");
		guess_color_images[1] = il.getImage("images/green_guess.jpg");
		guess_color_images[2] = il.getImage("images/blue_guess.jpg");
		guess_color_images[3] = il.getImage("images/purple_guess.jpg");
		guess_color_images[4] = il.getImage("images/yellow_guess.jpg");
		guess_color_images[5] = il.getImage("images/black_guess.jpg");
		guess_color_images[6] = il.getImage("images/white_guess.jpg");

		color_master_list = new ArrayList<MasterMindColor>();

		HotSpot red_hot = new HotSpot(1, 253, 583, 20, 20);
		MasterMindColor red = new MasterMindColor(guess_color_images[0], red_hot);
		color_master_list.add(red);

		HotSpot green_hot = new HotSpot(2, 236, 564, 20, 20);
		MasterMindColor green = new MasterMindColor(guess_color_images[1], green_hot);
		color_master_list.add(green);

		HotSpot blue_hot = new HotSpot(3, 210, 561, 20, 20);
		MasterMindColor blue = new MasterMindColor(guess_color_images[2], blue_hot);
		color_master_list.add(blue);

		HotSpot purple_hot = new HotSpot(4, 185, 570, 20, 20);
		MasterMindColor purple = new MasterMindColor(guess_color_images[3], purple_hot);
		color_master_list.add(purple);

		HotSpot yellow_hot = new HotSpot(5, 165, 588, 20, 20);
		MasterMindColor yellow = new MasterMindColor(guess_color_images[4], yellow_hot);
		color_master_list.add(yellow);

		HotSpot black_hot = new HotSpot(6, 168, 615, 20, 20);
		MasterMindColor black = new MasterMindColor(guess_color_images[5], black_hot);
		color_master_list.add(black);

		HotSpot white_hot = new HotSpot(7, 194, 623, 20, 20);
		MasterMindColor white = new MasterMindColor(guess_color_images[6], white_hot);
		color_master_list.add(white);

		secret_guess = new Guess(1);  //guess_id of 1 for drawing in modal dialog box
		util.Random random = util.Random.getRandomNumberGenerator();
		for (int i = 1; i <= 4; i++)
		{
			int random_int = random.randomInt(1, 7);
			secret_guess.addColor(random_int);
		}
		/*
		System.out.println("*************");
		System.out.println("SECRET CODE:");
		System.out.println("*************");
		System.out.println(secret_guess);
		*/
		
		/*
		secret_guess.addColor(7);
		secret_guess.addColor(7);
		secret_guess.addColor(5);
		secret_guess.addColor(5);
		System.out.println("*************");
		System.out.println("SECRET CODE:");
		System.out.println("*************");
		System.out.println(secret_guess);
		*/

/*
		secret_guess.addColor(1);
		secret_guess.addColor(1);
		secret_guess.addColor(7);
		secret_guess.addColor(4);
		System.out.println("*************");
		System.out.println("SECRET CODE:");
		System.out.println("*************");
		System.out.println(secret_guess);
*/		

		/*
		secret_guess.addColor(7);
		secret_guess.addColor(7);
		secret_guess.addColor(5);
		secret_guess.addColor(5);
		*/

		/*System.out.println("*************");
		System.out.println("SECRET CODE:");
		System.out.println("*************");
		System.out.println("    BLUE");
		System.out.println("    RED");
		System.out.println("    RED");
		System.out.println("    PINK");
		System.out.println("\n");*/
		
	}

	//return a copy
	public Guess getGuess(int guess_num)
	{
		int num_guesses = getNumGuesses();
		if (guess_num <= num_guesses)
		{
			return new Guess(player_guesses.get(guess_num - 1));
		}
		return null;
	}

	//report result when comparing two guesses to each other (first parameter tested against the second parameter)
	public static int[] reportResult(Guess one, Guess two)
	{
		return one.reportResult(two);
	}

	//returns the number of completed guesses
	public int getNumGuesses()
	{
		int num_player_guesses = player_guesses.size();
		if (num_player_guesses == 0) return 0;
		Guess last_guess = player_guesses.get(num_player_guesses - 1);
		if (last_guess.isFull())
		{
			return num_player_guesses;
		}
		else
		{
			return num_player_guesses - 1;
		}
	}

	// STORES 4 GUESSES at ONCE (USED BY AI)
	public void addGuess(Guess ai_guess)
	{
		assert ai_guess.isFull() : "Guess not complete.";
		ai_guess.computeResult(secret_guess);
		player_guesses.add(ai_guess);
	}

	// STORES 1 GUESS AT A TIME (USED BY MANUAL MODE)
	//the current guess and the new color for that guess
	public void addGuess(int guess_color_id)  
	{
		Guess guess;
		int num_player_guesses = player_guesses.size();

		if (num_player_guesses == 0)
		{
			guess = new Guess(1);
			player_guesses.add(guess);
		}
		else
		{
			guess = player_guesses.get(num_player_guesses - 1);  //get the last guess out

			//may need to create a new Guess
			if (guess.isFull() && num_player_guesses <= 7)
			{
				guess = new Guess(num_player_guesses + 1);
				player_guesses.add(guess);
			}
		}

		//add the new color to the guess
		guess.addColor(guess_color_id);
		guess.computeResult(secret_guess);
	}

	public void draw(Graphics g)
	{
		for (Guess player_guess : player_guesses)
		{
			player_guess.draw(g, color_master_list, black_button, white_button);  
		}
	}

	public int isColorSelected(int x, int y)
	{
		boolean select = false;
		int count = 1;
		for (MasterMindColor mm_color : color_master_list)
		{
			select = mm_color.isColorSelected(x, y);
			if (select)
			{
				return count;
			}
			count++;
		}
		return 0;  //no color selected
	}
}
