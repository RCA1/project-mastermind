//**************************************
// TO COMPILE, TYPE:
// build DRIVE_LETTER 0 for Manual mode
// OR
// build DRIVER_LETTER 1 for AI mode
//**************************************
package mastermind;

public class MasterMindDriver
{
	//entry point
	public static void main(String[] args)
	{
		MasterMind mm = new MasterMind();

		if (args[0].equals("1") && args.length > 0)
		{
			System.out.println("Starting mode: AI (Random)");
			mm.changeState(mm.getMasterMindAIState());
		}
		else
		{
			// else just stay in Manual mode which is default
			System.out.println("Starting mode: MANUAL");
		}
	}
}
