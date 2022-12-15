package mastermind;

class MasterMindGameOverState implements MasterMindState
{
	private MasterMind mastermind_pointer;

	//**************
	// CONSTRUCTOR
	//**************
	public MasterMindGameOverState(MasterMind mastermind_pointer)
	{
		this.mastermind_pointer = mastermind_pointer;
	}

	public void mouseClicked(int x_click, int y_click)
	{
		System.out.println("\nGAME OVER");

		//return;
	}

}