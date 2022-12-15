package mastermind;

class MasterMindAIRandom implements MasterMindAI
{
	private MasterMind mastermind_pointer;
	private Guess four_guesses_from_player;

	//**************
	// CONSTRUCTOR
	//**************
	public MasterMindAIRandom(MasterMind mastermind_pointer)
	{
		this.mastermind_pointer = mastermind_pointer;
	}

	public Guess nextGuess()
	{
		//System.out.println("INSIDE MasterMindAIRandom");
		int number_of_completed_guesses = mastermind_pointer.getNumGuesses();
		
		//--------------
		// MUST HAVE +1
		//--------------
		four_guesses_from_player = new Guess(number_of_completed_guesses + 1);  //guess_id of 1 for drawing in modal dialog box
		util.Random random = util.Random.getRandomNumberGenerator();
		for (int i = 0; i < 4; i++)
		{
			int random_int = random.randomInt(1, 7);
			four_guesses_from_player.addColor(random_int);
		}

		return four_guesses_from_player;
	}
}