package multicore.sequential;

public class SudokuTest
{
	public static void main(String[] args)
	{
		String filePath = "test1_b.txt";
		Sudoku sudoku = SudokuSolver.sudokuReader(filePath);
		SudokuSolver.solve(sudoku);
		System.out.println("Sudoku file name: "+filePath);
		System.out.println("Empty cells: " + sudoku.getEmptyCells());
		System.out.println("Fill factor: " + sudoku.getFillFactor() + " %");
		System.out.println("Space solution: " + sudoku.getSolutionSpace());
		System.out.println("Solution counter: " + sudoku.getSolutionCounter());
	}
}
