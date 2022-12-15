<!-- # mastermind -->



# Master Mind
Master Mind is a code breaking game. A secret code is selected by one player and another player tries to guess the code. The secret code has four positions and each position is assigned a color out of seven possible colors. It is legal to have multiple positions assigned the same color. In this version of Master Mind, the computer selects the secret code randomly.
To win the game, the player must determine the correct position and color for each position of the randomly generated secret code. A guess is composed of selecting a color for each of four positions. After each guess, feedback is obtained which is used to improve the guess until an exact match of each color and position of the secret code is obtained. The secret code must be obtained in eight or fewer guesses.
Feedback is determined as follows:
> A black "button" is awarded for each of the four guess elements that matches both color and position of the secret code.
> 
> A white "button" is awarded for each of the four guess elements that matches a color but is not in the correct position.
> 
> A guess element can be awarded at most one button (may be tricky).
It is likely that a given guess will have fewer than four (and maybe zero) buttons awarded.

# Guess

A Guess is composed of a guess id (from 1 to 8, representing the numerical ordering of the Guess) and a List<Integer> containing four color ids. The four color ids represent the player's attempt at deciphering the secret code with that Guess. Thus, each color in a Guess is stored as an integer (red = 1...white = 7). The index of the color id in the List corresponds to the position of that color in the player's Guess. For example, if the Guess is red in position 1, black in position 2, black in position 3, and red in position 4, then the List contains 1, 6, 6, 1. Note that the secret code is also a Guess.
The reportResult method compares two Guesses (one of which may be the secret code, but frequently will not be) and determines the number of black and white buttons to assign to the Guess. Complete this method to compute the number of white buttons (computing the black buttons is easy and has been done for you). You will need to remove elements from the Lists (copies of the Lists have been made for this reason) so that you don't assign too many white buttons. This method is essential not only for evaluating the player's Guess as compared to the secret code, but also to compare arbitrary Guesses to one another for the AI to find a reasonable next Guess to make.
The computeResult method calls the reportResult method when a comparison to the secret code is required. If the player is a human, the Guess may contain fewer than 4 color ids as the human player may not have completed their Guess, so an isFull() check is required. If the player is a computer, it is assumed that the Guess is complete.
A Guess also contains an integer array of size two containing the number of black buttons and white buttons assigned to a valid and completed Guess when compared with the secret code. The contents of this array are set in computeResult following the comparison to the secret code.
You should have a playable game after completing the Guess class.
  
# MasterMindAI: Strategy Design Pattern
A MasterMind computer player must, at a minimum, return a valid Guess:
```
public interface MasterMindAI
{
   public Guess nextGuess();
}
```

# MasterMindAIRandom
The simplest way to implement the interface is to populate the List of color ids with four random integers from 1 to 7 and to return the associated Guess. This is actually a useful class as it allows you to find bugs related to incorporating the AI into the MasterMind game rather than bugs in the AI itself.

# MasterMindState: State Design Pattern
You need to be able to switch back and forth between a human player and a computer player. This is readily accomplished using the MasterMindState interface:
  ```
public interface MasterMindState
{
   public void mouseClicked(int x_click, int y_click);
}
```

The four integers (0, 1, 2, 3) at the bottom right of the GUI allow the user to switch between human player mode (0) and AI mode (1, 2, or 3 for each of three different AI strategies). If the human player is active, then the current state of MasterMind is set to MasterMindPlayerState where mouse clicks are examined to see if a color has been clicked on, and, if so, the color is added to the Guess. Further, if the human player is not in the middle of selecting four colors for a Guess, the mouse click is checked to see if the current state should be switched to 
MasterMindAIState. A similar procedure is required when the current state is MasterMindAIState. Mouse clicks are examined to see if the user wants to switch to human player mode. Otherwise, the mouse click indicates that the AI should make another Guess.
MasterMindAIState also stores the different AIs in an array of type MasterMindAI. Let MasterMindRandom be the default AI. Eventually, you will add the more complex AIs described next. The user will be able to switch between AIs by MasterMindAIState using a different AI to make the nextGuess decision.

# MasterMindGameOverState
When the human player or AI has either used up all eight Guesses or the secret code has been identified, MasterMind should enter MasterMindGameOverState so that mouse clicks no longer do anything. The user will know whether they succeeded or not based on whether the last Guess generated 4 black buttons or not.

# MasterMindDriver
Allow the user to pass an integer parameter to MasterMindDriver. If no parameter or a 0 is passed to MasterMindDriver, start MasterMind in the human player mode. If the parameter is not an integer, start MasterMind in the human player mode. Otherwise, start MasterMind in the AI mode with MasterMindAIRandom as the default AI.
Create appropriate build.xml and build.bat files to allow the parameter to be passed to MasterMindDriver at the command prompt. Let the user pass the drive letter to ant as well.

# MasterMindAIConsistent
Use the following algorithm to create a decent AI that will decipher the secret code in 8 attempts or fewer. The first Guess is randomly generated just as is done for MasterMindAIRandom. The second Guess is initially random, but the Guess must satisfy a test before it is returned as the official second Guess. This "trial" Guess is compared to the first, randomly generated Guess. The comparison involves computing the black and white buttons as if the second Guess was the secret code. If the number of black and white buttons in the test comparison does not match the actual black and white buttons returned by MasterMind after the first Guess (obtained by comparison to the secret code), then it is impossible for the second Guess to be the secret code, and a new randomly generated trial Guess is obtained. The process repeats in this manner until a suitable second Guess is found and returned to MasterMind.
As the number of Guesses increases, the constraints on new Guesses also increases as a new Guess must be compared to all of the previous Guesses, generating the identical black and white button results that MasterMind assigned to each previous Guess by comparing them to the secret code. Eventually, there are only a few Guesses that can satisfy all of the constraints, one of which is the secret code.

# MasterMindAIMiniMax
Another approach that never takes more than 6 attempts but takes more time for early Guesses is called MiniMax. The idea is to create a List containing all possible Guesses (how many are there?). The first Guess in the List has all possible combinations of black and white buttons (how many are there of these?) assigned to it, one at a time. Since we don't know what button combination would be returned for that Guess, we must consider them all. A pass through all other Guesses for each of the black and white button assignments is made, counting up the number of other Guesses in the List that would be eliminated as possible secret codes. That is, we increment the count if comparing the two Guesses does not generate the specific black and white button count under consideration. The smallest of these eliminations (the mini part) is stored, along with the Guess that generated it. The AI is keeping track of the worst case that can occur for that particular Guess.
The second Guess in the List goes through the same process. If the smallest number of eliminations for this Guess is larger (the max part) than the previous smallest number of eliminations, store this new value and the Guess that generated it, otherwise keep the previous values. The AI is keeping track of the best of the worst cases for all of the Guesses.
At the end of the process, the computer player knows which Guess will eliminate the most other Guesses in the worst case. Make this Guess. Now remove from the List of Guesses those that are not consistent with the black and white buttons returned from MasterMind (as well as the Guess just made). Repeat the entire process for the second Guess.
The first Guess takes a long time to compute, but it is always the same (why?). To save a substantial amount of time, hardcode the first Guess to be red, blue, green, purple (or any four different colors).

# MasterMindAI Switching
As long as your computer players have access to all of the Guesses made so far (and they do), they should be able to pick up from any point, allowing the user to switch between AIs and between human player and any of the AIs without any problems. A commented out method, int changeAI(int x, int y), can be used to detect when the user wants to change AI/human player mode.
