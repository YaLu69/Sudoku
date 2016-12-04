package multicore.parallel;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class ParallelSolver extends RecursiveAction
{
	private Sudoku sudoku;
	
	public ParallelSolver(Sudoku sudoku)
	{
		this.sudoku = sudoku;
	}
	
	public ParallelSolver(Sudoku sudoku, int row, int col, int v)
	{
		this.sudoku = new Sudoku(sudoku);
		this.sudoku.setValue(row, col, v);
	}
	
	public Sudoku getSudoku() { return sudoku; }
	
	public void setSudoku(Sudoku sudoku) { this.sudoku = sudoku; }
	
	/**
	 * Risolvere ricorsivamente un Sudoku
	 * @param sudoku 
	 * @param index
	 */
    private static void findSolution(Sudoku sudoku, int index)
    {
        if(index == 81) sudoku.setSolutionCounter(sudoku.getSolutionCounter()+1);
        else
        {
        	List<ParallelSolver> tasks = new ArrayList<>();
            int row = index / 9;
            int col = index % 9;
            // Salto le celle già riempite e vado avanti
            if(sudoku.getValue(row, col) != 0) findSolution(sudoku, index+1);
            else
            {
                // Provo tutti i possibili valori con cui riempire quella cella
            	for(int i = 1; i <= 9; i++)
            		if(sudoku.isValid(row, col, i))
                    {
            			sudoku.setValue(row, col, i);
            			ParallelSolver ps = new ParallelSolver(sudoku,row,col, i);
                        tasks.add(ps);
                        ps.fork();
                        findSolution(sudoku,index+1);
                        sudoku.setValue(row, col, 0);
                    }
            }
            for(ParallelSolver ps: tasks)
            {
            	ps.join();
            	sudoku.setSolutionCounter(sudoku.getSolutionCounter()+ps.getSudoku().getSolutionCounter());
            }
        }
        
    }

	@Override
	protected void compute()
	{
		sudoku.findSpaceSolution();
    	//long startTime = System.currentTimeMillis();
    	findSolution(sudoku, 0);
		//System.out.println(System.currentTimeMillis() - startTime + " ms - solve() execution time");
	}
	
}
