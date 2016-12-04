package multicore.sequential;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Sudoku
{
	private int[][] sudoku;
	
	private int emptyCells;
	
	private float fillFactor;
    
    private BigInteger solutionSpace;
    
    private long solutionCounter;
	
    /**
     * Costruttore esplicito
     * @param sudoku griglia del Sudoku
     */
	public Sudoku(int[][] sudoku)
	{
		this.sudoku = sudoku;
		emptyCells = 0;
		solutionSpace = BigInteger.ONE;
		solutionCounter = 0;
	}
	
	/**
	 * Restituiscre il valore di una cella del Sudoku
	 * @param row
	 * @param col
	 * @return
	 */
	public int getValue(int row, int col) { return sudoku[row][col]; }
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param v
	 */
	public void setValue(int row, int col, int v) { sudoku[row][col] = v; }
	
	/**
     * Ritorna il numero delle celle da riempire del Sudoku
     * @return emptyCells
     */
    public int getEmptyCells() { return emptyCells; }
    
    /**
     * 
     * @param emptyCells
     */
    public void setEmptyCells(int emptyCells) { this.emptyCells = emptyCells; }
    
    /**
     * Ritorna il fattore di riempimento del Sudoku
     * @return fillFactor
     */
    public float getFillFactor() { return fillFactor; }
    
    /**
     * 
     * @param fillFactor
     */
    public void setFillFactor(float fillFactor) { this.fillFactor = fillFactor; }

    /**
     * Ritorna lo spazio delle soluzioni del Sudoku
     * @return solutionSpace
     */
    public BigInteger getSolutionSpace() { return solutionSpace; }
    
    /**
     * 
     * @param solutionSpace
     */
    public void setSolutionSpace(BigInteger solutionSpace) 
    { 
    	this.solutionSpace = solutionSpace;
    }
    
    /**
     * Ritorna il numero delle soluzioni valide del Sudoku
     * @return solutionCounter
     */
    public long getSolutionCounter() { return solutionCounter; }
    
    /**
     * 
     * @param solutionCounter
     */
    public void setSolutionCounter(long solutionCounter)
    {
		this.solutionCounter = solutionCounter;
	}
    
    /**
     * Calcola i candidati possibili per una specifica cella del Sudoku
     * @param sudoku
     * @param row
     * @param col
     * @return set di Integer che rappresentano i possibili candidati
     */
    public Set<Integer> getCandidates(int row, int col)
    {
    	Set<Integer> candidates = new HashSet<>();
    	for(int i = 1; i <= 9; i++)
    		if (isValid(row, col, i)) candidates.add(i);
		return candidates;
    }
    
    /**
     * Verifico la validità del candidato v in una specifica cella della Sudoku
     * @param sudoku
     * @param row
     * @param col 
     * @param v valore del candidato in esame
     * @return true se il candidato è valido, false altrimenti
     */
    public boolean isValid(int row, int col, int v)
    {        
        // Verifico che il candidato v sia valido lungo la riga e la colonna
        for(int i = 0; i < 9; i++)
        {
            if(sudoku[row][i] == v) return false;
            if(sudoku[i][col] == v) return false;
        }
        // Verifico che il candidato v sia valido all'interno del proprio quadrante
        int rowSubregion = (row / 3)*3;
        int colSubregion = (col / 3)*3;
        //int rowSubregion = row - row % 3;
        //int colSubregion = col - col % 3;
        for(int w = 0; w < 3; w++)
            for(int k = 0; k < 3; k++)
            	if(sudoku[rowSubregion + k][colSubregion + w] == v) return false;
        return true;
    }
    
    /**
     * Trova lo spazio delle soluzioni di un Sudoku
     * @param sudoku
     */
    public void findSpaceSolution()
    {
    	for (int i = 0; i < 9; i++)
    		for (int j = 0; j < 9; j++)
    			if(sudoku[i][j] == 0)
    			{
    				emptyCells++;
    				solutionSpace = solutionSpace.multiply(BigInteger.valueOf(getCandidates(i, j).size()));
    			}
    	fillFactor = (100*(81-emptyCells))/81;
    }
}
