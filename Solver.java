package main;

import main.util.PuzzleCompleteCallback;

import java.util.Scanner;

import static main.util.PuzzlePrintUtils.printPuzzle;


/**
 * Contains all methods for handling sudoku puzzles
 */
public class Solver
{

	/**
	 * Instantiates a new Solver.
	 */
	public Solver()
	{
	}

	/**
	 * Allows used to manually fill in puzzle
	 *
	 * @param req the req
	 */
	public void playPuzzle(int[][] req)
	{
		int intInput = 1;
		boolean hold = false;

		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				Scanner scanner = new Scanner(System.in);
				while (hold == false)
				{
					if (req[row][col] == 0)
					{
						while (intInput > 0 && intInput <= 9)
						{
							System.out.print("input a number: ");
							intInput = scanner.nextInt();
							if (intInput > 0 && intInput <= 9)
							{
								break;
							}
						}

						hold = isAllowed(intInput, row, col, req);
						if (hold == true)
						{
							System.out.println(intInput + " is valid");
						} else
						{
							System.out.println(intInput + " not valid");
						}
					}
					req[row][col] = intInput;
					hold = false;
					printPuzzle(req);
				}
			}
		}
	}

	/**
	 * Returns
	 * Look at the main follow the steps !!!!!
	 * @param puzzle   the puzzle
	 * @param callback the callback
	 * @return the boolean
	 */
	public boolean solveWithPartial(int[][] puzzle, PuzzleCompleteCallback callback)
	{
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{

				if (puzzle[row][col] == 0)
				{

					for (int num = 1; num <= 9; num++)
					{
						if (isAllowed(num, row, col, puzzle))
						{
							puzzle[row][col] = num;
							if (solveWithPartial(puzzle, callback))
							{
								callback.OnPuzzleReady(puzzle);
								return true;
							} else
							{
								puzzle[row][col] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Prints
	 * Look at the main follow the steps !!!!
	 * @param puzzle the puzzle
	 * @return the boolean
	 */
	public boolean solveWithPartial(int[][] puzzle)
	{
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{

				if (puzzle[row][col] == 0)
				{

					for (int num = 1; num <= 9; num++)
					{
						if (isAllowed(num, row, col, puzzle))
						{
							puzzle[row][col] = num;
							if (solveWithPartial(puzzle))
							{
								printPuzzle(puzzle);
								return true;
							} else
							{
								puzzle[row][col] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * Is allowed boolean.
	 *
	 * @param value the value
	 * @param row   the row
	 * @param col   the col
	 * @param arr   the arr
	 * @return the boolean
	 */
	public boolean isAllowed(int value, int row, int col, int[][] arr)
	{
		return !(containedInRow(value, row, col, arr) || containedInCol(value, row, col, arr) || containedInBox(value, row, col, arr));
	}


	/**
	 * Contained in row boolean.
	 *
	 * @param value the value
	 * @param row   the row
	 * @param col   the col
	 * @param arr   the arr
	 * @return the boolean
	 */
	public boolean containedInRow(int value, int row, int col, int[][] arr)
	{
		for (int i = 0; i < 9; i++)
		{
			if (arr[i][col] == value)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Contained in col boolean.
	 *
	 * @param value the value
	 * @param row   the row
	 * @param col   the col
	 * @param arr   the arr
	 * @return the boolean
	 */
	public boolean containedInCol(int value, int row, int col, int[][] arr)
	{
		for (int i = 0; i < 9; i++)
		{
			if (arr[row][i] == value)
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * Contained in box boolean.
	 * numbers contained in box.
	 * @param value the value
	 * @param row   the row
	 * @param col   the col
	 * @param arr   the arr
	 * @return the boolean
	 */
	public boolean containedInBox(int value, int row, int col, int[][] arr)
	{
		int r = row - row % 3;
		int c = col - col % 3;

		for (int i = r; i < r + 3; i++)
		{
			for (int j = c; j < c + 3; j++)
			{
				if (arr[i][j] == value)
				{
					return true;
				}
			}
		}

		return false;
	}
}