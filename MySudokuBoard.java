import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class MySudokuBoard {

    private int[][] board;
    
    public MySudokuBoard(String filename) {
        board = new int[9][9];
        try {
            Scanner file = new Scanner(new File(filename));
            for (int r = 0; r < 9; r++) {
                String line = file.nextLine();
                for (int c = 0; c < 9; c++) {
                    char ch = line.charAt(c);
                    if (ch == '.') {
                        board[r][c] = 0;
                    } else {
                       board[r][c] = ch - '0';
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + filename);
        }
    }

    public String toString() {
      String result = "";
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[0].length; c++) {
            if(board[r][c] == 0) {
               result += "X ";   
            } else {
               result += board[r][c] + " ";
            }
         }
         result += "\n";
      }

      return result;
    }
    
    
    
    public boolean isValid() {
      return checkRows() && checkColumns() && checkMiniSquares() && checkWeriedChar(); 
      //checkRows();
      //checkRows() && checkColumns() && checkMiniSquares();
    }
    
    private boolean checkWeriedChar() {
      for(int r = 0; r < 9; r++) {
         for(int c = 0; c < 9; c++) {
            if(board[r][c] < 0 || board[r][c] > 9) { //track for weried char: &, @, #
               return false;
            }
         }
      }
      return true;
    }
    
    //tracking for the role 
    private boolean checkRows() {      
      for(int r = 0; r < 9; r++){
         Set<Integer> duplicate = new HashSet<>();
         for(int c = 0; c < 9; c++){             
            if(board[r][c] != 0 && duplicate.contains(board[r][c])) {
               return false;            
            } else {            
               duplicate.add(board[r][c]);
            }            
         }
      }
      return true;
    }
      
      
   private boolean checkColumns() {       
      for(int c = 0; c < 9; c++) {
         Set<Integer> duplicate = new HashSet<>();
         for(int r = 0; r < 9; r++) {          
           if (board[r][c] != 0 && duplicate.contains(board[r][c])) {
               return false;
            } else {
               duplicate.add(board[r][c]);
            }           
         }
      }
      return true;
   }
   
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   private boolean checkMiniSquares() {
      for(int sqNum = 1; sqNum <= 9; sqNum++) {
         int[][] mini = miniSquare(sqNum);
         Set<Integer> duplicate = new HashSet<>();
         for(int r = 0; r < 3; r++) {            
            for(int c = 0 ; c < 3; c++) {  
               int val = mini[r][c];            
               if(val != 0 && duplicate.contains(val)) {
                  return false;
               } else {
                  duplicate.add(val);
               }
            }
         }
      }
      return true;
   }
 
   public boolean isSolved() {
      if(!isValid()) {
         return false;
      }
      Map<Integer, Integer> map = new HashMap<>(); // <the elemet in the board, it's count>
      for(int[] row : board) {
         for(int num : row) {
            if(num != 0) { //go foward if it's not blank
               if(map.containsKey(num)) {
                  map.put(num, map.get(num) + 1);                  
               } else {
                  map.put(num, 1);  //add num if it's not counted yet
               }
            } else {
               return false; // return false if there's blank
            }
         }
      }
      
      for(Integer count : map.values()) {
         if(count != 9) { //return false if the board doesn't have 0-9
            return false;
         }
      }
      //return false if the map's keys missing any 1-9
      for(int k = 1; k <= 9; k++) {
         if(!map.containsKey(k)) {
            return false;
         }
      }
      return true;
   }      
 
}
 
 /*
Checking empty board...passed.
Checking incomplete, valid board...passed.
Checking complete, valid board...passed.
Checking dirty data board...passed.
Checking row violating board...passed.
Checking col violating board...passed.
Checking row&col violating board...passed.
Checking mini-square violating board...passed.
**** HORRAY: ALL TESTS PASSED ****x` 
 */