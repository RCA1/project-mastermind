package mastermind;

import java.util.ArrayList;
import java.util.List;

class MasterMindAIMiniMax implements MasterMindAI
{
	private MasterMind mastermind_pointer;
	private Guess four_randomly_generated_guesses;
	private Guess possible_guesses;
	private ArrayList<Guess> array_of_all_2401_combinations; // THIS SHOULD NOT REDUCE IN SIZE!!
	private ArrayList<Guess> array_of_possible_guesses; // THIS IS THE ONE THAT GETS REDUCED IN SIZE
	private int index_I_want_for_next_guess;
	private int index_to_return;
	private int counter_to_check_if_all_passed_consistency;

	public MasterMindAIMiniMax(MasterMind mastermind_pointer)
	{
		index_I_want_for_next_guess = 0;
		index_to_return = 0;
		this.mastermind_pointer = mastermind_pointer;
		array_of_all_2401_combinations = new ArrayList<Guess>();
		array_of_possible_guesses = new ArrayList<Guess>();

		//---------------------------------------------------
		// PUT ALL 2401 POSSIBLE COMBINATIONS INTO AN ARRAY.
		// THIS ARRAY SHOULD NOT BE REDUCED
		//---------------------------------------------------
		for (int first = 1; first <= 7; first++)
		{
			for (int second = 1; second <= 7; second++)
			{	
				for (int third = 1; third <= 7; third++)
				{
					for (int fourth = 1; fourth <= 7; fourth++)
					{
						possible_guesses = new Guess(1);

						// ADD INDIVIDUAL NUMBERS INTO A LIST OF 4 NUMBERS
						possible_guesses.addColor(first);
						possible_guesses.addColor(second);
						possible_guesses.addColor(third);
						possible_guesses.addColor(fourth);

						// ADD INDIVIDUAL COMBINATION TO ARRAY OF COMBINATIONS
						array_of_all_2401_combinations.add(possible_guesses);
					}
				}
			}
		}
		
		//-----------------------------------------------------------------
		// CREATE ANOTHER ARRAY AND PUT ALL THE 2401 POSSIBLE COMBINATIONS
		// IN THERE AGAIN. THIS SECOND ARRAY WILL BE THE ONE THAT GETS
		// REDUCED IN SIZE
		//-----------------------------------------------------------------
		for (int first = 1; first <= 7; first++)
		{
			for (int second = 1; second <= 7; second++)
			{	
				for (int third = 1; third <= 7; third++)
				{
					for (int fourth = 1; fourth <= 7; fourth++)
					{
						possible_guesses = new Guess(1);

						// ADD INDIVIDUAL NUMBERS INTO A LIST OF 4 NUMBERS
						possible_guesses.addColor(first);
						possible_guesses.addColor(second);
						possible_guesses.addColor(third);
						possible_guesses.addColor(fourth);

						// ADD INDIVIDUAL COMBINATION TO ARRAY OF COMBINATIONS
						array_of_possible_guesses.add(possible_guesses);
					}
				}
			}
		}
	}

	public Guess nextGuess()
	{
		boolean four_blacks_found = false;
		int four_blacks_found_in_this_index = 0;
		int smallest_counter = 10000000;
		int largest_minimum = 0;
		boolean first_time = true;
		int number_of_completed_guesses = mastermind_pointer.getNumGuesses();
		four_randomly_generated_guesses = new Guess(number_of_completed_guesses + 1);  //guess_id of 1 for drawing in modal dialog box

		if (number_of_completed_guesses == 0)
		{
			System.out.println("\n\nPlease wait...");
		}

		//**************************************************************************************************************************************
		//
		// THIS IS THE REMOVING PART. ONLY REMOVES AFTER THERE IS 1 OR MORE number_of_completed_guesses
		//
		//**************************************************************************************************************************************
		System.out.println("\n---------------- GUESS FOR ROW " + (number_of_completed_guesses+1) + " ----------------");
		if (number_of_completed_guesses > 0)
		{
			//------------------------------------------------------
			// GO THROUGH ALL OF WHAT'S LEFT IN THE SHRINKING LIST
			//------------------------------------------------------
			for (int e = 0; e < array_of_possible_guesses.size(); e++)
			{
				//----------------------------------
				// START AT 0 OR RESET COUNTER TO 0
				//----------------------------------
				counter_to_check_if_all_passed_consistency = 0;

				//-------------------------------------------------------------------
				// THIS FOR-LOOP COMPARES THE COMBINATION FROM THE SHRINKING LIST TO
				// CHECK IF IT IS CONSISTENT WITH ALL THE PREVIOUS GUESSES.
				// IF IT IS NOT CONSISTENT, REMOVE IT FROM THE SHRINKING LIST
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
					array_of_black_and_white_pegs = mastermind_pointer.reportTestResult(array_of_possible_guesses.get(e), previous_four_guesses);
					//array_of_black_and_white_pegs = mastermind_pointer.reportTestResult(previous_four_guesses, array_of_possible_guesses.get(e));

					//-----------------------------------------------------------
					// STORE WHITE AND BLACK PEGS IN THEIR OWN VARIABLES JUST TO
					// MAKE THINGS A LITTLE CLEARER
					//-----------------------------------------------------------
					int number_of_white_pegs_from_next_combination = array_of_black_and_white_pegs[1];
					int number_of_black_pegs_from_next_combination = array_of_black_and_white_pegs[0];

					if((number_of_white_pegs_from_previous == number_of_white_pegs_from_next_combination) && (number_of_black_pegs_from_previous == number_of_black_pegs_from_next_combination))
					{
						//--------------------------------------------------------------------
						// IF THIS VALUE REACHES THE SAME SIZE OF number_of_completed_guesses
						// THEN THAT MEANS THE NEW RANDOMLY GENERATED NUMBER IS CONSISTENT
						// WITH ALL PREVIOUSLY MADE GUESSES
						//--------------------------------------------------------------------
						counter_to_check_if_all_passed_consistency++;
					}
				}

				//---------------------------------------------------------------------
				// IF THE COMBINATION IS NOT CONSISTENT WITH ALL THE PREVIOUS GUESSES,
				// REMOVE IT FROM THE SHRINKING LIST
				//---------------------------------------------------------------------
				if (counter_to_check_if_all_passed_consistency != number_of_completed_guesses)
				{
					array_of_possible_guesses.remove(e);
					e--;
				}
			}
		}

