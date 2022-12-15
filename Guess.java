package mastermind;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import gui.ImageLoader;

class Guess
{
private List<Integer> color_ids; // 4 COLOR IDS THAT REPRESENT THE PLAYER'S ATTEMPTS AT DECIPHERING THE SECRET CODE
private int guess_id;  //the number of this guess [from 1 to 8]
private int[] result;  //the number of black and white buttons, respectively

public Guess(int guess_id)  
{
	this.guess_id = guess_id;
	color_ids = new ArrayList<Integer>();
	result = new int[2];
}

public Guess(Guess copy_guess)  
{
	assert copy_guess.isFull() : "Guess not complete.";
	this.guess_id = copy_guess.guess_id;
	color_ids = copy_guess.getGuessColorIDs();

	result = new int[2];
	result[0] = copy_guess.result[0];
	result[1] = copy_guess.result[1];
}

public int getNumBlack()
{
	return result[0];
}

public int getNumWhite()
{
	return result[1];
}

//comparing two guesses to determine the number of black and white buttons
public int[] reportResult(Guess other_guess)
{
//guesses must have all four positions assigned a color first
	assert isFull() && other_guess.isFull() : "Guesses not complete.";

	int[] result = new int[2];

//need a copy of these as will be removing elements
	List<Integer> other_guess_ids = other_guess.getGuessColorIDs();
	List<Integer> current_guess_ids = getGuessColorIDs();

	int black_count = 0;   
	int white_count = 0;

//easy to check for black buttons
	for (int i = 0; i < other_guess_ids.size(); i++)
	{
		int other_guess_id = other_guess_ids.get(i);
		int current_guess_id = current_guess_ids.get(i);

		if (other_guess_id == current_guess_id)
		{
			black_count++;
	other_guess_ids.remove(i);  //award a black button and remove those elements
	current_guess_ids.remove(i);
	i--;  //accounts for shifting of elements after removal
}
}

//re-analyze for white buttons
//DO THIS

//-----------------------------------------------------------------------------------
// (START) ADD OWN CODE FROM HERE
//-----------------------------------------------------------------------------------
for (int i = 0; i < other_guess_ids.size(); i++)
{
	int other_guess_id = other_guess_ids.get(i);

	for (int j = 0; j < current_guess_ids.size(); j++)
	{
		int current_guess_id = current_guess_ids.get(j);
		if (other_guess_id == current_guess_id)
		{
			white_count++;
			other_guess_ids.remove(i);
			current_guess_ids.remove(j);
			i--; // ACCOUNTS FOR SHIFTING OF ELEMENTS AFTER REMOVAL
			j--; // ACCOUNTS FOR SHIFTING OF ELEMENTS AFTER REMOVAL
			break;
		}
	}
}
//-----------------------------------------------------------------------------------
// (END) TO HERE
//-----------------------------------------------------------------------------------

result[0] = black_count;
result[1] = white_count;
return result;
}

void computeResult(Guess secret)
{
	if (isFull())
	{
		int[] secret_result = reportResult(secret);
		result[0] = secret_result[0];
		result[1] = secret_result[1];
	}
}

public boolean isFull()
{
	return (color_ids.size() == 4);
}

public void addColor(Integer color_id)
{
	if (color_ids.size() < 4)
	{
		color_ids.add(color_id);
	}
}

//make a copy of the list of color ids
//so that black/white button results can be processed
public List<Integer> getGuessColorIDs()
{
	List<Integer> guess_color_ids = null;

	if (color_ids.size() == 4)
	{
		guess_color_ids = new ArrayList<Integer>();
		for (Integer color_id : color_ids)
		{
			guess_color_ids.add(color_id);
		}
	}

	return guess_color_ids;
}

public boolean isCorrect(Guess secret)
{
	if (!isFull()) return false;
	return (result[0] == 4);
}

//will draw incomplete guesses
public void draw(Graphics g, List<MasterMindColor> mm_colors, Image black_button, Image white_button)
{
	int y_pos = (guess_id - 1) * 67 + 34;

	int count = 0;
	for (Integer color_id : color_ids)
	{
		MasterMindColor mm_color = mm_colors.get(color_id - 1);
		int x_pos = count * 67 + 34;
		if (count >= 2) x_pos--;
		mm_color.draw(g, x_pos, y_pos);
		count++;
	}

	if (isFull())
	{
		drawResult(g, black_button, white_button);
	}
}

public void drawResult(Graphics g, Image black_button, Image white_button)
{
	int num_black = result[0];
	int num_white = result[1];

	int button_x_count = 0;

	for (int i = 0; i < num_black; i++)
	{
		button_x_count++;
		int x_loc = (button_x_count - 1)*39 + 309;
		int y_loc = (guess_id - 1)*67 + 34;

		g.drawImage(black_button, x_loc - 13, y_loc - 13, null);
	}

	for (int i = 0; i < num_white; i++)
	{
		button_x_count++;
		int x_loc = (button_x_count - 1)*39 + 309;
		int y_loc = (guess_id - 1)*67 + 34;

		g.drawImage(white_button, x_loc - 13, y_loc - 13, null);
	}
}

public String toString()
{
	String str = "";
	for (Integer color_id : color_ids)
	{
		str += color_id.toString();
	}
	return str;
}
}
