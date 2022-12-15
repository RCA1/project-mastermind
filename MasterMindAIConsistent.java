package mastermind;

class MasterMindAIConsistent implements MasterMindAI
{
	//--------------------------------------------------------------------
	// IF THIS VALUE REACHES THE SAME SIZE OF number_of_completed_guesses
	// THEN THAT MEANS THE NEW RANDOMLY GENERATED NUMBER IS CONSISTENT
	// WITH ALL PREVIOUSLY MADE GUESSES
	//--------------------------------------------------------------------
	private int counter_to_check_if_all_passed_consistency;

	private MasterMind mastermind_pointer;
	private Guess four_randomly_generated_guesses;

	//**************
	// CONSTRUCTOR
	//**************
	public MasterMindAIConsistent(MasterMind mastermind_pointer)
	{
		this.mastermind_pointer = mastermind_pointer;
	}

	public Guess nextGuess()
	{
		int number_of_completed_guesses = mastermind_pointer.getNumGuesses();
		
		//--------------
		// MUST HAVE +1
		//--------------
		four_randomly_generated_guesses = new Guess(number_of_completed_guesses + 1);  //guess_id of 1 for drawing in modal dialog box

		//-------------------------------------------------------------
		// IF THE USER CHOSE MasterMindAIConsistent AT THE VERY START,
		// THIS CODE GETS EXECUTED INSTEAD OF THE OTHER ONE BELOW
		//-------------------------------------------------------------
		if (number_of_completed_guesses == 0)
		{
			util.Random random = util.Random.getRandomNumberGenerator();
			for (int i = 0; i < 4; i++)
			{
				int random_int = random.randomInt(1, 7);
				four_randomly_generated_guesses.addColor(random_int);
			}

			return four_randomly_generated_guesses;
		}

		//-----------------------------------------------------------------------------
		// THIS VARIABLE DETERMINES IF THE NEW RANDOMLY GENERATED GUESS IS CONSISTENT.
		// IF IT TURNS TRUE, THEN four_randomly_generated_guesses CAN BE RETURNED TO
		// WHERE THIS METHOD WAS CALLED
		//-----------------------------------------------------------------------------
		boolean new_random_guess_is_consistent = false;

		//if (number_of_completed_guesses > 0)
		//{ 
			while (new_random_guess_is_consistent == false)
			{
				//---------------------------------------
				// RE-INITIALIZE WHEN MAKING A NEW GUESS
				//---------------------------------------
				four_randomly_generated_guesses = new Guess(number_of_completed_guesses + 1);
				
				//--------------------------------------
				// GENERATE A NEW SET OF RANDOM GUESSES
				//--------------------------------------
				util.Random random = util.Random.getRandomNumberGenerator();
				for (int i = 0; i < 4; i++)
				{
					int random_int = random.randomInt(1, 7);
					four_randomly_generated_guesses.addColor(random_int);
				}
				
				//System.out.println(four_randomly_generated_guesses);

				//----------------------------------
				// START AT 0 OR RESET COUNTER TO 0
				//----------------------------------
				counter_to_check_if_all_passed_consistency = 0;

				//-------------------------------------------------------------------
				// THIS FOR-LOOP COMPARES THE NEW RANDOMLY GENERATED SET OF NUMBERS
				// TO CHECK IF IT IS CONSISTENT WITH ALL THE PREVIOUS GUESSES
				//-------------------------------------------------------------------
				for (int j = 1; j <= number_of_completed_guesses; j++)
				{
					//--------------------------------------------------------
					// GET THE PREVIOUS GUESSES STARTING WITH THE FIRST GUESS
					//--------------------------------------------------------
					Guess previous_four_guesses = mastermind_pointer.getGuess(j);

					//-----------------------------------------------------
					// GET NUMBER OF WHITE PEGS FROM previous_four_guesses
					//-----------------------------------------------------
					int number_of_white_pegs_from_previous = previous_four_guesses.getNumWhite();
					
					//-----------------------------------------------------
					// GET NUMBER OF BLACK PEGS FROM previous_four_guesses
					//-----------------------------------------------------
					int number_of_black_pegs_from_previous = previous_four_guesses.getNumBlack();

					//-------------------------------------------------------------
					// GET THE NUMBER OF BLACK AND WHITE PEGS FROM THIS GUESS WHEN
					// COMPARED WITH four_randomly_generated_guesses
					//-------------------------------------------------------------
					int[] array_of_black_and_white_pegs = new int[2];
					array_of_black_and_white_pegs = mastermind_pointer.reportTestResult(four_randomly_generated_guesses, previous_four_guesses);

					//-----------------------------------------------------------
					// STORE WHITE AND BLACK PEGS IN THEIR OWN VARIABLES JUST TO
					// MAKE THINGS A LITTLE CLEARER
					//-----------------------------------------------------------
					int number_of_white_pegs_from_new_random = array_of_black_and_white_pegs[1];
					int number_of_black_pegs_from_new_random = array_of_black_and_white_pegs[0];
					
					//-------------------------------------------------------------------
					// MAKE SURE four_randomly_generated_guesses HAS THE SAME AMOUNT OF
					// WHITE AND BLACK PEGS AS previous_four_guesses
					//-------------------------------------------------------------------
					/*if ((number_of_white_pegs_from_previous != number_of_white_pegs_from_new_random) && (number_of_black_pegs_from_previous != number_of_black_pegs_from_new_random))
					{
						//System.out.println(number_of_white_pegs_from_previous + " VS " + number_of_white_pegs_from_new_random + "    AND    " + number_of_black_pegs_from_previous + " VS " + number_of_black_pegs_from_new_random);
						//System.out.println("NO MATCH!\n");

						//----------------------------------------------------------
						// IF THERE IS NO MATCH, THEN BREAK OUT OF THE FOR LOOP AND
						// GENERATE A NEW SET OF RANDOM NUMBERS
						//----------------------------------------------------------
						break;
					}*/
					
					if((number_of_white_pegs_from_previous == number_of_white_pegs_from_new_random) && (number_of_black_pegs_from_previous == number_of_black_pegs_from_new_random))
					{
						//--------------------------------------------------------------------
						// IF THIS VALUE REACHES THE SAME SIZE OF number_of_completed_guesses
						// THEN THAT MEANS THE NEW RANDOMLY GENERATED NUMBER IS CONSISTENT
						// WITH ALL PREVIOUSLY MADE GUESSES
						//--------------------------------------------------------------------
						counter_to_check_if_all_passed_consistency++;

						//System.out.println(number_of_white_pegs_from_previous + " VS " + number_of_white_pegs_from_new_random + "    AND    " + number_of_black_pegs_from_previous + " VS " + number_of_black_pegs_from_new_random);
						//System.out.println("IT'S A MATCH!\n");
					}

					//----------------------------------------------------------------------------
					// WHEN THE COUNTER REACHES THE SAME VALUE AS THE NUMBER OF COMPLETED GUESSES
					// THAT MEANS THE NEW RANDOMLY GENERATED SET OF NUMBERS IS CONSISTED WITH
					// ALL THE PREVIOUS GUESSES. SO THIS IS THE SET OF RANDOM NUMBERS THAT GETS
					// RETURNED
					//----------------------------------------------------------------------------
					if (counter_to_check_if_all_passed_consistency == number_of_completed_guesses)
					{
						new_random_guess_is_consistent = true;
					}
				}
			}
		//}

		return four_randomly_generated_guesses;
	}
}