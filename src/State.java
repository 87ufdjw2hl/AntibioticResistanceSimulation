package antibioticresistancesimulation;

/** The State class contains information about the current state of the 
 * bacteria colony we are working with as well as different methods that 
 * we can use to affect the state of the colony. 
 * The State class contains three variables:
 * (1) Cell[][] state: A 2-D array of Cell objects. This array contains the 
 * information about the current state of the bacteria colony.
 * (2) Cell[][] change: A 2-D array of same size to store the temporary state of
 * the bacteria colony in between transitions. 
 * (3) int dimension: The dimension of the square grid/array we are working with.
 * The default is set to = 25. Currently, this default size cannot be modified.
 */

public class State {
    Cell[][] state;
    Cell[][] change;
    int dimension;
    
    public State(){
        dimension = 25;
        state = new Cell[dimension][dimension];
        change = new Cell[dimension][dimension];
    }
    
    
    /** Exports the current STATE of the colony .
     * The method traverses through each of the Cells in the STATE. For each 
     * Cell, the GENEXP is rounded to three decimal places. It is then 
     * subtracted from 1. 
     * 
     * (This subtraction is not a necessary step. The graphics painter paints 1 
     * as white and 0 as black, while I wanted the opposite to be true, so that
     * the higher the GENEXP of a Cell, the darker the table cell appears.)
     * 
     * The resulting value is stored into a 2D array. 
     * 
     * @return The MODEL for JTabel display, corresponding to the current STATE
     * of the bacteria colony.
     */
    public double[][] exportModel(){
       double[][] model = new double[dimension][dimension];
       
       for(int i=0; i<dimension; i++){
           for(int j=0; j<dimension; j++){
               model[i][j]=Math.abs(1.0-(Math.floor(state[i][j].genexp*1000)/1000));
           }
       }
       return model;
   }
 
