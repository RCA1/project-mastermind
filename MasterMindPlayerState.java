package mastermind;

class MasterMindPlayerState implements MasterMindState
{
	private MasterMind mastermind_pointer;

	//**************
	// CONSTRUCTOR
	//**************
	public MasterMindPlayerState(MasterMind mastermind_pointer)
	{
		this.mastermind_pointer = mastermind_pointer;
	}

	//------------------------------------------------------------------
	// MOUSE CLICKS ARE EXAMINED TO SEE IF A COLOR HAS BEEN CLICKED ON.
	// IF SO, THE COLOR IS ADDED TO THE GUESS
	//------------------------------------------------------------------
	public void mouseClicked(int x_click, int y_click)
	{
		//------------------------------------------
		// CHECK IF ONE OF THE 7 COLORS WAS CLICKED
		//------------------------------------------
		int is_color_selected = mastermind_pointer.isColorSelected(x_click, y_click);

		//--------------------------------------------------------------
		// IF A COLOR WAS CLICKED, THEN THE COLOR IS ADDED TO THE GUESS
		//--------------------------------------------------------------
		if (is_color_selected > 0)
		{
			mastermind_pointer.addGuess(is_color_selected);
		}

		//------------------------------------------------------------
		// IF THE HUMAN PLAYER IS NOT IN THE MIDDLE OF SELECTING FOUR
		// COLORS FOR A Guess, THE MOUSE CLICK IS CHECKED TO SEE IF
		// THE CURRENT STATE SHOULD BE SWITCHED MasterMindAIState.
		//------------------------------------------------------------
		if (mastermind_pointer.isPlayerGuessComplete() == true)
		{
			//-----------------------------------------
			// CHECK IF THE USER CLICKED 0, 1, 2, OR 3
			//-----------------------------------------
			int number_choice = mastermind_pointer.changeAI(x_click, y_click);

			//--------------------------------------------------------------------
			// IF A NUMBER--1, 2, or 3-- WAS CLICKED, CHANGE TO MasterMindAIState
			//--------------------------------------------------------------------
			if (number_choice > 0)
			{
				//-----------------------------
				// CHANGE TO MasterMindAIState
				//-----------------------------
				System.out.println("STATE CHANGED TO: MasterMindAIState");
				//System.out.println("CURRENT DEFAULT MODE: AI RANDOM");
				mastermind_pointer.changeState(mastermind_pointer.getMasterMindAIState());
				

				/****************************************************************************
				 *	I HAVE TO CLICK THE AI MODE TWICE IF I CHOOSE AN AI MODE AT THE 
				 *	VERY START OF THE GAME.
				 *
				 *	BUT IF I UNCOMMENT THIS LINE OF CODE, IT WORKS BY CLICKING JUST ONCE
				 ****************************************************************************/
				// GO BACK TO THE mouseClicked NETHOD IN MasterMind.java AND SINCE THE STATE
				// HAS BEEN CHANGED, YOU WON'T NEED TO CLICK AGAIN. IT WILL SWITCH
				// TO THE AI THAT YOU CLICKED
				int number_of_completed_guesses = mastermind_pointer.getNumGuesses();
				if (number_of_completed_guesses == 0)
				{
					mastermind_pointer.mouseClicked(x_click, y_click);
				}
				
			}
		}
	}
}