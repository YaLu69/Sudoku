package multicore.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SudokuSolver
{
    /**
     * Crea un Sudoku formato matrice di interi 9x9 a partire dalla lettura di un file
     * @param filePath stringa rappresentante il percorso del file
     * @return il Sudoku associato al file dato in input
     */
	public static Sudoku sudokuReader(String filePath)
	{
		int[][] sudoku = new int[9][9];
		try (InputStream in = Files.newInputStream(Paths.get(new File(filePath).toURI()));
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			int row = 0;
			while ((line = reader.readLine()) != null)
			{
				for (int col = 0; col < line.length(); col++)
					sudoku[row][col] = line.charAt(col) != '.' ?
							Integer.parseInt(""+line.charAt(col)) : 0;
				row++;
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return new Sudoku(sudoku);
	}  
    
    /**
     * Trova lo spazio di soluzioni e il numero di soluzioni del Sudoku in input
     * @param sudoku
     */
    public static void solve(Sudoku sudoku)
    {
    	SudokuTest.fjp.invoke(new ParallelSolver(sudoku));
    }

}