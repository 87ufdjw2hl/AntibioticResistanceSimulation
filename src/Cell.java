package antibioticresistancesimulation;

/** A simple Cell class to represent a single bacteria cell. 
 * For our purposes, we only need two information from each cell:
 * (1) int AGE. This later helps determine whether the bacteria should divide 
 * and reproduce, or to stay as it is. 
 * (2) double GENEXP. This represents the expression level of an arbitrary 
 * gene that influences the chance of survival of the cell under exposure to 
 * different antibiotic solutions. 
 */

public class Cell {
    double genexp;
    int age;
    
    /** The default value of AGE and GENEXP is equal to 0. 
     * At such a case, we consider the cell to be dead or nonexistent. 
     * A cell is considered to be alive if both AGE and GENEXP is > 0. 
     */
    
    public Cell(){
        genexp = 0.0;
        age = 0;
    }
}