    /** Calculates and returns the average GENEXP of the Colony.
     * @return The average GENEXP (gene expression level) of the live Cells
     * within the colony, rounded to three decimal points.
     */
    public double average(){
        double sum=0;
        int counter=0;
        
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(state[i][j].genexp>0){
                    // Count the number of alive Cells (denominator)
                    counter++; 
                }
                // Calculate the sum of all the GENEXP (numerator)
                sum+= state[i][j].genexp;
            }
        }
        return Math.floor(sum/counter*1000)/1000; 
    }
   
    /** Triggers the start of the simulation by spawning a Cell at a random
     * position. First initializes both the STATE and the temporary CHANGE to 
     * an empty array. The randomly positioned cell has a GENEXP of 0.1.  
     */
    public void randomStart(){
        // Reset both the STATE and CHANGE arrays.
        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++){
                Cell cell = new Cell();
                state[i][j] = cell;
                change[i][j] = cell;
            }
        }
        
        // Spawn a Cell with GENEXP 0.1 at a random position of the Grid.
        state[randomNumber(dimension-1)][randomNumber(dimension-1)].genexp=0.1;
        state[randomNumber(dimension-1)][randomNumber(dimension-1)].age = 1;   
    }
    
    /** Updates the STATE of the bacteria colony. 
     */
    public void updateState(){
        int[] coord; 
        
        /* Iterate through all the table cells of STATE and determines what kind
        of Cell should be in the same position in the updated STATE. */
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j <dimension; j++) {
                if(change[i][j].age!=0){
                    /* If the CHANGE cell of this position is already filled, 
                    skip this position. This is to avoid overwriting table cells
                    in CHANGE that has spawned a new Cell from a previous Cell 
                    in STATE with a dead Cell (to maintain the dead-ness of a 
                    Cell in STATE). */ 
                } else if(state[i][j].age!=0 ){
                    /* If there is a Cell in position i,j of STATE, choose a 
                    random neighbor and spawn a new Cell in that random neighbor.
                    Relay both the parent and daughter Cell information to CHANGE.
                    */
                    change[i][j].genexp = state[i][j].genexp;
                    change[i][j].age = state[i][j].age+1;
                    coord=randomNeighbor(i,j);
                    change[coord[0]][coord[1]].genexp = spawn(state[i][j].genexp);
                    change[coord[0]][coord[1]].age = 1;
                } else if(state[i][j].age==0){
                    /* If there are no Cell in position i,j of STATE, maintain 
                    the no-Cellness or dead-ness of the table cell in CHANGE. 
                    */
                    change[i][j].genexp=0.0;
                    change[i][j].age=0;
                } else{
                    // In case there are missing cases, originally placed to 
                    // implement a more complicated usage of age. 
                    change[i][j].genexp = state[i][j].genexp;
                    change[i][j].age = state[i][j].age+1;
                } 
            }
        }
        // The temporary state holder CHANGE is filled. Update the STATE using 
        // CHANGE, then reset CHANGE.
        state = change;
        
        change = new Cell[dimension][dimension];
        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++){
                Cell cell = new Cell();
                change[i][j] = cell;
            }
        }
    }
    
    /** Determines the GENEXP (gene expression) to be inherited by the 
     * newly spawning Cell. 
     * 
     * @param gene The gene expression level of the parent Cell. 
     * @return The gene expression level of the daughter Cell.
     */
    public double spawn(double gene){
        // A random number is generated to choose between three possible outcomes.
        int rand = randomNumber(2)+1;
        
        /* In the first case, the gene is passed on with a mutation that increases
        the gene expression level. */
        if(rand%3==0){
            gene += 0.05;
            if(gene>1){
                gene=1;
            }
        /* In the second case, the gene is passed on with a mutation that decreases
            the gene expression level.   */
        } else if(rand%3==1){
            gene -= 0.05;
            if(gene<0.1){
                gene=0.1;
            }
        } 
        // Otherwise, the gene is passed on without any mutation.
        return gene;
    }
    
    
    /** Selects a random neighbor of a Cell with coordinates ROW and COL. 
     * @param row Row index of the Cell
     * @param col Col index of the Cell
     * @return The coordinates of the randomly selected neighbor.
     */
    public int[] randomNeighbor(int row, int col){
        int[] neighbor = new int[2];

        neighbor[0] = row;
        neighbor[1] = col;
        
        // Generate a random number between 1 and 12. 
        int rand = randomNumber(11)+1;

        // Based on the random number and the position of the Cell, choose a 
        // random neighobr of the Cell.  
        if(row==0 && col==0){  // top left
            if(rand%2==0){
                neighbor[0] = 1;
            } else{
                neighbor[1] = 1;
            }   
        } else if(row==0 && col==(dimension-1)){ // top right
            if(rand%2==0){
                neighbor[0] += 1;
            } else{
                neighbor[1] -= 1;
            }            
        } else if(row==(dimension-1) && col==0){ // bottom left
            if(rand%2==0){
                neighbor[0] -= 1;
            } else{
                neighbor[1] += 1;
            }            
        } else if(row==(dimension-1) && col==(dimension-1)){ // bottom right
            if(rand%2==0){
                neighbor[0] -= 1;
            } else{
                neighbor[1] -= 1;
            }            
        } else if(row==0){ // top edge
            switch (rand%3) {
                case 0:
                    neighbor[0] += 1;
                    break;
                case 1:
                    neighbor[1] -= 1;
                    break;
                default:
                    neighbor[1] += 1;
                    break;
            }
        } else if(row==(dimension-1)){ // bottom edge
            switch (rand%3) {
                case 0:
                    neighbor[0] -= 1;
                    break;
                case 1:
                    neighbor[1] -= 1;
                    break;            
                default:
                    neighbor[1] += 1;
                    break;
            }
        } else if(col==0){ // left edge
            if(rand%3==0){
                neighbor[0] += 1;
            } else if(rand%3==1){
                neighbor[0] -= 1;
            } else{
                neighbor[1] += 1;
            }            
        } else if(col==(dimension-1)){ // right edge
            switch (rand%3) {
                case 0:
                    neighbor[0] += 1;
                    break;
                case 1:
                    neighbor[0] -= 1;
                    break;            
                default:
                    neighbor[1] -= 1;
                    break;
            }
        } else{ // all other remaining cells
            switch (rand%4) {
                case 0:
                    neighbor[0] += 1;
                    break;
                case 1:
                    neighbor[0] -= 1;
                    break;
                case 2:
                    neighbor[1] += 1;
                    break;
                default:
                    neighbor[1] -= 1;
                    break;
            }
        }
        return neighbor; 
    }
    
    /** Doses the colony with an antibiotic solution of type TYPE.
     * @param type Type of antibiotic solution used. 
     */
    public void doseAntibio(char type){
        for (Cell x[] : state) {
            for (Cell y : x) {
                if(type=='b'){ 
                    /* Antibiotic solution type 1. The higher the gene expression
                    level of a Cell, the higher the chance of its survival. In 
                    other words, it is advantageous for the Cell to appear dark.
                    */
                    if(Math.random() > y.genexp){
                        y.genexp = 0.0;
                        y.age = 0;
                    }
                } else if(type=='w'){ 
                    /* Antibiotic solution type 2. The lower the gene expression
                    level of a Cell, the higher the chance of its survival. In 
                    other words, it is advantageous for the Cell to appear light.
                    */
                    if(Math.random() > (1-(y.genexp-0.1))){
                        y.genexp = 0.0;
                        y.age = 0;
                    }
                }
            }
        }
    }    

    /** Generate a random integer between 0 and max.
     * @param max The upper bound of the random integer.
     * @return The random integer.
     */
    public int randomNumber(int max){   
       return (int)(Math.random() * (max+1));
    }
        
}

