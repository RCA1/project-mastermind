package mastermind;

class MasterMindAIState implements MasterMindState
{	
	//----------------------------------------------
	// MasterMindAIState ALSO STORES THE DIFFERENT 
	// AIs IN AN ARRAY OF TYPE MasterMindAI
	//----------------------------------------------
	private MasterMindAI[] array_of_AIs;
	private MasterMind mastermind_pointer;
	private MasterMindAI current_AI;

	//**************
	// CONSTRUCTOR
	//**************
	public MasterMindAIState(MasterMind mastermind_pointer)
	{
		this.mastermind_pointer = mastermind_pointer;
		array_of_AIs = new MasterMindAI[3];

		//---------------------------------
		// ADD THE 3 AI MODES IN THE ARRAY
		//---------------------------------
		array_of_AIs[0] = new MasterMindAIRandom(mastermind_pointer);
		array_of_AIs[1] = new MasterMindAIConsistent(mastermind_pointer);
		array_of_AIs[2] = new MasterMindAIMiniMax(mastermind_pointer);

		//-------------------------------
		// LET MasterMindAIRandom BE THE
		// DEFAULT AI MODE
		//-------------------------------
		current_AI = array_of_AIs[0];
	}

	public void mouseClicked(int x_click, int y_click)
	{
		//-----------------------------------------
		// CHECK IF THE USER CLICKED 0, 1, 2, OR 3
		//-----------------------------------------
		int number_choice = mastermind_pointer.changeAI(x_click, y_click);

		//----------------------------------------------
		// IF THE PLAYER WANTS TO SWITCH BACK TO MANUAL
		//----------------------------------------------
		if (number_choice == 0)
		{
			System.out.println("CURRENT MODE: MANUAL");
			mastermind_pointer.changeState(mastermind_pointer.getMasterMindPlayerState());
			return;
		}

		//-----------------------------------------------
		// IF 1 IS CLICKED, SWITCH TO MasterMindAIRandom.
		// ONLY CHANGE IF IT'S NOT ALREADY SET
		//-----------------------------------------------
		if (number_choice == 1 && current_AI != array_of_AIs[0])
		{
			System.out.println("CURRENT MODE: AI RANDOM");
			current_AI = array_of_AIs[0];
		}

		//---------------------------------------------------
		// IF 2 IS CLICKED, SWITCH TO MasterMindAIConsistent
		// ONLY CHANGE IF IT'S NOT ALREADY SET
		//---------------------------------------------------
		if (number_choice == 2 && current_AI != array_of_AIs[1])
		{
			System.out.println("CURRENT MODE: AI CONSISTENT");
			current_AI = array_of_AIs[1];
		}

		//------------------------------------------------
		// IF 2 IS CLICKED, SWITCH TO MasterMindAIMiniMax
		// ONLY CHANGE IF IT'S NOT ALREADY SET
		//------------------------------------------------
		if (number_choice == 3)
		{
			System.out.println("CURRENT MODE: AI MINI MAX");
			current_AI = array_of_AIs[2];
		}

		//--------------------------------------------
		// GET THE NEXT GUESS FROM THE APPROPRIATE AI.
		// ADDED THE IF STATEMENT SO THAT CLICKING
		// ON THE NUMBERS DOES NOT AUTOMATICALLY MAKE
		// ANOTHER GUESS.
		//--------------------------------------------
		if (number_choice != 0 && number_choice != 1 && number_choice != 2 && number_choice != 3)
		{
			Guess guesses_made_by_AI = current_AI.nextGuess();
			mastermind_pointer.addGuess(guesses_made_by_AI);
		}
	}

}