		//**************************************************************************************************************************************
		//
		// IF THE SIZE OF THE ARRAY ENDS UP WITH 2 OR LESS, THEN JUST RETURN THE NEXT ONE IN INDEX 0 AS THE FINAL GUESS
		// BUT CHECK IF == 1 FIRST
		//
		//**************************************************************************************************************************************
		if (array_of_possible_guesses.size() == 1)
		{
			Guess next_guess_unconverted = array_of_possible_guesses.get(0);
			List<Integer> next_guess_converted = next_guess_unconverted.getGuessColorIDs();
			four_randomly_generated_guesses.addColor(next_guess_converted.get(0));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(1));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(2));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(3));

			System.out.println("AI'S GUESS: " + four_randomly_generated_guesses);
			//System.out.println("# REMAINING COMBINATIONS: " + array_of_possible_guesses.size());
			return four_randomly_generated_guesses;
		}
		else if (array_of_possible_guesses.size() <= 2)
		{
			Guess next_guess_unconverted = array_of_possible_guesses.get(0);
			List<Integer> next_guess_converted = next_guess_unconverted.getGuessColorIDs();
			four_randomly_generated_guesses.addColor(next_guess_converted.get(0));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(1));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(2));
			four_randomly_generated_guesses.addColor(next_guess_converted.get(3));
			array_of_possible_guesses.remove(0);

			System.out.println("AI'S GUESS: " + four_randomly_generated_guesses);
			//System.out.println("# REMAINING COMBINATIONS: " + array_of_possible_guesses.size());
			return four_randomly_generated_guesses;
		}

		//**************************************************************************************************************************************
		//
		//
		// "MINI MAX" PART OF THE PROGRAM
		//
		//
		//**************************************************************************************************************************************

		//--------------------------------------------------------------------------
		// LOOP OVER ALL THE POSSIBLE GUESSES (THE ARRAY THAT DOES NOT GET REDUCED)
		//--------------------------------------------------------------------------
		for (int i = 0; i < array_of_all_2401_combinations.size(); i++)
		{
			//---------------------------------------------------------------------
			// GET INDIVIDUAL GUESS COMBINATIONS FROM THE ARRAY CORRESPONDING WITH
			// THE INDEX NUMBER
			//---------------------------------------------------------------------
			Guess guess_from_2401 = array_of_all_2401_combinations.get(i);

			//------------------------------
			// NEED THIS FOR THE NEXT PART
			//------------------------------
			int[] array_of_counters = new int[15];
			// INITIALIZE ALL COUNTERS TO 0
			for (int a = 0; a < 15; a++)
			{
				array_of_counters[a] = 0;
			}

			//-------------------------------
			// LOOP OVER THE SHRINKING ARRAY
			//-------------------------------
			for( int j = 0; j < array_of_possible_guesses.size(); j++)
			{
				//----------------------------------------------------------------
				// COMPARE AND GET THE NUMBER OF BLACK AND WHITE PEGS THAT RESULT 
				// FROM THE COMPARISON
				//----------------------------------------------------------------
				int[] array_of_black_and_white_pegs = new int[2];
				array_of_black_and_white_pegs = mastermind_pointer.reportTestResult(guess_from_2401, array_of_possible_guesses.get(j));

				//-----------------------------------------------------------
				// STORE WHITE AND BLACK PEGS IN THEIR OWN VARIABLES JUST TO
				// MAKE THINGS A LITTLE CLEARER
				//-----------------------------------------------------------
				int num_white_pegs_from_result = array_of_black_and_white_pegs[1];
				int num_black_pegs_from_result = array_of_black_and_white_pegs[0];

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES - - - -
				//--------------------------------------
				if(num_white_pegs_from_result != 0 || num_black_pegs_from_result != 0)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[0] = array_of_counters[0] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES W - - -
				//--------------------------------------
				if(num_white_pegs_from_result != 1 || num_black_pegs_from_result != 0)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[1] = array_of_counters[1] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES W W - -
				//--------------------------------------
				if(num_white_pegs_from_result != 2 || num_black_pegs_from_result != 0)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[2] = array_of_counters[2] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES W W W -
				//--------------------------------------
				if(num_white_pegs_from_result != 3 || num_black_pegs_from_result != 0)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[3] = array_of_counters[3] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES W W W W
				//--------------------------------------
				if(num_white_pegs_from_result != 4 || num_black_pegs_from_result != 0)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[4] = array_of_counters[4] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B - - -
				//--------------------------------------
				if(num_white_pegs_from_result != 0 || num_black_pegs_from_result != 1)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[5] = array_of_counters[5] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B W - -
				//--------------------------------------
				if(num_white_pegs_from_result != 1 || num_black_pegs_from_result != 1)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[6] = array_of_counters[6] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B W W -
				//--------------------------------------
				if(num_white_pegs_from_result != 2 || num_black_pegs_from_result != 1)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[7] = array_of_counters[7] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B W W W
				//--------------------------------------
				if(num_white_pegs_from_result != 3 || num_black_pegs_from_result != 1)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[8] = array_of_counters[8] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B - -
				//--------------------------------------
				if(num_white_pegs_from_result != 0 || num_black_pegs_from_result != 2)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[9] = array_of_counters[9] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B W -
				//--------------------------------------
				if(num_white_pegs_from_result != 1 || num_black_pegs_from_result != 2)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[10] = array_of_counters[10] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B W W
				//--------------------------------------
				if(num_white_pegs_from_result != 2 || num_black_pegs_from_result != 2)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[11] = array_of_counters[11] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B B -
				//--------------------------------------
				if(num_white_pegs_from_result != 0 || num_black_pegs_from_result != 3)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[12] = array_of_counters[12] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B B W
				//--------------------------------------
				if(num_white_pegs_from_result != 1 || num_black_pegs_from_result != 3)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[13] = array_of_counters[13] + 1;
				}

				//--------------------------------------
				// CHECK IF THE RESULT MATCHES B B B B
				//--------------------------------------
				if(num_white_pegs_from_result != 0 || num_black_pegs_from_result != 4)
				{
					// INCREMENT COUNTER INSIDE ARRAY
					array_of_counters[14] = array_of_counters[14] + 1;
				}
			}

			//-------------------------------------------------------------
			// KEEP TRACK OF THE SMALLEST COUNTER OUT OF THESE 15 COUNTERS
			//-------------------------------------------------------------
			// MAKE THE FIRST ONE BE THE SMALLEST AND COMPARE WITH THE
			// REST OF THE OTHER COUNTERS IN THE ARRAY
			smallest_counter = array_of_counters[0];
			for (int x = 1; x < 15; x++)
			{
				// IF THE NEXT ONE IS SMALLER, UPDATE THAT TO BE THE SMALLEST
				if (array_of_counters[x] < smallest_counter)// && array_of_counters[x] != 0)
				{
					smallest_counter = array_of_counters[x];
				}
			}

			// KEEP TRACK OF THE LARGEST MINIMUM COUNTER
			if (first_time == true)
			{
				largest_minimum = smallest_counter;
				first_time = false;
			}

			if (smallest_counter > largest_minimum)
			{
				largest_minimum = smallest_counter;
				index_I_want_for_next_guess = i;
				//System.out.println("Guessing (" + guess_from_2401 + ") can eliminate (" + largest_minimum + ") combinations");
			}
		}

		//--------------------------------------------------------------------
		// SINCE I HAVE "1" INSIDE new Guess(1) IN THE CONSTRUCTOR, I HAVE TO
		// CONVERT THEM TO INTEGERS AND RE-ADD TO THE NEW Guess VARIABLE
		// OR ELSE IT WILL ALWAYS ADD TO ROW #1
		//--------------------------------------------------------------------
		Guess next_guess_unconverted = array_of_all_2401_combinations.get(index_I_want_for_next_guess);
		List<Integer> next_guess_converted = next_guess_unconverted.getGuessColorIDs();
		four_randomly_generated_guesses.addColor(next_guess_converted.get(0));
		four_randomly_generated_guesses.addColor(next_guess_converted.get(1));
		four_randomly_generated_guesses.addColor(next_guess_converted.get(2));
		four_randomly_generated_guesses.addColor(next_guess_converted.get(3));

		System.out.println("AI'S GUESS: " + four_randomly_generated_guesses);
		//System.out.println("# REMAINING COMBINATIONS: " + array_of_possible_guesses.size());

		return four_randomly_generated_guesses;
	}
}